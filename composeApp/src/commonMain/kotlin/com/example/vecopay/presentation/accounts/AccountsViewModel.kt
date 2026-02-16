package com.example.vecopay.presentation.accounts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vecopay.core.util.Result
import com.example.vecopay.domain.model.Account
import com.example.vecopay.domain.model.AccountType
import com.example.vecopay.domain.usecase.account.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.uuid.Uuid
import kotlin.uuid.ExperimentalUuidApi

data class AccountsState(
    val isLoading: Boolean = false,
    val accounts: List<Account> = emptyList(),
    val error: String? = null,
    val successMessage: String? = null
)

class AccountsViewModel(
    private val getAccountsUseCase: GetAccountsUseCase,
    private val createAccountUseCase: CreateAccountUseCase,
    private val updateAccountUseCase: UpdateAccountUseCase,
    private val deleteAccountUseCase: DeleteAccountUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(AccountsState())
    val state: StateFlow<AccountsState> = _state.asStateFlow()

    fun loadAccounts(userId: String) {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true, error = null)

            when (val result = getAccountsUseCase(userId)) {
                is Result.Success -> {
                    _state.value = _state.value.copy(
                        accounts = result.data,
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
    fun createAccount(
        userId: String,
        name: String,
        type: AccountType,
        balance: Double,
        institution: String? = null
    ) {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true, error = null)

            val account = Account(
                id = Uuid.random().toString(),
                name = name,
                type = type,
                balance = balance,
                institution = institution,
                userId = userId
            )

            when (val result = createAccountUseCase(account)) {
                is Result.Success -> {
                    _state.value = _state.value.copy(
                        successMessage = "Cuenta creada exitosamente",
                        isLoading = false
                    )
                    loadAccounts(userId)
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

    fun updateAccount(account: Account, userId: String) {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true, error = null)

            when (val result = updateAccountUseCase(account)) {
                is Result.Success -> {
                    _state.value = _state.value.copy(
                        successMessage = "Cuenta actualizada exitosamente",
                        isLoading = false
                    )
                    loadAccounts(userId)
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

    fun deleteAccount(accountId: String, userId: String) {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true, error = null)

            when (val result = deleteAccountUseCase(accountId)) {
                is Result.Success -> {
                    _state.value = _state.value.copy(
                        successMessage = "Cuenta eliminada exitosamente",
                        isLoading = false
                    )
                    loadAccounts(userId)
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