package com.example.vecopay.domain.model

/**
 * Modelo de Cuenta Bancaria
 */
data class Account(
    val id: String,
    val name: String,
    val type: AccountType,
    val balance: Double,
    val currency: String = "S/",
    val institution: String? = null,
    val icon: String? = null,
    val userId: String
)

enum class AccountType {
    CASH,           // Efectivo
    BANK_ACCOUNT,   // Cuenta bancaria
    SAVINGS,        // Ahorros
    CREDIT_CARD     // Tarjeta de crédito
}