package com.example.vecopay.domain.repository

import com.example.vecopay.core.util.Result
import com.example.vecopay.domain.model.*
import kotlinx.coroutines.flow.Flow

/**
 * Repositorio de Cuentas
 */
interface AccountRepository {
    suspend fun getAccounts(userId: String): Result<List<Account>>
    suspend fun getAccountById(id: String): Result<Account?>
    suspend fun createAccount(account: Account): Result<Account>
    suspend fun updateAccount(account: Account): Result<Account>
    suspend fun deleteAccount(id: String): Result<Unit>
    fun observeAccounts(userId: String): Flow<List<Account>>
}

/**
 * Repositorio de Transacciones
 */
interface TransactionRepository {
    suspend fun getTransactions(userId: String): Result<List<Transaction>>
    suspend fun getTransactionsByAccount(accountId: String): Result<List<Transaction>>
    suspend fun createTransaction(transaction: Transaction): Result<Transaction>
    suspend fun deleteTransaction(id: String): Result<Unit>
    fun observeTransactions(userId: String): Flow<List<Transaction>>
}

/**
 * Repositorio de Deudas
 */
interface DebtRepository {
    suspend fun getDebts(userId: String): Result<List<Debt>>
    suspend fun getDebtsByType(userId: String, type: DebtType): Result<List<Debt>>
    suspend fun createDebt(debt: Debt): Result<Debt>
    suspend fun updateDebt(debt: Debt): Result<Debt>
    suspend fun deleteDebt(id: String): Result<Unit>
    suspend fun markDebtAsPaid(id: String): Result<Unit>
    fun observeDebts(userId: String): Flow<List<Debt>>
}