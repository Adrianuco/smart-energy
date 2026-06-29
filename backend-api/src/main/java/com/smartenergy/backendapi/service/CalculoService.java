package com.smartenergy.backendapi.service;

import com.smartenergy.backendapi.model.*;
import com.smartenergy.backendapi.repository.AulaRepository;
import com.smartenergy.backendapi.repository.HorarioAcademicoRepository;
import com.smartenergy.backendapi.repository.RegistroOperativoRepository;
import com.smartenergy.backendapi.repository.IConfigSistemaRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CalculoService {

    private final RegistroOperativoRepository registroOperativoRepository;
    private final AulaRepository aulaRepository;
    private final HorarioAcademicoRepository horarioAcademicoRepository;
    private final IConfigSistemaRepository configSistemaRepository;

    public CalculoService(RegistroOperativoRepository registroOperativoRepository, AulaRepository aulaRepository, HorarioAcademicoRepository horarioAcademicoRepository, IConfigSistemaRepository configSistemaRepository) {
        this.registroOperativoRepository = registroOperativoRepository;
        this.aulaRepository = aulaRepository;
        this.horarioAcademicoRepository = horarioAcademicoRepository;
        this.configSistemaRepository = configSistemaRepository;
    }
    // metodo para calcular el consumo de un registro operativo en un intervalo en especifico
    public double obtenerConsumoEnIntervalo(RegistroOperativo r, LocalDateTime start, LocalDateTime end) {
        // en caso de que sea un registro apagado o si el equipo es null, retorna 0
        if (r.getEstado() == Estado.APAGADO) {
            return 0.0;
        }
        Equipo equipo = r.getEquipo();
        if (equipo == null) {
            return 0.0;
        }

        // obtener el fin del registro operativo tomando en cuenta registros abiertos
        LocalDateTime finTemporal = r.getFin() != null ? r.getFin() : LocalDateTime.now();

        // se selecciona el intervalo en base al registro operativo
        LocalDateTime overlapStart = r.getInicio().isBefore(start) ? start : r.getInicio(); // se selecciona el mayor para evitar horas no existentes en el registro
        LocalDateTime overlapEnd = finTemporal.isAfter(end) ? end : finTemporal; // lo mismo pero se selecciona el menor

        // verificar error de logica
        if (overlapStart.isBefore(overlapEnd)) {
            // se hace el calculo del consumo en esa ventana de tiempo
            long minutosDiferencia = ChronoUnit.MINUTES.between(overlapStart, overlapEnd);
            double tiempoConsumo = minutosDiferencia / 60.0;
            return (((equipo.getPotenciaMinima() + equipo.getPotenciaNominal()) / 2) * tiempoConsumo) / 1000.0;
        }
        return 0.0;
    }

    // calcular el consumo de un registro operativo
    public double obtenerConsumoDeRegistro(RegistroOperativo r) {
        // en caso de que sea un registro apagado o si el equipo es null, retorna 0
        if (r.getEstado() == Estado.APAGADO) {
            return 0.0;
        }
        Equipo equipo = r.getEquipo();
        if (equipo == null) {
            return 0.0;
        }

        // obtener el fin del registro operativo
        LocalDateTime finTemporal = r.getFin() != null ? r.getFin() : LocalDateTime.now();

        // diferencia de minutos entre la hora inicio y la hora fin del registro
        long minutosDiferencia = ChronoUnit.MINUTES.between(r.getInicio(), finTemporal);

        // se transforma a horas y se hace el calculo
        double tiempoConsumo = minutosDiferencia / 60.0;
        return (((equipo.getPotenciaMinima() + equipo.getPotenciaNominal()) / 2) * tiempoConsumo) / 1000.0;
    }

    // metodo para especificamente cambiar el consumo de un registro
    public void calcularConsumoRegistro(RegistroOperativo registroOperativo) {
        registroOperativo.setConsumo(obtenerConsumoDeRegistro(registroOperativo));
    }

    // metodo para calcular el consumo de un edificio
    public double calcularConsumoEdificio(UUID edificioId) {

        // se establecen los extremos del intervalo de tiempo
        LocalDateTime inicio = LocalDate.now().atStartOfDay();
        LocalDateTime fin = LocalDateTime.now();

        // se buscan todos los registros que se encuentren en ese intervalo
        List<RegistroOperativo> registros = registroOperativoRepository.findOverlapping(inicio, fin);

        // recorremos la lista de registros
        return registros.stream().filter(r ->
                // validaciones para evitar errores
                        r.getEquipo() != null &&
                        r.getEquipo().getAula() != null &&
                        r.getEquipo().getAula().getEdificio() != null &&
                // se filtra por el id del edificio
                        r.getEquipo().getAula().getEdificio().getId().equals(edificioId)
                )
                // recorremos la listra ya filtrada
                .mapToDouble(r ->
                        // se calcula el consumo para cada registro y se suma
                        obtenerConsumoEnIntervalo(r, inicio, fin)
                )
                .sum();
    }

    // metodo para calcular el consumo de todos los registros
    public double calcularConsumoHoy() {
        // se define la ventana de tiempo, desde el inicio del dia hasta el punto actual en el tiempo
        LocalDateTime inicio = LocalDate.now().atStartOfDay();
        LocalDateTime fin = LocalDateTime.now();

        // se recorre la lista de todos los registros de la ventana de tiempo y se calcula el consumo
        return registroOperativoRepository.findOverlapping(inicio, fin)
                .stream()
                .mapToDouble(r -> obtenerConsumoEnIntervalo(r, inicio, fin))
                .sum();
    }

    // metodo usado en los reportes para calcular el consumo de las ultimas 7 horas
    public List<Double> calcularConsumoUltimasHoras() {
        // se define el punto actual en el tiempo
        LocalDateTime now = LocalDateTime.now();
        List<Double> consumos = new ArrayList<>();

        // calculamos el consumo de cada hora
        for (int i = 6; i >= 0; i--) {
            // se define el inicio en base a la resta del momento actual menos las horas actuales del bucle
            LocalDateTime startOfHour = now.minusHours(i).withMinute(0).withSecond(0).withNano(0);
            // se suma uno para definir un intervalo de una hora
            LocalDateTime endOfHour = startOfHour.plusHours(1);

            // recorremos los registros operativos que solapan el intervalo de una hora
            double sum = registroOperativoRepository.findOverlapping(startOfHour, endOfHour)
                    .stream()
                    // se calculan los consumos y se suman
                    .mapToDouble(r -> obtenerConsumoEnIntervalo(r, startOfHour, endOfHour))
                    .sum();
            // añadimos a la lista el consumo calculado de una hora
            consumos.add(sum);
        }
        return consumos;
    }

    // metodo para calcular el ahorro de un dia
    public double calcularAhorro() {
        // calculamos el consumo desde el inicio del dia hasta el punto actual en el tiempo
        double consumoActual = calcularConsumoHoy();
        if (consumoActual == 0) {
            return 0.0;
        }

        // calculamos el consumo esperado en el mismo intervalo
        double consumoEsperadoTotal = calcularConsumoEsperadoTotalHoy();

        if (consumoEsperadoTotal <= 0) {
            return 0.0;
        }

        // se calcula el ahorro porcentual
        return (consumoEsperadoTotal - consumoActual) / consumoEsperadoTotal;

    }

    // calcula el consumo esperado total de hoy
    public double calcularConsumoEsperadoTotalHoy() {
        // se buscan todas las aulas y se calcula su consumo esperado
        return aulaRepository.findAll().stream()
                .mapToDouble(aula -> {
                    try {
                        return calcularConsumoEsperadoAula(aula.getId());
                    } catch (Exception e) {
                        return 0.0;
                    }
                })
                .sum();
    }

    // metodo para calcular el escenario base de un aula
    public double calcularConsumoEsperadoAula(UUID aulaId){

        // buscamos el aula y sus horarios
        Aula aula = aulaRepository.findById(aulaId)
                .orElseThrow();

        List<HorarioAcademico> horarios =
                horarioAcademicoRepository.findByAulaId(aulaId);

        // obtenemos el dia y hora actual
        int diaSemanaActual = LocalDate.now().getDayOfWeek().getValue();
        LocalTime now = LocalTime.now();

        // obtenemos el margen de encendido de config sistema
        ConfigSistema config = configSistemaRepository.findFirstByOrderByIdAsc().orElse(null);
        int margenEncendido = (config != null) ? config.getMargenEncendido() : 0;

        // filtramos los horarios academicos por el dia de hoy
        List<HorarioAcademico> horariosHoy = horarios.stream()
                .filter(h -> h.getDiaSemana() == diaSemanaActual)
                .toList();

        if (horariosHoy.isEmpty()) {
            return 0.0;
        }

        // inicializamos el intervalo de tiempo de la jornada
        LocalTime horaInicioMinima = null;
        LocalTime horaFinMaxima = null;

        // recorremos cada uno de los horarios
        for (HorarioAcademico h : horariosHoy) {
            // ajustamos la hora de inicio restandole el margen de encendido
            LocalTime horaInicioAjustada = h.getHoraInicio().minusMinutes(margenEncendido);

            // obtener los minimos y maximos para obtener la jornada completa
            if (horaInicioMinima == null || horaInicioAjustada.isBefore(horaInicioMinima)) {
                horaInicioMinima = horaInicioAjustada;
            }
            if (horaFinMaxima == null || h.getHoraFin().isAfter(horaFinMaxima)) {
                horaFinMaxima = h.getHoraFin();
            }
        }

        // en caso de que aun no empiece la jornada
        if (horaInicioMinima == null || now.isBefore(horaInicioMinima)) {
            return 0.0;
        }

        // verificar si existe el equipo
        Equipo equipo = aula.getEquipo();
        if (equipo == null) {
            return 0.0;
        }

        // se verifica que se obtenga o el momento actual en el tiempo o la hora fin de la ultima clase
        LocalTime finCalculo = now.isBefore(horaFinMaxima) ? now : horaFinMaxima;
        if (finCalculo.isBefore(horaInicioMinima)) {
            return 0.0;
        }

        // se hace el calculo del consumo
        long minutos = ChronoUnit.MINUTES.between(horaInicioMinima, finCalculo);
        double horasTotales = minutos / 60.0;

        return (((equipo.getPotenciaNominal() + equipo.getPotenciaMinima()) / 2) * horasTotales) / 1000.0;

    }


    // metodo para calcular el consumo esperado por edificio
    public double calcularConsumoEsperadoEdificio(UUID edificioId) {
        // se buscan las aulas por edificio y se recorre la lista
        return aulaRepository.findByEdificioId(edificioId).stream()
                .mapToDouble(aula -> {
                    try {
                        if (aula.getEquipo() == null) {
                            return 0.0;
                        }
                        // se calcula el consumo esperado por aula y se suma
                        return calcularConsumoEsperadoAula(aula.getId());
                    } catch (Exception e) {
                        return 0.0;
                    }
                })
                .sum();
    }

    // metodo para calcular el ahorro por edificio
    public double calcularAhorroEdificio(UUID edificioId) {

        // se obtiene el consumo actual y esperado del edificio
        double consumoActual = calcularConsumoEdificio(edificioId);
        double consumoEsperado = calcularConsumoEsperadoEdificio(edificioId);
        if (consumoEsperado <= 0) {
            return 0.0;
        }
        // se calcula el ahorro porcentual
        double ahorro = (consumoEsperado - consumoActual) / consumoEsperado;
        return Math.max(0.0, ahorro);
    }
}

