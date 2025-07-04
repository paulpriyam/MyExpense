package com.example.myexpense.ui.screens.investment

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myexpense.entity.InvestmentEntity
import com.example.myexpense.ui.components.ExpenseCard
import com.example.myexpense.ui.components.InvestmentTypePieChart
import com.example.myexpense.ui.components.MonthlyInvestmentBarChart
import com.example.myexpense.ui.screens.expense.ExpenseCardData
import com.example.myexpense.ui.screens.expense.ExpenseListItem
import kotlin.math.ceil
import kotlin.text.first

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InvestmentScreen(
    onAddInvestment: () -> Unit = { /* Default no-op */ }
) {

    val investmentCards = listOf(
        ExpenseCardData(
            title = "Total Investment",
            amount = 10000.0,
            date = "2023-10-01",
            type = "Investment"
        ),
        ExpenseCardData(
            title = "Mutual Fund Investment",
            amount = 10000.0,
            date = "2023-10-01",
            type = "Investment"
        ),
        ExpenseCardData(
            title = "Fixed Deposit Investment",
            amount = 10000.0,
            date = "2023-10-01",
            type = "Investment"
        ),
        ExpenseCardData(
            title = "Alternate Investment",
            amount = 10000.0,
            date = "2023-10-01",
            type = "Investment"
        ),
        ExpenseCardData(
            title = "Bond Investment",
            amount = 10000.0,
            date = "2023-10-01",
            type = "Investment"
        )
    )
    val investments = listOf(
        InvestmentEntity(
            investmentName = "Mutual Fund",
            investmentAmount = 10000.0,
            investmentDate = "2023-10-01",
            investmentType = "Mutual Fund",
            userId = "1",
            investmentDescription = "Mutual Fund Investment",
        ),
        InvestmentEntity(
            investmentName = "Mutual Fund",
            investmentAmount = 10000.0,
            investmentDate = "2023-10-01",
            investmentType = "Mutual Fund",
            userId = "1",
            investmentDescription = "Mutual Fund Investment",
        ),
        InvestmentEntity(
            investmentName = "Bonds",
            investmentAmount = 10000.0,
            investmentDate = "2023-10-01",
            investmentType = "Bonds",
            userId = "1",
            investmentDescription = "Mutual Fund Investment",
        ),
        InvestmentEntity(
            investmentName = "Stocks",
            investmentAmount = 10000.0,
            investmentDate = "2023-10-01",
            investmentType = "Stocks",
            userId = "1",
            investmentDescription = "Mutual Fund Investment",
        ),
        InvestmentEntity(
            investmentName = "Fixed Deposit",
            investmentAmount = 10000.0,
            investmentDate = "2023-10-01",
            investmentType = "Fixed Deposit",
            userId = "1",
            investmentDescription = "Mutual Fund Investment",
        ),
        InvestmentEntity(
            investmentName = "Mutual Fund",
            investmentAmount = 10000.0,
            investmentDate = "2023-10-01",
            investmentType = "Stocks",
            userId = "1",
            investmentDescription = "Mutual Fund Investment",
        ),
    )
    // Calculate rows needed to display all investments with at most 2 per row
    val investmentsCount = investmentCards.size
    val investmentTypes = listOf("All", "Mutual Fund", "Fixed Deposit", "Alternate", "Bonds")
    var selectedInvestType by remember { mutableStateOf(investmentTypes[0]) }

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
        // Filter investments based on selection
        val filteredInvestments = if (selectedInvestType == "All") {
            investments
        } else {
            investments.filter { it.investmentType == selectedInvestType }
        }

        // Use a single LazyColumn for all content
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentPadding = PaddingValues(bottom = 16.dp)
        ) {
            // Charts section
            item {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Investment Analytics",
                    modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp),
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
            }

            item {
                // Pie chart for investment type breakdown
                InvestmentTypePieChart(
                    investments = investments,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            item {
                // Monthly investment bar chart  
                MonthlyInvestmentBarChart(
                    investments = investments,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            // Section title
            item {
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Investment Summary",
                    modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp),
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
            }

            // Investment cards section - arranged in rows with 2 cards each
            for (i in 0 until investmentsCount step 2) {
                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp)
                    ) {
                        // First card in the row (always present)
                        ExpenseCard(
                            modifier = Modifier
                                .weight(1f)
                                .padding(8.dp),
                            expenseData = investmentCards[i],
                            onClick = { /* Handle click */ }
                        )

                        // Second card in the row (if available)
                        if (i + 1 < investmentsCount) {
                            ExpenseCard(
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(8.dp),
                                expenseData = investmentCards[i + 1],
                                onClick = { /* Handle click */ }
                            )
                        } else {
                            // Empty space to maintain layout balance when there's only one card in the last row
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(8.dp)
                            )
                        }
                    }
                }
            }

            // Filter chips row
            item {
                LazyRow(
                    contentPadding = PaddingValues(horizontal = 8.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp)
                ) {
                    items(investmentTypes.size) { investmentTypeIndex ->
                        FilterChip(
                            selected = selectedInvestType == investmentTypes[investmentTypeIndex],
                            modifier = Modifier.padding(end = 8.dp),
                            onClick = { selectedInvestType = investmentTypes[investmentTypeIndex] },
                            label = { Text(investmentTypes[investmentTypeIndex]) }
                        )
                    }
                }
            }

            // Investment list items
            items(filteredInvestments.take(5).size) { index ->
                InvestmentListItemView(investment = filteredInvestments[index])
            }
        }
    }
}

@Composable
fun InvestmentListItemView(investment: InvestmentEntity) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Category indicator
        val categoryColor = when (investment.investmentType) {
            "Mutual Fund" -> Color(0xFFFF9800)
            "Fixed Deposit" -> Color(0xFF2196F3)
            "Stocks" -> Color(0xFF4CAF50)
            "Bonds" -> Color(0xFF9C27B0)
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
                text = investment.investmentType.first().toString(),
                color = categoryColor,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = investment.investmentName,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Medium
            )
            Text(
                text = investment.investmentDate,
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray
            )
        }

        Text(
            text = "₹${investment.investmentAmount}",
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF4CAF50)
        )
    }
}

@Preview
@Composable
fun InvestmentScreenPreview() {
    InvestmentScreen()
}

