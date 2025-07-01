package com.example.myexpense

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.myexpense.navigation.ExpenseNavHost
import com.example.myexpense.ui.components.ExpenseBottomNavigationBar
import com.example.myexpense.ui.components.ExpenseTopAppBar
import com.example.myexpense.ui.screens.Screens
import com.example.myexpense.ui.theme.MyExpenseTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyExpenseApp()
        }
    }
}

@Composable
fun MyExpenseApp() {
    MyExpenseTheme {
        val navController = rememberNavController()
        val screens = listOf(
            Screens.DashBoard,
            Screens.Investment,
            Screens.Expense,
            Screens.Settings,

        )
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                ExpenseTopAppBar(
                    title = "My Expense",
                    onNavigationBack = {}
                )
            },
            bottomBar = {
                ExpenseBottomNavigationBar(
                    navController = navController,
                    screens = screens
                )
            }

        ) { innerPadding ->

            ExpenseNavHost(
                navController = navController,
                startDestination = Screens.DashBoard.route,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            )
        }
    }
}

