package com.example.vecopay.domain.model

import kotlinx.datetime.LocalDateTime

/**
 * Modelo de Transacción
 */
data class Transaction(
    val id: String,
    val amount: Double,
    val type: TransactionType,
    val category: String,
    val description: String,
    val accountId: String,
    val date: String, // ISO 8601 format
    val userId: String
)

enum class TransactionType {
    INCOME,    // Ingreso
    EXPENSE    // Gasto
}
