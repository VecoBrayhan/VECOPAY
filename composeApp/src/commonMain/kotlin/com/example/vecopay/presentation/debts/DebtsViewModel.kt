package com.example.vecopay.presentation.debts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vecopay.core.util.Result
import com.example.vecopay.domain.model.Debt
import com.example.vecopay.domain.model.DebtType
import com.example.vecopay.domain.usecase.debt.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlin.uuid.Uuid
import kotlin.uuid.ExperimentalUuidApi

data class DebtsState(
    val isLoading: Boolean = false,
    val iOweDebts: List<Debt> = emptyList(),
    val theyOweDebts: List<Debt> = emptyList(),
    val totalIOwe: Double = 0.0,
    val totalTheyOwe: Double = 0.0,
    val error: String? = null,
    val successMessage: String? = null
)

class DebtsViewModel(
    private val getDebtsUseCase: GetDebtsUseCase,
    private val getDebtsByTypeUseCase: GetDebtsByTypeUseCase,
    private val createDebtUseCase: CreateDebtUseCase,
    private val updateDebtUseCase: UpdateDebtUseCase,
    private val deleteDebtUseCase: DeleteDebtUseCase,
    private val markDebtAsPaidUseCase: MarkDebtAsPaidUseCase,
    private val getTotalIOweUseCase: GetTotalIOweUseCase,
    private val getTotalTheyOweUseCase: GetTotalTheyOweUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(DebtsState())
    val state: StateFlow<DebtsState> = _state.asStateFlow()

    fun loadDebts(userId: String) {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true, error = null)

            try {
                // Cargar deudas que YO DEBO
                when (val iOweResult = getDebtsByTypeUseCase(userId, DebtType.I_OWE)) {
                    is Result.Success -> {
                        _state.value = _state.value.copy(iOweDebts = iOweResult.data)
                    }
                    is Result.Error -> {
                        _state.value = _state.value.copy(error = iOweResult.message)
                    }
                    is Result.Loading -> {}
                }

                // Cargar deudas que ME DEBEN
                when (val theyOweResult = getDebtsByTypeUseCase(userId, DebtType.THEY_OWE)) {
                    is Result.Success -> {
                        _state.value = _state.value.copy(theyOweDebts = theyOweResult.data)
                    }
                    is Result.Error -> {
                        _state.value = _state.value.copy(error = theyOweResult.message)
                    }
                    is Result.Loading -> {}
                }

                // Calcular totales
                when (val totalIOweResult = getTotalIOweUseCase(userId)) {
                    is Result.Success -> {
                        _state.value = _state.value.copy(totalIOwe = totalIOweResult.data)
                    }
                    is Result.Error -> {}
                    is Result.Loading -> {}
                }

                when (val totalTheyOweResult = getTotalTheyOweUseCase(userId)) {
                    is Result.Success -> {
                        _state.value = _state.value.copy(totalTheyOwe = totalTheyOweResult.data)
                    }
                    is Result.Error -> {}
                    is Result.Loading -> {}
                }

            } finally {
                _state.value = _state.value.copy(isLoading = false)
            }
        }
    }

    @OptIn(ExperimentalUuidApi::class)
    fun createDebt(
        userId: String,
        amount: Double,
        type: DebtType,
        person: String,
        description: String
    ) {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true, error = null)

            val debt = Debt(
                id = Uuid.random().toString(),
                amount = amount,
                type = type,
                person = person,
                description = description,
                date = Clock.System.now().toString(),
                isPaid = false,
                userId = userId
            )

            when (val result = createDebtUseCase(debt)) {
                is Result.Success -> {
                    _state.value = _state.value.copy(
                        successMessage = "Deuda creada exitosamente",
                        isLoading = false
                    )
                    loadDebts(userId)
                }
                is Result.Error -> {
                    _state.value = _state.value.copy(
                        error = result.message,
                        isLoading = false
                    )
                }
                is Result.Loading -> {}
            }
        }
    }

    fun markDebtAsPaid(debtId: String, userId: String) {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true, error = null)

            when (val result = markDebtAsPaidUseCase(debtId)) {
                is Result.Success -> {
                    _state.value = _state.value.copy(
                        successMessage = "Deuda marcada como pagada",
                        isLoading = false
                    )
                    loadDebts(userId)
                }
                is Result.Error -> {
                    _state.value = _state.value.copy(
                        error = result.message,
                        isLoading = false
                    )
                }
                is Result.Loading -> {}
            }
        }
    }

    fun deleteDebt(debtId: String, userId: String) {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true, error = null)

            when (val result = deleteDebtUseCase(debtId)) {
                is Result.Success -> {
                    _state.value = _state.value.copy(
                        successMessage = "Deuda eliminada exitosamente",
                        isLoading = false
                    )
                    loadDebts(userId)
                }
                is Result.Error -> {
                    _state.value = _state.value.copy(
                        error = result.message,
                        isLoading = false
                    )
                }
                is Result.Loading -> {}
            }
        }
    }

    fun clearMessages() {
        _state.value = _state.value.copy(error = null, successMessage = null)
    }
}