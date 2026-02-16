package com.example.vecopay.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import com.example.vecopay.domain.model.*

// ============ ACCOUNT ============
@Serializable
data class AccountDto(
    @SerialName("id") val id: String,
    @SerialName("name") val name: String,
    @SerialName("type") val type: String,
    @SerialName("balance") val balance: Double,
    @SerialName("currency") val currency: String = "S/",
    @SerialName("institution") val institution: String? = null,
    @SerialName("icon") val icon: String? = null,
    @SerialName("user_id") val userId: String,
    @SerialName("created_at") val createdAt: String? = null
)

fun AccountDto.toDomain(): Account {
    return Account(
        id = id,
        name = name,
        type = AccountType.valueOf(type),
        balance = balance,
        currency = currency,
        institution = institution,
        icon = icon,
        userId = userId
    )
}

fun Account.toDto(): AccountDto {
    return AccountDto(
        id = id,
        name = name,
        type = type.name,
        balance = balance,
        currency = currency,
        institution = institution,
        icon = icon,
        userId = userId
    )
}

// ============ TRANSACTION ============
@Serializable
data class TransactionDto(
    @SerialName("id") val id: String,
    @SerialName("amount") val amount: Double,
    @SerialName("type") val type: String,
    @SerialName("category") val category: String,
    @SerialName("description") val description: String,
    @SerialName("account_id") val accountId: String,
    @SerialName("date") val date: String,
    @SerialName("user_id") val userId: String,
    @SerialName("created_at") val createdAt: String? = null
)

fun TransactionDto.toDomain(): Transaction {
    return Transaction(
        id = id,
        amount = amount,
        type = TransactionType.valueOf(type),
        category = category,
        description = description,
        accountId = accountId,
        date = date,
        userId = userId
    )
}

fun Transaction.toDto(): TransactionDto {
    return TransactionDto(
        id = id,
        amount = amount,
        type = type.name,
        category = category,
        description = description,
        accountId = accountId,
        date = date,
        userId = userId
    )
}

// ============ DEBT ============
@Serializable
data class DebtDto(
    @SerialName("id") val id: String,
    @SerialName("amount") val amount: Double,
    @SerialName("type") val type: String,
    @SerialName("person") val person: String,
    @SerialName("description") val description: String,
    @SerialName("date") val date: String,
    @SerialName("is_paid") val isPaid: Boolean = false,
    @SerialName("user_id") val userId: String,
    @SerialName("created_at") val createdAt: String? = null
)

fun DebtDto.toDomain(): Debt {
    return Debt(
        id = id,
        amount = amount,
        type = DebtType.valueOf(type),
        person = person,
        description = description,
        date = date,
        isPaid = isPaid,
        userId = userId
    )
}

fun Debt.toDto(): DebtDto {
    return DebtDto(
        id = id,
        amount = amount,
        type = type.name,
        person = person,
        description = description,
        date = date,
        isPaid = isPaid,
        userId = userId
    )
}