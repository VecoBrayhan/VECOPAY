package com.example.vecopay.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vecopay.core.util.Result
import com.example.vecopay.domain.model.Account
import com.example.vecopay.domain.model.Transaction
import com.example.vecopay.domain.usecase.account.GetAccountsUseCase
import com.example.vecopay.domain.usecase.account.GetTotalBalanceUseCase
import com.example.vecopay.domain.usecase.transaction.GetTransactionsUseCase
import com.example.vecopay.domain.usecase.transaction.GetTodayIncomeUseCase
import com.example.vecopay.domain.usecase.transaction.GetTodayExpensesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class HomeState(
    val isLoading: Boolean = false,
    val totalBalance: Double = 0.0,
    val todayIncome: Double = 0.0,
    val todayExpenses: Double = 0.0,
    val accounts: List<Account> = emptyList(),
    val recentTransactions: List<Transaction> = emptyList(),
    val error: String? = null
)

class HomeViewModel(
    private val getAccountsUseCase: GetAccountsUseCase,
    private val getTotalBalanceUseCase: GetTotalBalanceUseCase,
    private val getTransactionsUseCase: GetTransactionsUseCase,
    private val getTodayIncomeUseCase: GetTodayIncomeUseCase,
    private val getTodayExpensesUseCase: GetTodayExpensesUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(HomeState())
    val state: StateFlow<HomeState> = _state.asStateFlow()

    fun loadHomeData(userId: String) {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true, error = null)

            try {
                // Cargar balance total
                when (val balanceResult = getTotalBalanceUseCase(userId)) {
                    is Result.Success -> {
                        _state.value = _state.value.copy(totalBalance = balanceResult.data)
                    }
                    is Result.Error -> {
                        _state.value = _state.value.copy(error = balanceResult.message)
                    }
                    is Result.Loading -> {}
                }

                // Cargar cuentas
                when (val accountsResult = getAccountsUseCase(userId)) {
                    is Result.Success -> {
                        _state.value = _state.value.copy(accounts = accountsResult.data)
                    }
                    is Result.Error -> {
                        _state.value = _state.value.copy(error = accountsResult.message)
                    }
                    is Result.Loading -> {}
                }

                // Cargar transacciones recientes
                when (val transactionsResult = getTransactionsUseCase(userId)) {
                    is Result.Success -> {
                        // Ordenar por fecha y tomar las últimas 5
                        val recent = transactionsResult.data
                            .sortedByDescending { it.date }
                            .take(5)
                        _state.value = _state.value.copy(recentTransactions = recent)
                    }
                    is Result.Error -> {
                        _state.value = _state.value.copy(error = transactionsResult.message)
                    }
                    is Result.Loading -> {}
                }

                // Cargar ingresos de hoy
                when (val incomeResult = getTodayIncomeUseCase(userId)) {
                    is Result.Success -> {
                        _state.value = _state.value.copy(todayIncome = incomeResult.data)
                    }
                    is Result.Error -> {}
                    is Result.Loading -> {}
                }

                // Cargar gastos de hoy
                when (val expensesResult = getTodayExpensesUseCase(userId)) {
                    is Result.Success -> {
                        _state.value = _state.value.copy(todayExpenses = expensesResult.data)
                    }
                    is Result.Error -> {}
                    is Result.Loading -> {}
                }

            } finally {
                _state.value = _state.value.copy(isLoading = false)
            }
        }
    }

    fun refresh(userId: String) {
        loadHomeData(userId)
    }

    fun clearError() {
        _state.value = _state.value.copy(error = null)
    }
}