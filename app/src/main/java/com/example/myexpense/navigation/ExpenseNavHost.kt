package com.example.myexpense.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.myexpense.ui.screens.settings.SettingsScreen
import com.example.myexpense.ui.screens.Screens
import com.example.myexpense.ui.screens.dashboard.DashboardScreen
import com.example.myexpense.ui.screens.expense.AddExpenseScreen
import com.example.myexpense.ui.screens.expense.ExpenseDetailsScreen
import com.example.myexpense.ui.screens.expense.ExpenseScreen
import com.example.myexpense.ui.screens.investment.InvestmentDetailsScreen
import com.example.myexpense.ui.screens.investment.InvestmentScreen
import com.example.myexpense.ui.screens.settings.EditProfileScreen


@Composable
fun ExpenseNavHost(
    navController: androidx.navigation.NavHostController,
    startDestination: String,
    modifier: Modifier// Default start destination
) {

    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable(route = Screens.AddExpense.route) {
            // Placeholder for Add Expense Screen
            // This can be expanded later with actual UI components for adding an expense
            // For now, we can just display a simple text or a placeholder UI
            AddExpenseScreen(
                onClose = {
                    navController.popBackStack() // Navigate back after adding an expense
                },
            )
        }

        composable(route = Screens.Expense.route) {
            // Placeholder for Expense Screen
            // This can be expanded later with actual UI components for displaying expenses
            // For now, we can just display a simple text or a placeholder UI
            // Example:
            // Text(text = "Expense Screen")
            ExpenseScreen(
                onAddExpense = {
                    navController.navigate(Screens.AddExpense.route)
                },
//                onExpenseDetails = { expenseId ->
//                    navController.navigate(Screens.ExpenseDetails.createRoute(expenseId))
//                }
            )
        }

        composable(route = Screens.DashBoard.route) {
            // Placeholder for Dashboard Screen
            // This can be expanded later with actual UI components for displaying the dashboard
            // For now, we can just display a simple text or a placeholder UI
            // Example:
            // Text(text = "Dashboard Screen")
            DashboardScreen()
        }

        composable(route = Screens.Settings.route) {
            // Placeholder for Settings Screen
            // This can be expanded later with actual UI components for displaying settings
            // For now, we can just display a simple text or a placeholder UI
            // Example:
            // Text(text = "Settings Screen")
            SettingsScreen(
                onEditProfile = {
                    navController.navigate(Screens.EditProfile.route)
                }
            )
        }
        composable(route = Screens.Investment.route) {
            // Placeholder for Investment Screen
            // This can be expanded later with actual UI components for displaying investments
            // For now, we can just display a simple text or a placeholder UI
            // Example:
            // Text(text = "Investment Screen")
            InvestmentScreen()
        }
        composable(route = Screens.InvestmentDetails.route) { backStackEntry ->
            // Placeholder for Investment Details Screen
            // This can be expanded later with actual UI components for displaying investment details
            // For now, we can just display a simple text or a placeholder UI
            // Example:
            // val investmentId = backStackEntry.arguments?.getString("investmentId")
            // Text(text = "Investment Details Screen for ID: $investmentId")
            InvestmentDetailsScreen()
        }
        composable(route = Screens.ExpenseDetails.route) { backStackEntry ->
            // Placeholder for Expense Details Screen
            // This can be expanded later with actual UI components for displaying expense details
            // For now, we can just display a simple text or a placeholder UI
            // Example:
            // val expenseId = backStackEntry.arguments?.getString("expenseId")
            // Text(text = "Expense Details Screen for ID: $expenseId")
            ExpenseDetailsScreen()
        }

        composable(route = Screens.EditProfile.route) {
            // Placeholder for Edit Profile Screen
            // This can be expanded later with actual UI components for editing user profile
            // For now, we can just display a simple text or a placeholder UI
            // Example:
            // Text(text = "Edit Profile Screen")
            EditProfileScreen(
                onProfileSaved = {
                    navController.popBackStack()
                }
            )
        }
    }
}