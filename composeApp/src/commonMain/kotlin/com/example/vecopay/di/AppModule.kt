package com.example.vecopay.di

import com.example.vecopay.data.remote.SupabaseDataSource
import com.example.vecopay.data.remote.VecoPayDataSource
import com.example.vecopay.data.repository.*
import com.example.vecopay.domain.repository.*
import com.example.vecopay.domain.usecase.*
import com.example.vecopay.domain.usecase.account.*
import com.example.vecopay.domain.usecase.transaction.*
import com.example.vecopay.domain.usecase.debt.*
import com.example.vecopay.presentation.auth.AuthViewModel
import com.example.vecopay.presentation.home.HomeViewModel
import com.example.vecopay.presentation.accounts.AccountsViewModel
import com.example.vecopay.presentation.history.HistoryViewModel
import com.example.vecopay.presentation.debts.DebtsViewModel

/**
 * Módulo de Inyección de Dependencias
 * Aquí se crean e inyectan todas las dependencias del proyecto
 */
object AppModule {

    // ============ DATA LAYER ============

    private val supabaseAuthDataSource: SupabaseDataSource by lazy {
        SupabaseDataSource()
    }

    private val vecoPayDataSource: VecoPayDataSource by lazy {
        VecoPayDataSource()
    }

    // Repositories
    private val userRepository: UserRepository by lazy {
        UserRepositoryImpl(supabaseAuthDataSource)
    }

    private val accountRepository: AccountRepository by lazy {
        AccountRepositoryImpl(vecoPayDataSource)
    }

    private val transactionRepository: TransactionRepository by lazy {
        TransactionRepositoryImpl(vecoPayDataSource)
    }

    private val debtRepository: DebtRepository by lazy {
        DebtRepositoryImpl(vecoPayDataSource)
    }

    // ============ DOMAIN LAYER - USE CASES ============

    // Auth Use Cases
    private val signInUseCase: SignInUseCase by lazy {
        SignInUseCase(userRepository)
    }

    private val signUpUseCase: SignUpUseCase by lazy {
        SignUpUseCase(userRepository)
    }

    // Account Use Cases
    private val getAccountsUseCase: GetAccountsUseCase by lazy {
        GetAccountsUseCase(accountRepository)
    }

    private val createAccountUseCase: CreateAccountUseCase by lazy {
        CreateAccountUseCase(accountRepository)
    }

    private val updateAccountUseCase: UpdateAccountUseCase by lazy {
        UpdateAccountUseCase(accountRepository)
    }

    private val deleteAccountUseCase: DeleteAccountUseCase by lazy {
        DeleteAccountUseCase(accountRepository)
    }

    private val getTotalBalanceUseCase: GetTotalBalanceUseCase by lazy {
        GetTotalBalanceUseCase(accountRepository)
    }

    // Transaction Use Cases
    private val getTransactionsUseCase: GetTransactionsUseCase by lazy {
        GetTransactionsUseCase(transactionRepository)
    }

    private val createTransactionUseCase: CreateTransactionUseCase by lazy {
        CreateTransactionUseCase(transactionRepository, accountRepository)
    }

    private val deleteTransactionUseCase: DeleteTransactionUseCase by lazy {
        DeleteTransactionUseCase(transactionRepository)
    }

    private val getTransactionsByAccountUseCase: GetTransactionsByAccountUseCase by lazy {
        GetTransactionsByAccountUseCase(transactionRepository)
    }

    private val getTodayIncomeUseCase: GetTodayIncomeUseCase by lazy {
        GetTodayIncomeUseCase(transactionRepository)
    }

    private val getTodayExpensesUseCase: GetTodayExpensesUseCase by lazy {
        GetTodayExpensesUseCase(transactionRepository)
    }

    // Debt Use Cases
    private val getDebtsUseCase: GetDebtsUseCase by lazy {
        GetDebtsUseCase(debtRepository)
    }

    private val getDebtsByTypeUseCase: GetDebtsByTypeUseCase by lazy {
        GetDebtsByTypeUseCase(debtRepository)
    }

    private val createDebtUseCase: CreateDebtUseCase by lazy {
        CreateDebtUseCase(debtRepository)
    }

    private val updateDebtUseCase: UpdateDebtUseCase by lazy {
        UpdateDebtUseCase(debtRepository)
    }

    private val deleteDebtUseCase: DeleteDebtUseCase by lazy {
        DeleteDebtUseCase(debtRepository)
    }

    private val markDebtAsPaidUseCase: MarkDebtAsPaidUseCase by lazy {
        MarkDebtAsPaidUseCase(debtRepository)
    }

    private val getTotalIOweUseCase: GetTotalIOweUseCase by lazy {
        GetTotalIOweUseCase(debtRepository)
    }

    private val getTotalTheyOweUseCase: GetTotalTheyOweUseCase by lazy {
        GetTotalTheyOweUseCase(debtRepository)
    }

    // ============ PRESENTATION LAYER - VIEW MODELS ============

    fun provideAuthViewModel(): AuthViewModel {
        return AuthViewModel(
            signInUseCase = signInUseCase,
            signUpUseCase = signUpUseCase
        )
    }

    fun provideHomeViewModel(): HomeViewModel {
        return HomeViewModel(
            getAccountsUseCase = getAccountsUseCase,
            getTotalBalanceUseCase = getTotalBalanceUseCase,
            getTransactionsUseCase = getTransactionsUseCase,
            getTodayIncomeUseCase = getTodayIncomeUseCase,
            getTodayExpensesUseCase = getTodayExpensesUseCase
        )
    }

    fun provideAccountsViewModel(): AccountsViewModel {
        return AccountsViewModel(
            getAccountsUseCase = getAccountsUseCase,
            createAccountUseCase = createAccountUseCase,
            updateAccountUseCase = updateAccountUseCase,
            deleteAccountUseCase = deleteAccountUseCase
        )
    }

    fun provideHistoryViewModel(): HistoryViewModel {
        return HistoryViewModel(
            getTransactionsUseCase = getTransactionsUseCase,
            createTransactionUseCase = createTransactionUseCase,
            deleteTransactionUseCase = deleteTransactionUseCase
        )
    }

    fun provideDebtsViewModel(): DebtsViewModel {
        return DebtsViewModel(
            getDebtsUseCase = getDebtsUseCase,
            getDebtsByTypeUseCase = getDebtsByTypeUseCase,
            createDebtUseCase = createDebtUseCase,
            updateDebtUseCase = updateDebtUseCase,
            deleteDebtUseCase = deleteDebtUseCase,
            markDebtAsPaidUseCase = markDebtAsPaidUseCase,
            getTotalIOweUseCase = getTotalIOweUseCase,
            getTotalTheyOweUseCase = getTotalTheyOweUseCase
        )
    }
}