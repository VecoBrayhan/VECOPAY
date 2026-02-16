package com.example.vecopay.data.repository

import com.example.vecopay.core.util.Result
import com.example.vecopay.data.remote.VecoPayDataSource
import com.example.vecopay.data.remote.dto.*
import com.example.vecopay.domain.model.*
import com.example.vecopay.domain.repository.AccountRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class AccountRepositoryImpl(
    private val dataSource: VecoPayDataSource
) : AccountRepository {

    override suspend fun getAccounts(userId: String): Result<List<Account>> {
        return try {
            val accounts = dataSource.getAccounts(userId)
            Result.Success(accounts.map { it.toDomain() })
        } catch (e: Exception) {
            Result.Error(e, e.message ?: "Error getting accounts")
        }
    }

    override suspend fun getAccountById(id: String): Result<Account?> {
        return try {
            val account = dataSource.getAccountById(id)
            Result.Success(account?.toDomain())
        } catch (e: Exception) {
            Result.Error(e, e.message ?: "Error getting account")
        }
    }

    override suspend fun createAccount(account: Account): Result<Account> {
        return try {
            val accountDto = dataSource.createAccount(account.toDto())
            if (accountDto != null) {
                Result.Success(accountDto.toDomain())
            } else {
                Result.Error(Exception("Failed to create account"), "Could not create account")
            }
        } catch (e: Exception) {
            Result.Error(e, e.message ?: "Error creating account")
        }
    }

    override suspend fun updateAccount(account: Account): Result<Account> {
        return try {
            val accountDto = dataSource.updateAccount(account.toDto())
            if (accountDto != null) {
                Result.Success(accountDto.toDomain())
            } else {
                Result.Error(Exception("Failed to update account"), "Could not update account")
            }
        } catch (e: Exception) {
            Result.Error(e, e.message ?: "Error updating account")
        }
    }

    override suspend fun deleteAccount(id: String): Result<Unit> {
        return try {
            dataSource.deleteAccount(id)
            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Error(e, e.message ?: "Error deleting account")
        }
    }

    override fun observeAccounts(userId: String): Flow<List<Account>> = flow {
        // Implementación básica - puede mejorarse con realtime subscriptions
        val accounts = dataSource.getAccounts(userId)
        emit(accounts.map { it.toDomain() })
    }
}