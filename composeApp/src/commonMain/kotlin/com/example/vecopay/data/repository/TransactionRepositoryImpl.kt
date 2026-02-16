package com.example.vecopay.data.repository

import com.example.vecopay.core.util.Result
import com.example.vecopay.data.remote.VecoPayDataSource
import com.example.vecopay.data.remote.dto.*
import com.example.vecopay.domain.model.Transaction
import com.example.vecopay.domain.repository.TransactionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class TransactionRepositoryImpl(
    private val dataSource: VecoPayDataSource
) : TransactionRepository {

    override suspend fun getTransactions(userId: String): Result<List<Transaction>> {
        return try {
            val transactions = dataSource.getTransactions(userId)
            Result.Success(transactions.map { it.toDomain() })
        } catch (e: Exception) {
            Result.Error(e, e.message ?: "Error getting transactions")
        }
    }

    override suspend fun getTransactionsByAccount(accountId: String): Result<List<Transaction>> {
        return try {
            val transactions = dataSource.getTransactionsByAccount(accountId)
            Result.Success(transactions.map { it.toDomain() })
        } catch (e: Exception) {
            Result.Error(e, e.message ?: "Error getting transactions by account")
        }
    }

    override suspend fun createTransaction(transaction: Transaction): Result<Transaction> {
        return try {
            val transactionDto = dataSource.createTransaction(transaction.toDto())
            if (transactionDto != null) {
                Result.Success(transactionDto.toDomain())
            } else {
                Result.Error(Exception("Failed to create transaction"), "Could not create transaction")
            }
        } catch (e: Exception) {
            Result.Error(e, e.message ?: "Error creating transaction")
        }
    }

    override suspend fun deleteTransaction(id: String): Result<Unit> {
        return try {
            dataSource.deleteTransaction(id)
            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Error(e, e.message ?: "Error deleting transaction")
        }
    }

    override fun observeTransactions(userId: String): Flow<List<Transaction>> = flow {
        val transactions = dataSource.getTransactions(userId)
        emit(transactions.map { it.toDomain() })
    }
}