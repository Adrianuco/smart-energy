package com.example.smartenergy.viewmodel.horarios

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smartenergy.model.AsignacionEquiposRequest
import com.example.smartenergy.model.Aula
import com.example.smartenergy.model.Equipo
import com.example.smartenergy.model.Estado
import com.example.smartenergy.model.HorarioAcademico
import com.example.smartenergy.model.RegistroOperativo
import com.example.smartenergy.repository.AulaRepository
import com.example.smartenergy.repository.EquipoRepository
import com.example.smartenergy.repository.HorarioAcademicoRepository
import com.example.smartenergy.repository.RegistroOperativoRepository
import com.example.smartenergy.service.ApiResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import java.time.LocalDateTime

class HorariosViewModel(
    private val repository: HorarioAcademicoRepository,
    private val aulaRepository: AulaRepository,
    private val equipoRepository: EquipoRepository,
    private val registroOperativoRepository: RegistroOperativoRepository
) : ViewModel() {

    // estado para carga de horarios
    private val _state = MutableStateFlow<HorariosState>(HorariosState.Loading)

    // estado para la importacion de horarios
    private val _importState = MutableStateFlow<ImportState>(ImportState.Idle)
    val state = _state.asStateFlow()
    val importState = _importState.asStateFlow()

    init {
        findAll()
    }

    fun findAll() {
        viewModelScope.launch {
            _state.value = HorariosState.Loading
            // buscamos todos los horarios, aulas y equipos
            val horariosResult = repository.findAll()
            val aulasResult = aulaRepository.findAll()
            val equiposResult = equipoRepository.findAll()

            // en caso de success
            if (horariosResult is ApiResult.Success &&
                aulasResult is ApiResult.Success &&
                equiposResult is ApiResult.Success
            ) {
                // filtramos las aulas que no tienen equipo
                val aulasSinEquipo = aulasResult.data.filter { it.equipo == null }

                // en success agregamos los datos
                _state.value = HorariosState.Success(
                    horarios = horariosResult.data,
                    aulasSinEquipo = aulasSinEquipo,
                    equiposDisponibles = equiposResult.data,
                    todasAulas = aulasResult.data
                )
            } else {
                // en error los errores de cada llamada
                val hoMsg = (horariosResult as? ApiResult.Error)?.message ?: ""
                val auMsg = (aulasResult as? ApiResult.Error)?.message ?: ""
                val eqMsg = (equiposResult as? ApiResult.Error)?.message ?: ""
                _state.value = HorariosState.Error(
                    "Error al cargar datos: Horarios: $hoMsg, Aulas: $auMsg, Equipos: $eqMsg"
                )
            }
        }
    }

    // metodo al momento de asignar un modelo de equipo a las aulas
    fun asignarEquipo(aulas: List<Aula>, equipoModelo: Equipo) {
        viewModelScope.launch {
            _state.value = HorariosState.Loading
            val request = AsignacionEquiposRequest(aulas, equipoModelo)
            when (val result = equipoRepository.asignarEquipos(request)) {
                is ApiResult.Success -> {
                    findAll()
                }
                is ApiResult.Error -> {
                    _state.value = HorariosState.Error(result.message)
                }
            }
        }
    }

    // momento de importar el excel
    fun import(file: MultipartBody.Part) {
        viewModelScope.launch {
            _importState.value = ImportState.Loading

            when(val result = repository.import(file)) {
                is ApiResult.Success -> {
                    _importState.value = ImportState.Success(result.data)
                    findAll()
                }
                is ApiResult.Error -> _importState.value = ImportState.Error(result.message)
            }
        }
    }

    // al crear un horario de manera manual
    fun crearHorario(horario: HorarioAcademico) {
        viewModelScope.launch {
            _state.value = HorariosState.Loading
            when (val result = repository.save(horario)) {
                is ApiResult.Success -> {
                    findAll()
                }
                is ApiResult.Error -> {
                    _state.value = HorariosState.Error(result.message)
                }
            }
        }
    }
}