package com.example.vecopay.data.repository

import com.example.vecopay.core.util.Result
import com.example.vecopay.data.remote.VecoPayDataSource
import com.example.vecopay.data.remote.dto.*
import com.example.vecopay.domain.model.Debt
import com.example.vecopay.domain.model.DebtType
import com.example.vecopay.domain.repository.DebtRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class DebtRepositoryImpl(
    private val dataSource: VecoPayDataSource
) : DebtRepository {

    override suspend fun getDebts(userId: String): Result<List<Debt>> {
        return try {
            val debts = dataSource.getDebts(userId)
            Result.Success(debts.map { it.toDomain() })
        } catch (e: Exception) {
            Result.Error(e, e.message ?: "Error getting debts")
        }
    }

    override suspend fun getDebtsByType(userId: String, type: DebtType): Result<List<Debt>> {
        return try {
            val debts = dataSource.getDebtsByType(userId, type.name)
            Result.Success(debts.map { it.toDomain() })
        } catch (e: Exception) {
            Result.Error(e, e.message ?: "Error getting debts by type")
        }
    }

    override suspend fun createDebt(debt: Debt): Result<Debt> {
        return try {
            val debtDto = dataSource.createDebt(debt.toDto())
            if (debtDto != null) {
                Result.Success(debtDto.toDomain())
            } else {
                Result.Error(Exception("Failed to create debt"), "Could not create debt")
            }
        } catch (e: Exception) {
            Result.Error(e, e.message ?: "Error creating debt")
        }
    }

    override suspend fun updateDebt(debt: Debt): Result<Debt> {
        return try {
            val debtDto = dataSource.updateDebt(debt.toDto())
            if (debtDto != null) {
                Result.Success(debtDto.toDomain())
            } else {
                Result.Error(Exception("Failed to update debt"), "Could not update debt")
            }
        } catch (e: Exception) {
            Result.Error(e, e.message ?: "Error updating debt")
        }
    }

    override suspend fun deleteDebt(id: String): Result<Unit> {
        return try {
            dataSource.deleteDebt(id)
            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Error(e, e.message ?: "Error deleting debt")
        }
    }

    override suspend fun markDebtAsPaid(id: String): Result<Unit> {
        return try {
            dataSource.markDebtAsPaid(id)
            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Error(e, e.message ?: "Error marking debt as paid")
        }
    }

    override fun observeDebts(userId: String): Flow<List<Debt>> = flow {
        val debts = dataSource.getDebts(userId)
        emit(debts.map { it.toDomain() })
    }
}