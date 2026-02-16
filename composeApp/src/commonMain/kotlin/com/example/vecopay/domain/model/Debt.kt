package com.example.vecopay.domain.model

/**
 * Modelo de Deuda
 */
data class Debt(
    val id: String,
    val amount: Double,
    val type: DebtType,
    val person: String,
    val description: String,
    val date: String,
    val isPaid: Boolean = false,
    val userId: String
)

enum class DebtType {
    I_OWE,      // Yo debo
    THEY_OWE    // Me deben
}