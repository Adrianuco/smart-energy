package com.example.smartenergy.viewmodel.horarios

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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

    private val _state = MutableStateFlow<HorariosState>(HorariosState.Loading)
    private val _importState = MutableStateFlow<ImportState>(ImportState.Idle)
    val state = _state.asStateFlow()
    val importState = _importState.asStateFlow()

    init {
        findAll()
    }

    fun findAll() {
        viewModelScope.launch {
            _state.value = HorariosState.Loading
            val horariosResult = repository.findAll()
            val aulasResult = aulaRepository.findAll()
            val equiposResult = equipoRepository.findAll()

            if (horariosResult is ApiResult.Success &&
                aulasResult is ApiResult.Success &&
                equiposResult is ApiResult.Success
            ) {
                val aulasSinEquipo = aulasResult.data.filter { it.equipo == null }
                _state.value = HorariosState.Success(
                    horarios = horariosResult.data,
                    aulasSinEquipo = aulasSinEquipo,
                    equiposDisponibles = equiposResult.data
                )
            } else {
                val hoMsg = (horariosResult as? ApiResult.Error)?.message ?: ""
                val auMsg = (aulasResult as? ApiResult.Error)?.message ?: ""
                val eqMsg = (equiposResult as? ApiResult.Error)?.message ?: ""
                _state.value = HorariosState.Error(
                    "Error al cargar datos: Horarios: $hoMsg, Aulas: $auMsg, Equipos: $eqMsg"
                )
            }
        }
    }

    fun asignarEquipo(aulas: List<Aula>, equipoModelo: Equipo) {
        viewModelScope.launch {
            _state.value = HorariosState.Loading
            var success = true
            var errorMessage = ""
            for (aula in aulas) {
                // Create a NEW equipment using the model as a template
                val newEquipo = Equipo(
                    id = null,
                    marca = equipoModelo.marca,
                    modelo = equipoModelo.modelo,
                    btu = equipoModelo.btu,
                    eficiencia = equipoModelo.eficiencia,
                    operativo = equipoModelo.operativo,
                    potenciaMinima = equipoModelo.potenciaMinima,
                    potenciaNominal = equipoModelo.potenciaNominal,
                    aula = aula,
                    estado = Estado.APAGADO
                )
                // Save the new equipment in the backend
                when (val equipoResult = equipoRepository.save(newEquipo)) {
                    is ApiResult.Success -> {
                        val savedEquipo = equipoResult.data
                        // Create the RegistroOperativo for this new equipment
                        val registro = RegistroOperativo(
                            estado = Estado.APAGADO,
                            consumo = 0.0,
                            inicio = LocalDateTime.now(),
                            fin = null,
                            equipo = savedEquipo
                        )
                        // Save the RegistroOperativo
                        when (val registroResult = registroOperativoRepository.save(registro)) {
                            is ApiResult.Success -> {
                                // OK
                            }
                            is ApiResult.Error -> {
                                success = false
                                errorMessage = registroResult.message
                                break
                            }
                        }
                    }
                    is ApiResult.Error -> {
                        success = false
                        errorMessage = equipoResult.message
                        break
                    }
                }
            }
            if (success) {
                findAll()
            } else {
                _state.value = HorariosState.Error(errorMessage)
            }
        }
    }

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
}