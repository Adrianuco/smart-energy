package com.smartenergy.backendapi.service;

import com.smartenergy.backendapi.model.Equipo;
import com.smartenergy.backendapi.model.Estado;
import com.smartenergy.backendapi.model.RegistroOperativo;
import org.springframework.stereotype.Service;

import java.time.temporal.ChronoUnit;

@Service
public class CalculoService {

    public void calcularConsumoRegistro(RegistroOperativo registroOperativo) {

        if (registroOperativo.getEstado() == Estado.ENCENDIDO) {
            Equipo equipo = registroOperativo.getEquipo();
            double potenciaMinima = equipo.getPotenciaMinima();
            double potenciaNominal = equipo.getPotenciaNominal();
            long minutosDiferencia = ChronoUnit.MINUTES.between(registroOperativo.getInicio(), registroOperativo.getFin());
            double tiempoConsumo = minutosDiferencia / 60.0;
            double consumo = ((potenciaMinima + potenciaNominal) / 2) * tiempoConsumo;

            registroOperativo.setConsumo(consumo);
        }
        else {
            registroOperativo.setConsumo(0);
        }

    }
}
