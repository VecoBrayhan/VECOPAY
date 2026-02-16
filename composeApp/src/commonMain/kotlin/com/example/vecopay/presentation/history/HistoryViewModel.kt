package com.example.vecopay.presentation.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vecopay.core.util.Result
import com.example.vecopay.domain.model.Transaction
import com.example.vecopay.domain.model.TransactionType
import com.example.vecopay.domain.usecase.transaction.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlin.uuid.Uuid
import kotlin.uuid.ExperimentalUuidApi

data class HistoryState(
    val isLoading: Boolean = false,
    val transactions: List<Transaction> = emptyList(),
    val filteredTransactions: List<Transaction> = emptyList(),
    val selectedFilter: TransactionFilter = TransactionFilter.ALL,
    val error: String? = null,
    val successMessage: String? = null
)

enum class TransactionFilter {
    ALL,
    INCOME,
    EXPENSE
}

class HistoryViewModel(
    private val getTransactionsUseCase: GetTransactionsUseCase,
    private val createTransactionUseCase: CreateTransactionUseCase,
    private val deleteTransactionUseCase: DeleteTransactionUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(HistoryState())
    val state: StateFlow<HistoryState> = _state.asStateFlow()

    fun loadTransactions(userId: String) {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true, error = null)

            when (val result = getTransactionsUseCase(userId)) {
                is Result.Success -> {
                    val transactions = result.data.sortedByDescending { it.date }
                    _state.value = _state.value.copy(
                        transactions = transactions,
                        filteredTransactions = transactions,
                        isLoading = false
                    )
                }
                is Result.Error -> {
                    _state.value = _state.value.copy(
                        error = result.message,
                        isLoading = false
                    )
                }
                is Result.Loading -> {
                    _state.value = _state.value.copy(isLoading = true)
                }
            }
        }
    }

    @OptIn(ExperimentalUuidApi::class)
    fun createTransaction(
        userId: String,
        amount: Double,
        type: TransactionType,
        category: String,
        description: String,
        accountId: String
    ) {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true, error = null)

            val transaction = Transaction(
                id = Uuid.random().toString(),
                amount = amount,
                type = type,
                category = category,
                description = description,
                accountId = accountId,
                date = Clock.System.now().toString(),
                userId = userId
            )

            when (val result = createTransactionUseCase(transaction)) {
                is Result.Success -> {
                    _state.value = _state.value.copy(
                        successMessage = "Transacción creada exitosamente",
                        isLoading = false
                    )
                    loadTransactions(userId)
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

    fun deleteTransaction(transactionId: String, userId: String) {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true, error = null)

            when (val result = deleteTransactionUseCase(transactionId)) {
                is Result.Success -> {
                    _state.value = _state.value.copy(
                        successMessage = "Transacción eliminada exitosamente",
                        isLoading = false
                    )
                    loadTransactions(userId)
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

    fun filterTransactions(filter: TransactionFilter) {
        val filtered = when (filter) {
            TransactionFilter.ALL -> _state.value.transactions
            TransactionFilter.INCOME -> _state.value.transactions.filter { it.type == TransactionType.INCOME }
            TransactionFilter.EXPENSE -> _state.value.transactions.filter { it.type == TransactionType.EXPENSE }
        }

        _state.value = _state.value.copy(
            selectedFilter = filter,
            filteredTransactions = filtered
        )
    }

    fun clearMessages() {
        _state.value = _state.value.copy(error = null, successMessage = null)
    }
}