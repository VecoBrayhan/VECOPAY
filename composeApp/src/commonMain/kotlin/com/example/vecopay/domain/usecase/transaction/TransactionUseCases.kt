package com.example.vecopay.domain.usecase.transaction

import com.example.vecopay.core.util.Result
import com.example.vecopay.domain.model.Transaction
import com.example.vecopay.domain.model.TransactionType
import com.example.vecopay.domain.repository.TransactionRepository
import com.example.vecopay.domain.repository.AccountRepository
import kotlinx.datetime.Clock

/**
 * Caso de uso: Obtener todas las transacciones del usuario
 */
class GetTransactionsUseCase(
    private val repository: TransactionRepository
) {
    suspend operator fun invoke(userId: String): Result<List<Transaction>> {
        if (userId.isBlank()) {
            return Result.Error(Exception("User ID is required"), "User ID cannot be empty")
        }
        return repository.getTransactions(userId)
    }
}

/**
 * Caso de uso: Crear una nueva transacción y actualizar el balance de la cuenta
 */
class CreateTransactionUseCase(
    private val transactionRepository: TransactionRepository,
    private val accountRepository: AccountRepository
) {
    suspend operator fun invoke(transaction: Transaction): Result<Transaction> {
        // Validaciones
        if (transaction.amount <= 0) {
            return Result.Error(Exception("Invalid amount"), "Amount must be greater than 0")
        }

        if (transaction.description.isBlank()) {
            return Result.Error(Exception("Description is required"), "Description cannot be empty")
        }

        // Obtener la cuenta para actualizar su balance
        val accountResult = accountRepository.getAccountById(transaction.accountId)
        if (accountResult !is Result.Success || accountResult.data == null) {
            return Result.Error(Exception("Account not found"), "The selected account does not exist")
        }

        val account = accountResult.data

        // Calcular nuevo balance
        val newBalance = when (transaction.type) {
            TransactionType.INCOME -> account.balance + transaction.amount
            TransactionType.EXPENSE -> account.balance - transaction.amount
        }

        // Verificar que no quede en negativo (opcional, depende de tu lógica de negocio)
        if (newBalance < 0 && transaction.type == TransactionType.EXPENSE) {
            return Result.Error(Exception("Insufficient funds"), "Not enough balance in account")
        }

        // Crear transacción
        val transactionResult = transactionRepository.createTransaction(transaction)

        // Si la transacción se creó exitosamente, actualizar el balance de la cuenta
        if (transactionResult is Result.Success) {
            val updatedAccount = account.copy(balance = newBalance)
            accountRepository.updateAccount(updatedAccount)
        }

        return transactionResult
    }
}

/**
 * Caso de uso: Eliminar una transacción
 */
class DeleteTransactionUseCase(
    private val repository: TransactionRepository
) {
    suspend operator fun invoke(transactionId: String): Result<Unit> {
        if (transactionId.isBlank()) {
            return Result.Error(Exception("Transaction ID is required"), "Transaction ID cannot be empty")
        }

        return repository.deleteTransaction(transactionId)
    }
}

/**
 * Caso de uso: Obtener transacciones por cuenta
 */
class GetTransactionsByAccountUseCase(
    private val repository: TransactionRepository
) {
    suspend operator fun invoke(accountId: String): Result<List<Transaction>> {
        if (accountId.isBlank()) {
            return Result.Error(Exception("Account ID is required"), "Account ID cannot be empty")
        }

        return repository.getTransactionsByAccount(accountId)
    }
}

/**
 * Caso de uso: Calcular ingresos totales de hoy
 */
class GetTodayIncomeUseCase(
    private val repository: TransactionRepository
) {
    suspend operator fun invoke(userId: String): Result<Double> {
        return when (val result = repository.getTransactions(userId)) {
            is Result.Success -> {
                val today = Clock.System.now().toString().substring(0, 10)
                val todayIncome = result.data
                    .filter { it.type == TransactionType.INCOME && it.date.startsWith(today) }
                    .sumOf { it.amount }
                Result.Success(todayIncome)
            }
            is Result.Error -> result
            is Result.Loading -> Result.Loading
        }
    }
}

/**
 * Caso de uso: Calcular gastos totales de hoy
 */
class GetTodayExpensesUseCase(
    private val repository: TransactionRepository
) {
    suspend operator fun invoke(userId: String): Result<Double> {
        return when (val result = repository.getTransactions(userId)) {
            is Result.Success -> {
                val today = Clock.System.now().toString().substring(0, 10)
                val todayExpenses = result.data
                    .filter { it.type == TransactionType.EXPENSE && it.date.startsWith(today) }
                    .sumOf { it.amount }
                Result.Success(todayExpenses)
            }
            is Result.Error -> result
            is Result.Loading -> Result.Loading
        }
    }
}