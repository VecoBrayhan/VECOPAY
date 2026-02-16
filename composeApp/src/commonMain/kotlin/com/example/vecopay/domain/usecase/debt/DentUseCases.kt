package com.example.vecopay.domain.usecase.debt

import com.example.vecopay.core.util.Result
import com.example.vecopay.domain.model.Debt
import com.example.vecopay.domain.model.DebtType
import com.example.vecopay.domain.repository.DebtRepository

/**
 * Caso de uso: Obtener todas las deudas del usuario
 */
class GetDebtsUseCase(
    private val repository: DebtRepository
) {
    suspend operator fun invoke(userId: String): Result<List<Debt>> {
        if (userId.isBlank()) {
            return Result.Error(Exception("User ID is required"), "User ID cannot be empty")
        }
        return repository.getDebts(userId)
    }
}

/**
 * Caso de uso: Obtener deudas por tipo (Yo debo / Me deben)
 */
class GetDebtsByTypeUseCase(
    private val repository: DebtRepository
) {
    suspend operator fun invoke(userId: String, type: DebtType): Result<List<Debt>> {
        if (userId.isBlank()) {
            return Result.Error(Exception("User ID is required"), "User ID cannot be empty")
        }
        return repository.getDebtsByType(userId, type)
    }
}

/**
 * Caso de uso: Crear una nueva deuda
 */
class CreateDebtUseCase(
    private val repository: DebtRepository
) {
    suspend operator fun invoke(debt: Debt): Result<Debt> {
        // Validaciones
        if (debt.amount <= 0) {
            return Result.Error(Exception("Invalid amount"), "Amount must be greater than 0")
        }

        if (debt.person.isBlank()) {
            return Result.Error(Exception("Person name is required"), "Person name cannot be empty")
        }

        if (debt.description.isBlank()) {
            return Result.Error(Exception("Description is required"), "Description cannot be empty")
        }

        return repository.createDebt(debt)
    }
}

/**
 * Caso de uso: Actualizar una deuda
 */
class UpdateDebtUseCase(
    private val repository: DebtRepository
) {
    suspend operator fun invoke(debt: Debt): Result<Debt> {
        if (debt.person.isBlank()) {
            return Result.Error(Exception("Person name is required"), "Person name cannot be empty")
        }

        return repository.updateDebt(debt)
    }
}

/**
 * Caso de uso: Marcar una deuda como pagada
 */
class MarkDebtAsPaidUseCase(
    private val repository: DebtRepository
) {
    suspend operator fun invoke(debtId: String): Result<Unit> {
        if (debtId.isBlank()) {
            return Result.Error(Exception("Debt ID is required"), "Debt ID cannot be empty")
        }

        return repository.markDebtAsPaid(debtId)
    }
}

/**
 * Caso de uso: Eliminar una deuda
 */
class DeleteDebtUseCase(
    private val repository: DebtRepository
) {
    suspend operator fun invoke(debtId: String): Result<Unit> {
        if (debtId.isBlank()) {
            return Result.Error(Exception("Debt ID is required"), "Debt ID cannot be empty")
        }

        return repository.deleteDebt(debtId)
    }
}

/**
 * Caso de uso: Calcular total de lo que YO DEBO
 */
class GetTotalIOweUseCase(
    private val repository: DebtRepository
) {
    suspend operator fun invoke(userId: String): Result<Double> {
        return when (val result = repository.getDebtsByType(userId, DebtType.I_OWE)) {
            is Result.Success -> {
                val total = result.data
                    .filter { !it.isPaid }
                    .sumOf { it.amount }
                Result.Success(total)
            }
            is Result.Error -> result
            is Result.Loading -> Result.Loading
        }
    }
}

/**
 * Caso de uso: Calcular total de lo que ME DEBEN
 */
class GetTotalTheyOweUseCase(
    private val repository: DebtRepository
) {
    suspend operator fun invoke(userId: String): Result<Double> {
        return when (val result = repository.getDebtsByType(userId, DebtType.THEY_OWE)) {
            is Result.Success -> {
                val total = result.data
                    .filter { !it.isPaid }
                    .sumOf { it.amount }
                Result.Success(total)
            }
            is Result.Error -> result
            is Result.Loading -> Result.Loading
        }
    }
}