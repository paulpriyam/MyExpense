package com.example.myexpense.ui.screens.expense

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.myexpense.ui.components.ExpenseCard
import com.example.myexpense.utils.PreferenceManager
import java.text.SimpleDateFormat
import java.util.Locale

data class ExpenseCardData(
    val title: String,
    val amount: Double,
    val date: String,
    val type: String = "Expense"
)

data class ExpenseListItem(
    val title: String,
    val amount: Double,
    val category: String,
    val date: String,
    val type: String = "Expense"
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpenseScreen(
    onAddExpense: () -> Unit = {},
) {
    val context = LocalContext.current
    val preferenceManager: PreferenceManager = remember { PreferenceManager(context) }
    val viewModel: ExpenseViewModel = hiltViewModel()

    // Load data only once on composition, not on each recomposition
    LaunchedEffect(preferenceManager.getUserId()) {
        val userId = preferenceManager.getUserId()
        if (!userId.isNullOrEmpty()) {
            viewModel.getExpenseByUserId(userId)
            viewModel.loadCategories()
        }
    }

    // Get data from ViewModel
    val expenses by viewModel.expenses.collectAsState()
    val categories by viewModel.categories.collectAsState()
    var selectedCategory by remember { mutableStateOf("All") }

    // Generate expense cards from real expense data
    val expenseCards = remember(expenses) {
        val totalExpenses = expenses.filter { it.category != "Investment" }
            .sumOf { it.amount }

        val foodExpenses = expenses.filter { it.category == "Food" }
            .sumOf { it.amount }

        val transportExpenses = expenses.filter { it.category == "Transport" }
            .sumOf { it.amount }

        listOf(
            ExpenseCardData(
                title = "Total",
                amount = totalExpenses,
                date = "This Month",
                type = "Expense"
            ),
            ExpenseCardData(
                title = "Food",
                amount = foodExpenses,
                date = "This Month",
                type = "Expense"
            ),
            ExpenseCardData(
                title = "Transport",
                amount = transportExpenses,
                date = "This Month",
                type = "Expense"
            )
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Expense") },
                actions = {
                    IconButton(onClick = onAddExpense) {
                        Icon(
                            imageVector = Icons.Default.AddCircle,
                            contentDescription = "Add Expense"
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Expense Cards section
            item {
                Spacer(modifier = Modifier.height(16.dp))
            }

            // First row of expense cards
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Box(modifier = Modifier.weight(1f)) {
                        if (expenseCards.isNotEmpty()) {
                            ExpenseCard(expenseCards[0])
                        }
                    }
                    Box(modifier = Modifier.weight(1f)) {
                        if (expenseCards.size > 1) {
                            ExpenseCard(expenseCards[1])
                        }
                    }
                }
            }

            // Second row of expense card (if there's a third card)
            if (expenseCards.size > 2) {
                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Box(modifier = Modifier.weight(1f)) {
                            ExpenseCard(expenseCards[2])
                        }
                        // Empty box to maintain grid layout
                        Box(modifier = Modifier.weight(1f)) {}
                    }
                }
            }

            // Add New Expense Button
            item {
                Button(
                    onClick = onAddExpense,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "Add New Expense", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                }
            }

            // Expense History Title
            item {
                Text(
                    text = "Expense History",
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center
                )
            }

            // Category Chips
            item {
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    contentPadding = PaddingValues(vertical = 8.dp)
                ) {
                    items(categories) { category ->
                        FilterChip(
                            selected = selectedCategory == category,
                            onClick = { selectedCategory = category },
                            label = { Text(category) },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = MaterialTheme.colorScheme.primary,
                                selectedLabelColor = Color.White
                            )
                        )
                    }
                }
            }

            // Filtered Expense History List (showing only 5 items)
            val filteredExpenses = if (selectedCategory == "All") {
                expenses
            } else {
                expenses.filter { it.category == selectedCategory }
            }

            filteredExpenses.take(5).forEachIndexed { index, expense ->
                val expenseItem = ExpenseListItem(
                    title = expense.description,
                    amount = expense.amount,
                    category = expense.category,
                    date = expense.date.let {
                        // Format date as needed, e.g., "Today, 2:30 PM"
                        SimpleDateFormat("MMM dd, hh:mm a", Locale.getDefault()).format(it)
                    } ,
                    type = "Expense"
                )
                item {
                    ExpenseListItemView(expenseItem)
                    if (index < filteredExpenses.size - 1) {
                        Divider(
                            modifier = Modifier.padding(vertical = 8.dp),
                            color = Color.LightGray.copy(alpha = 0.5f)
                        )
                    }
                }
            }

            // Add bottom padding
            item {
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
fun ExpenseListItemView(expense: ExpenseListItem) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Category indicator
        val categoryColor = when (expense.category) {
            "Food" -> Color(0xFFFF9800)
            "Transport" -> Color(0xFF2196F3)
            "Utility" -> Color(0xFF4CAF50)
            "Investment" -> Color(0xFF9C27B0)
            else -> Color(0xFF607D8B)
        }

        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(categoryColor.copy(alpha = 0.2f))
                .border(1.dp, categoryColor, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = expense.category.first().toString(),
                color = categoryColor,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = expense.title,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Medium
            )
            Text(
                text = expense.date,
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray
            )
        }

        val amountColor = if (expense.type == "Invest") Color(0xFF4CAF50) else Color(0xFFE57373)
        val amountPrefix = if (expense.type == "Invest") "+" else "-"

        Text(
            text = "$amountPrefix₹${expense.amount}",
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold,
            color = amountColor
        )
    }
}

@Preview
@Composable
fun ExpenseScreenPreview() {
    ExpenseScreen(onAddExpense = {})
}

@Preview
@Composable
fun ExpenseListItemViewPreview() {
    val expense = ExpenseListItem(
        title = "Lunch at Restaurant",
        amount = 32.50,
        category = "Food",
        date = "Today, 2:30 PM",
        type = "Expense"
    )
    ExpenseListItemView(expense)
}

