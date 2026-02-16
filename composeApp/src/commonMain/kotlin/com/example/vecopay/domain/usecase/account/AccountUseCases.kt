package com.example.vecopay.domain.usecase.account

import com.example.vecopay.core.util.Result
import com.example.vecopay.domain.model.Account
import com.example.vecopay.domain.repository.AccountRepository

/**
 * Caso de uso: Obtener todas las cuentas del usuario
 */
class GetAccountsUseCase(
    private val repository: AccountRepository
) {
    suspend operator fun invoke(userId: String): Result<List<Account>> {
        if (userId.isBlank()) {
            return Result.Error(Exception("User ID is required"), "User ID cannot be empty")
        }
        return repository.getAccounts(userId)
    }
}

/**
 * Caso de uso: Crear una nueva cuenta
 */
class CreateAccountUseCase(
    private val repository: AccountRepository
) {
    suspend operator fun invoke(account: Account): Result<Account> {
        // Validaciones de negocio
        if (account.name.isBlank()) {
            return Result.Error(Exception("Account name is required"), "Account name cannot be empty")
        }

        if (account.balance < 0) {
            return Result.Error(Exception("Invalid balance"), "Balance cannot be negative")
        }

        return repository.createAccount(account)
    }
}

/**
 * Caso de uso: Actualizar una cuenta existente
 */
class UpdateAccountUseCase(
    private val repository: AccountRepository
) {
    suspend operator fun invoke(account: Account): Result<Account> {
        if (account.name.isBlank()) {
            return Result.Error(Exception("Account name is required"), "Account name cannot be empty")
        }

        return repository.updateAccount(account)
    }
}

/**
 * Caso de uso: Eliminar una cuenta
 */
class DeleteAccountUseCase(
    private val repository: AccountRepository
) {
    suspend operator fun invoke(accountId: String): Result<Unit> {
        if (accountId.isBlank()) {
            return Result.Error(Exception("Account ID is required"), "Account ID cannot be empty")
        }

        return repository.deleteAccount(accountId)
    }
}

/**
 * Caso de uso: Calcular balance total
 */
class GetTotalBalanceUseCase(
    private val repository: AccountRepository
) {
    suspend operator fun invoke(userId: String): Result<Double> {
        return when (val result = repository.getAccounts(userId)) {
            is Result.Success -> {
                val totalBalance = result.data.sumOf { it.balance }
                Result.Success(totalBalance)
            }
            is Result.Error -> result
            is Result.Loading -> Result.Loading
        }
    }
}