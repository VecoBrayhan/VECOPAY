package com.example.vecopay.data.remote

import com.example.vecopay.core.network.SupabaseClientProvider
import com.example.vecopay.data.remote.dto.AccountDto
import com.example.vecopay.data.remote.dto.DebtDto
import com.example.vecopay.data.remote.dto.TransactionDto
import io.github.jan.supabase.postgrest.from

class VecoPayDataSource {

    private val supabase = SupabaseClientProvider.client

    // ============ ACCOUNTS ============

    suspend fun getAccounts(userId: String): List<AccountDto> {
        return try {
            supabase.from("accounts")
                .select {
                    filter {
                        eq("user_id", userId)
                    }
                }
                .decodeList<AccountDto>()
        } catch (e: Exception) {
            println("Error getting accounts: ${e.message}")
            emptyList()
        }
    }

    suspend fun getAccountById(id: String): AccountDto? {
        return try {
            supabase.from("accounts")
                .select {
                    filter {
                        eq("id", id)
                    }
                }
                .decodeSingleOrNull<AccountDto>()
        } catch (e: Exception) {
            println("Error getting account: ${e.message}")
            null
        }
    }

    suspend fun createAccount(account: AccountDto): AccountDto? {
        return try {
            supabase.from("accounts")
                .insert(account)
                .decodeSingle<AccountDto>()
        } catch (e: Exception) {
            println("Error creating account: ${e.message}")
            null
        }
    }

    suspend fun updateAccount(account: AccountDto): AccountDto? {
        return try {
            supabase.from("accounts")
                .update(account) {
                    filter {
                        eq("id", account.id)
                    }
                }
                .decodeSingle<AccountDto>()
        } catch (e: Exception) {
            println("Error updating account: ${e.message}")
            null
        }
    }

    suspend fun deleteAccount(id: String) {
        try {
            supabase.from("accounts")
                .delete {
                    filter {
                        eq("id", id)
                    }
                }
        } catch (e: Exception) {
            println("Error deleting account: ${e.message}")
        }
    }

    // ============ TRANSACTIONS ============

    suspend fun getTransactions(userId: String): List<TransactionDto> {
        return try {
            supabase.from("transactions")
                .select {
                    filter {
                        eq("user_id", userId)
                    }
                }
                .decodeList<TransactionDto>()
        } catch (e: Exception) {
            println("Error getting transactions: ${e.message}")
            emptyList()
        }
    }

    suspend fun getTransactionsByAccount(accountId: String): List<TransactionDto> {
        return try {
            supabase.from("transactions")
                .select {
                    filter {
                        eq("account_id", accountId)
                    }
                }
                .decodeList<TransactionDto>()
        } catch (e: Exception) {
            println("Error getting transactions by account: ${e.message}")
            emptyList()
        }
    }

    suspend fun createTransaction(transaction: TransactionDto): TransactionDto? {
        return try {
            supabase.from("transactions")
                .insert(transaction)
                .decodeSingle<TransactionDto>()
        } catch (e: Exception) {
            println("Error creating transaction: ${e.message}")
            null
        }
    }

    suspend fun deleteTransaction(id: String) {
        try {
            supabase.from("transactions")
                .delete {
                    filter {
                        eq("id", id)
                    }
                }
        } catch (e: Exception) {
            println("Error deleting transaction: ${e.message}")
        }
    }

    // ============ DEBTS ============

    suspend fun getDebts(userId: String): List<DebtDto> {
        return try {
            supabase.from("debts")
                .select {
                    filter {
                        eq("user_id", userId)
                    }
                }
                .decodeList<DebtDto>()
        } catch (e: Exception) {
            println("Error getting debts: ${e.message}")
            emptyList()
        }
    }

    suspend fun getDebtsByType(userId: String, type: String): List<DebtDto> {
        return try {
            supabase.from("debts")
                .select {
                    filter {
                        eq("user_id", userId)
                        eq("type", type)
                    }
                }
                .decodeList<DebtDto>()
        } catch (e: Exception) {
            println("Error getting debts by type: ${e.message}")
            emptyList()
        }
    }

    suspend fun createDebt(debt: DebtDto): DebtDto? {
        return try {
            supabase.from("debts")
                .insert(debt)
                .decodeSingle<DebtDto>()
        } catch (e: Exception) {
            println("Error creating debt: ${e.message}")
            null
        }
    }

    suspend fun updateDebt(debt: DebtDto): DebtDto? {
        return try {
            supabase.from("debts")
                .update(debt) {
                    filter {
                        eq("id", debt.id)
                    }
                }
                .decodeSingle<DebtDto>()
        } catch (e: Exception) {
            println("Error updating debt: ${e.message}")
            null
        }
    }

    suspend fun deleteDebt(id: String) {
        try {
            supabase.from("debts")
                .delete {
                    filter {
                        eq("id", id)
                    }
                }
        } catch (e: Exception) {
            println("Error deleting debt: ${e.message}")
        }
    }

    suspend fun markDebtAsPaid(id: String) {
        try {
            supabase.from("debts")
                .update({
                    set("is_paid", true)
                }) {
                    filter {
                        eq("id", id)
                    }
                }
        } catch (e: Exception) {
            println("Error marking debt as paid: ${e.message}")
        }
    }
}