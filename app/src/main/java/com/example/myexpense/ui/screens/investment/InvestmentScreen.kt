package com.example.myexpense.ui.screens.investment

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InvestmentScreen(
    onAddInvestment: () -> Unit = { /* Default no-op */ }
) {
    // Placeholder for Investment Screen
    // This can be expanded later with actual UI components for displaying investments
    // For now, we can just display a simple text or a placeholder UI
    // Example:
    // Text(text = "Investment Screen")

    Scaffold(topBar = {
        TopAppBar(
            title = { Text(text = "Investment") },
            actions = {
                IconButton(onClick = onAddInvestment) {
                    Icon(
                        imageVector = Icons.Default.AddCircle,
                        contentDescription = "Add Investment",
                    )
                }
            }
        )
    }) { innerPadding ->
        // Content of the Investment Screen
        // This can include a list of investments, a form to add new investments, etc.
        // For now, we can just display a placeholder box or text
        Box(modifier = Modifier.padding(innerPadding)) {
            // Placeholder content
            Text(text = "Investment Screen Content")
        }
    }
}
