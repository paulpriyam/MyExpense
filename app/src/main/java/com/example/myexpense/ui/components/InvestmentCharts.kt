package com.example.myexpense.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.myexpense.entity.InvestmentEntity
import com.patrykandpatrick.vico.compose.axis.horizontal.rememberBottomAxis
import com.patrykandpatrick.vico.compose.axis.vertical.rememberStartAxis
import com.patrykandpatrick.vico.compose.chart.Chart
import com.patrykandpatrick.vico.compose.chart.column.columnChart
import com.patrykandpatrick.vico.compose.style.currentChartStyle
import com.patrykandpatrick.vico.core.entry.FloatEntry
import com.patrykandpatrick.vico.core.entry.entryModelOf
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt

/**
 * Data class for monthly investment data
 */
data class MonthlyInvestment(
    val month: String,
    val amount: Float,
    val monthIndex: Int // 0-based index for the month (0 = Jan, 11 = Dec)
)

/**
 * Data class for investment by type data
 */
data class InvestmentByType(
    val type: String,
    val amount: Float,
    val color: Color
)

/**
 * Aggregates investment data by month for the bar chart
 */
fun aggregateInvestmentsByMonth(investments: List<InvestmentEntity>): List<MonthlyInvestment> {
    // Group investments by month and sum amounts
    val monthlyInvestments = mutableMapOf<Int, Float>()
    val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    
    investments.forEach { investment ->
        try {
            val date = dateFormat.parse(investment.investmentDate)
            val calendar = Calendar.getInstance()
            calendar.time = date ?: Date()
            val monthIndex = calendar.get(Calendar.MONTH) // Already 0-based (0 = Jan, 11 = Dec)
            val currentAmount = monthlyInvestments.getOrDefault(monthIndex, 0f)
            monthlyInvestments[monthIndex] = currentAmount + investment.investmentAmount.toFloat()
        } catch (e: Exception) {
            // Handle date parsing error - skip this investment
        }
    }
    
    // Convert to list of MonthlyInvestment objects
    val months = listOf("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec")
    return months.mapIndexed { index, month ->
        MonthlyInvestment(
            month = month,
            amount = monthlyInvestments.getOrDefault(index, 0f),
            monthIndex = index
        )
    }
}

/**
 * Aggregates investment data by type for the pie chart
 */
fun aggregateInvestmentsByType(investments: List<InvestmentEntity>): List<InvestmentByType> {
    // Group investments by type and sum amounts
    val investmentsByType = investments.groupBy { it.investmentType }
        .map { (type, invests) ->
            InvestmentByType(
                type = type,
                amount = invests.sumOf { it.investmentAmount }.toFloat(),
                color = getColorForInvestmentType(type)
            )
        }
        .sortedByDescending { it.amount }
    
    return investmentsByType
}

/**
 * Get a consistent color for each investment type
 */
fun getColorForInvestmentType(type: String): Color {
    return when (type) {
        "Mutual Fund" -> Color(0xFF4CAF50) // Green
        "Stocks" -> Color(0xFF2196F3) // Blue
        "Fixed Deposit" -> Color(0xFFFF9800) // Orange
        "Bonds" -> Color(0xFF9C27B0) // Purple
        "Alternate" -> Color(0xFFE91E63) // Pink
        else -> Color(0xFF607D8B) // Blue Grey
    }
}

/**
 * Monthly Investment Bar Chart
 */
@Composable
fun MonthlyInvestmentBarChart(
    investments: List<InvestmentEntity>,
    modifier: Modifier = Modifier
) {
    val monthlyData = aggregateInvestmentsByMonth(investments)
    
    // Create chart entries
    val entries = monthlyData.map { FloatEntry(it.monthIndex.toFloat(), it.amount) }
    val entryModel = entryModelOf(entries)
    
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(280.dp)
            .padding(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(
                text = "Monthly Investment Overview",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            
            Chart(
                chart = columnChart(),
                model = entryModel,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                startAxis = rememberStartAxis(
                    valueFormatter = { value, _ ->
                        "₹${value.roundToInt()}"
                    }
                ),
                bottomAxis = rememberBottomAxis(
                    valueFormatter = { value, _ ->
                        monthlyData.find { it.monthIndex == value.toInt() }?.month ?: ""
                    }
                )
            )
        }
    }
}

/**
 * Investment Type Distribution Chart (using simple visual representation)
 */
@Composable
fun InvestmentTypePieChart(
    investments: List<InvestmentEntity>,
    modifier: Modifier = Modifier
) {
    val investmentsByType = aggregateInvestmentsByType(investments)
    val totalInvestment = investmentsByType.sumOf { it.amount.toDouble() }.toFloat()
    
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = "Investment Distribution",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // Simple visual representation using progress bars
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                investmentsByType.forEach { investmentType ->
                    val percentage =
                        if (totalInvestment > 0) (investmentType.amount / totalInvestment) else 0f

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(12.dp)
                                        .background(investmentType.color, CircleShape)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = investmentType.type,
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            }
                            Text(
                                text = "${(percentage * 100).roundToInt()}% (₹${investmentType.amount.roundToInt()})",
                                style = MaterialTheme.typography.bodySmall,
                                fontWeight = FontWeight.Medium
                            )
                        }

                        Spacer(modifier = Modifier.height(4.dp))

                        // Progress bar
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(8.dp)
                                .background(Color.Gray.copy(alpha = 0.2f), RoundedCornerShape(4.dp))
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth(percentage)
                                    .height(8.dp)
                                    .background(investmentType.color, RoundedCornerShape(4.dp))
                            )
                        }
                    }
                }
            }
        }
    }
}
