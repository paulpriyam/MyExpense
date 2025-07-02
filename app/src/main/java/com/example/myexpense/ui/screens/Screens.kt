package com.example.myexpense.ui.screens

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screens(val route: String, val title: String, val icon: ImageVector) {
    object DashBoard : Screens(ScreenNames.DashBoard.name, ScreenNames.DashBoard.title, Icons.Default.Home)

    object Expense : Screens(ScreenNames.Expense.name, ScreenNames.Expense.title, Icons.Default.Info)
    object AddExpense :
        Screens(ScreenNames.AddExpense.name, ScreenNames.AddExpense.title, Icons.Default.Info)

    object ExpenseDetails : Screens(
        ScreenNames.ExpenseDetails.name.plus("/{expenseId}"),
        ScreenNames.ExpenseDetails.title,
        Icons.Default.Info
    ) {
        fun createRoute(expenseId: String) = ScreenNames.ExpenseDetails.name + "/$expenseId"
    }

    object Investment :
        Screens(ScreenNames.Investment.name, ScreenNames.Investment.title, Icons.Default.Info)

    object InvestmentDetails : Screens(
        ScreenNames.InvestmentDetails.name.plus("/{investmentId}"),
        ScreenNames.InvestmentDetails.title,
        Icons.Default.Info
    ) {
        fun createRoute(investmentId: String) =
            ScreenNames.InvestmentDetails.name + "/$investmentId"
    }

    object Settings :
        Screens(ScreenNames.Settings.name, ScreenNames.Settings.title, Icons.Default.Settings)

    object EditProfile :
        Screens(ScreenNames.EditProfile.name, ScreenNames.EditProfile.title, Icons.Default.Settings)

}

enum class ScreenNames(val title: String) {
    DashBoard("Dashboard"),
    Expense("Expense"),
    AddExpense("Add Expense"),
    ExpenseDetails("Expense Details"),
    Investment("Investment"),
    AddInvestment("Add Investment"),
    InvestmentDetails("Investment Details"),

    Settings("Settings"),
    EditProfile("Edit Profile"),
}