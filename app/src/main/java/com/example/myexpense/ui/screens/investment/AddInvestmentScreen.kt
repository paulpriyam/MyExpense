package com.example.myexpense.ui.screens.investment

import android.app.DatePickerDialog
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.myexpense.entity.InvestmentEntity
import com.example.myexpense.ui.screens.investment.viewmodel.InvestmentViewModel
import com.example.myexpense.utils.PreferenceManager
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddInvestmentScreen(
    onBackPressed: () -> Unit,
    onSave: () -> Unit,
    onDelete: () -> Unit,
    viewModel: InvestmentViewModel = hiltViewModel()
) {

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Add Investment") },
                navigationIcon = {
                    IconButton(onClick = onBackPressed) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Close Investment Form"
                        )
                    }
                },
                actions = {
                    IconButton(onClick = onDelete) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Delete Investment"
                        )
                    }
                }
            )
        },

        ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.background)
                .verticalScroll(
                    state = rememberScrollState()
                )
        ) {


            val context = LocalContext.current
            val sharedPreferences  = remember{ PreferenceManager(context)}
            var investmentName by remember { mutableStateOf("") }
            var investmentDescription by remember { mutableStateOf("") }
            val investmentTypes = listOf("Stocks", "Bonds", "Mutual Funds","Fixed Deposits")
            var selectedInvestmentType by remember { mutableStateOf("") }
            var isDropdownExpanded by remember { mutableStateOf(false) }
            var investmentDateMillis by remember { mutableStateOf(System.currentTimeMillis()) }
            var investmentAmount by remember { mutableStateOf("") }
            var shareQuantity by remember { mutableStateOf("") }
            var sharePrice by remember { mutableStateOf("") }

            val dateFormatted = SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).format(Date(investmentDateMillis))
            val interestCompounded = listOf("Monthly", "Quarterly", "Half Yearly", "Yearly")
            val selectedInterestCompounded by remember { mutableStateOf("Monthly") }
            var fixedDepositInterestRate by remember { mutableStateOf("") }
            var fixedDepositMaturityAmount by remember { mutableStateOf("") }
            var fixedDepositTenureYear by remember { mutableStateOf("") }
            var fixedDepositTenureMonth by remember { mutableStateOf("") }
            var fixedDepositTenureDay by remember { mutableStateOf("") }
            val sipTypes = listOf("Monthly", "Yearly", "Quarterly", "Half Yearly")
            var selectedSipType by remember { mutableStateOf("Monthly") }
            val calendar = Calendar.getInstance()
            calendar.timeInMillis = investmentDateMillis

            val datePickerDialog = DatePickerDialog(
                context,
                { _, year, month, dayOfMonth ->
                    val cal = Calendar.getInstance()
                    cal.set(year, month, dayOfMonth, 0, 0, 0)
                    cal.set(Calendar.MILLISECOND, 0)
                    investmentDateMillis = cal.timeInMillis
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            )
            Text(
                text = "Investment Name",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(horizontal =16.dp)
            )

            OutlinedTextField(
                value = investmentName,
                onValueChange = {
                    investmentName = it
                },
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth()
                ,supportingText = {
                    Text(text = "Enter the name of the investment")
                },
                shape = RoundedCornerShape(size = 8.dp),
            )
            Spacer(modifier = Modifier.height(8.dp))

            // Investment Description
            Text(
                text = "Investment Description",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(horizontal =16.dp)
            )

            OutlinedTextField(
                value = investmentDescription,
                onValueChange = {
                    investmentDescription = it
                },
                modifier = Modifier
                    .height(100.dp)
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth()
                ,supportingText = {
                    Text(text = "Enter the description of the investment")
                },
                shape = RoundedCornerShape(size = 8.dp),
            )
            Spacer(modifier = Modifier.height(8.dp))

            // Investment Type Dropdown
            Box(
                modifier = Modifier
                    .fillMaxWidth()

            ){

                Column {
                    Text(
                        text = "Investment Type",
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )

                    OutlinedTextField(
                        value = selectedInvestmentType,
                        onValueChange = { },
                        readOnly = true,
                        trailingIcon = {
                            IconButton(
                                onClick = { isDropdownExpanded = true }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.KeyboardArrowDown,
                                    contentDescription = "Select Category",
                                    modifier = Modifier.padding(8.dp)
                                )
                            }
                        },
                        placeholder = { Text("Select category") },
                        supportingText = { Text("Select or add a new expense category") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                            .clickable { isDropdownExpanded = true }
                    )
                }

                DropdownMenu(
                    expanded = isDropdownExpanded, // This should be controlled by a state variable
                    onDismissRequest = { isDropdownExpanded = false },
                    modifier = Modifier.fillMaxWidth(0.9f)
                ) {
                    investmentTypes.forEach { type ->
                        DropdownMenuItem(
                            text = { Text(text = type) },
                            onClick = {
                                selectedInvestmentType = type
                                isDropdownExpanded = false
                            }
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            when(selectedInvestmentType){
                "Stocks" -> {

                    Text(
                        text = "Stock Investment Amount",
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                    OutlinedTextField(
                        value = investmentAmount,
                        onValueChange = {
                            investmentAmount = it
                        },
                        prefix = {
                            Text(text = "₹")
                        },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number
                        ),
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Number of Shares Purchased",
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                    OutlinedTextField(
                        value = shareQuantity,
                        onValueChange = {
                            shareQuantity = it
                        },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number
                        ),
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Price Per Share",
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                    OutlinedTextField(
                        value = sharePrice,
                        onValueChange = {
                            sharePrice = it
                        },
                        prefix = {
                            Text(text = "₹")
                        },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number
                        ),
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .fillMaxWidth()
                    )
                }
                "Bonds" -> {
                    // Bond Investment Amount
                    Text(
                        text = "Bond Investment Amount",
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                    OutlinedTextField(
                        value = investmentAmount,
                        onValueChange = {
                            investmentAmount = it
                        },
                        prefix = {
                            Text(text = "₹")
                        },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number
                        ),
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .fillMaxWidth(),
                        supportingText = {
                            Text(text = "Enter the amount invested in bonds")
                        },
                        shape = RoundedCornerShape(size = 8.dp)
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    // Bond Tenure
                    Text(
                        text = "Bond Tenure",
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                    Row {
                        OutlinedTextField(
                            value = fixedDepositTenureYear,
                            onValueChange = {
                                fixedDepositTenureYear = it
                            },
                            label = { Text("Years") },
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Number
                            ),
                            modifier = Modifier
                                .weight(1f)
                                .padding(horizontal = 16.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        OutlinedTextField(
                            value = fixedDepositTenureMonth,
                            onValueChange = {
                                fixedDepositTenureMonth = it
                            },
                            label = { Text("Months") },
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Number
                            ),
                            modifier = Modifier
                                .weight(1f)
                                .padding(horizontal = 16.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    // Bond Interest Rate
                    Text(
                        text = "Bond Interest Rate",
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                    OutlinedTextField(
                        value = fixedDepositInterestRate,
                        onValueChange = {
                            fixedDepositInterestRate = it
                        },
                        suffix = {
                            Text(text = "%")
                        },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number
                        ),
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .fillMaxWidth(),
                        supportingText = {
                            Text(text = "Enter the interest rate of the bond")
                        },
                        shape = RoundedCornerShape(size = 8.dp)
                    )

                    Spacer(modifier = Modifier.height(8.dp))
                }
                "Mutual Funds" -> {

                    Text(
                        text = "Mutual Fund Amount",
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(horizontal =16.dp)
                    )
                    OutlinedTextField(
                        value = investmentAmount,
                        onValueChange = {
                            investmentAmount = it
                        },
                        prefix = {
                            Text(text = "₹")
                        },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number
                        ),
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .fillMaxWidth()
                        ,supportingText = {
                            Text(text = "Enter the amount of the mutual fund investment")
                        },
                        shape = RoundedCornerShape(size = 8.dp),
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()

                    ){

                        Column {
                            Text(
                                text = "SIP Type",
                                style = MaterialTheme.typography.bodyLarge,
                                modifier = Modifier.padding(horizontal = 16.dp)
                            )

                            OutlinedTextField(
                                value = selectedSipType,
                                onValueChange = { },
                                readOnly = true,
                                trailingIcon = {
                                    IconButton(
                                        onClick = { isDropdownExpanded = true }
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.KeyboardArrowDown,
                                            contentDescription = "Select Category",
                                            modifier = Modifier.padding(8.dp)
                                        )
                                    }
                                },
                                placeholder = { Text("Select SIP Type") },
                                supportingText = { Text("Select the SIP type") },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 16.dp)
                                    .clickable { isDropdownExpanded = true }
                            )
                        }

                        DropdownMenu(
                            expanded = isDropdownExpanded, // This should be controlled by a state variable
                            onDismissRequest = { isDropdownExpanded = false },
                            modifier = Modifier.fillMaxWidth(0.9f)
                        ) {
                            sipTypes.forEach { type ->
                                DropdownMenuItem(
                                    text = { Text(text = type) },
                                    onClick = {
                                        selectedInvestmentType = type
                                        isDropdownExpanded = false
                                    }
                                )
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                }
                "Fixed Deposits" -> {
                    // Fixed Deposit Principal Amount
                    Text(
                        text = "Fixed Deposit Amount",
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(horizontal =16.dp)
                    )
                    OutlinedTextField(
                        value = investmentAmount,
                        onValueChange = {
                            investmentAmount = it
                        },
                        prefix = {
                            Text(text = "₹")
                        },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number
                        ),
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .fillMaxWidth()
                        ,supportingText = {
                            Text(text = "Enter the principal amount of the fixed deposit")
                        },
                        shape = RoundedCornerShape(size = 8.dp),
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    // Fixed Deposit Purchase Date
                    Text(
                        text = "Purchase Date",
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(horizontal =16.dp)
                    )
                    OutlinedTextField(
                        value = dateFormatted,
                        onValueChange = {

                        },
                        trailingIcon = {
                            IconButton(
                                onClick = { datePickerDialog.show() }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.DateRange,
                                    contentDescription = "Select Date"
                                )
                            }
                        },
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .fillMaxWidth()
                        ,supportingText = {
                            Text(text = "Enter the purchase date of the investment")
                        },
                        shape = RoundedCornerShape(size = 8.dp),
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    // Fixed Deposit Interest Rate
                    Text(
                        text = "Fixed Deposit Interest Rate",
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(horizontal =16.dp)
                    )
                    OutlinedTextField(
                        value = fixedDepositInterestRate,
                        onValueChange = {
                            fixedDepositInterestRate = it
                        },
                        suffix = {
                            Text(text = "%")
                        },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number
                        ),
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .fillMaxWidth()
                        ,supportingText = {
                            Text(text = "Enter the interest rate of the fixed deposit")
                        },
                        shape = RoundedCornerShape(size = 8.dp),
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    // Fixed Deposit Duration
                    Text(
                        text = "Fixed Deposit Duration",
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(horizontal =16.dp)
                    )
                    Row {
                        OutlinedTextField(
                            value = fixedDepositTenureYear,
                            onValueChange = {
                                fixedDepositTenureYear = it
                            },
                            label = { Text("Years") },
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Number
                            ),
                            modifier = Modifier
                                .weight(1f)
                                .padding(horizontal = 16.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        OutlinedTextField(
                            value = fixedDepositTenureMonth,
                            onValueChange = {
                                fixedDepositTenureMonth = it
                            },
                            label = { Text("Months") },
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Number
                            ),
                            modifier = Modifier
                                .weight(1f)
                                .padding(horizontal = 16.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        OutlinedTextField(
                            value = fixedDepositTenureDay,
                            onValueChange = {
                                fixedDepositTenureDay = it
                            },
                            label = { Text("Days") },
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Number
                            ),
                            modifier = Modifier
                                .weight(1f)
                                .padding(horizontal = 16.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))


                    // Fixed Deposit interest compounded
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()

                    ){

                        Column {
                            Text(
                                text = "Interest Compounded",
                                style = MaterialTheme.typography.bodyLarge,
                                modifier = Modifier.padding(horizontal = 16.dp)
                            )

                            OutlinedTextField(
                                value = selectedInterestCompounded,
                                onValueChange = { },
                                readOnly = true,
                                trailingIcon = {
                                    IconButton(
                                        onClick = { isDropdownExpanded = true }
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.KeyboardArrowDown,
                                            contentDescription = "Select Category",
                                            modifier = Modifier.padding(8.dp)
                                        )
                                    }
                                },
                                placeholder = { Text("Select category") },
                                supportingText = { Text("Select or add a new expense category") },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 16.dp)
                                    .clickable { isDropdownExpanded = true }
                            )
                        }

                        DropdownMenu(
                            expanded = isDropdownExpanded, // This should be controlled by a state variable
                            onDismissRequest = { isDropdownExpanded = false },
                            modifier = Modifier.fillMaxWidth(0.9f)
                        ) {
                            interestCompounded.forEach { type ->
                                DropdownMenuItem(
                                    text = { Text(text = type) },
                                    onClick = {
                                        selectedInvestmentType = type
                                        isDropdownExpanded = false
                                    }
                                )
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))

                    // Fixed Deposit Maturity Amount
                    Text(
                        text = "Fixed Deposit Maturity Amount",
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(horizontal =16.dp)
                    )
                    OutlinedTextField(
                        value = fixedDepositMaturityAmount,
                        onValueChange = {
                            fixedDepositMaturityAmount = it
                        },
                        prefix = {
                            Text(text = "₹")
                        },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number
                        ),
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .fillMaxWidth(),
                        supportingText = {
                            Text(text = "Enter the maturity amount of the fixed deposit")
                        },
                        shape = RoundedCornerShape(size = 8.dp),
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
                else -> {
                    // Handle other types or show a message
                    Text(
                        text = "Investment Amount",
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(horizontal =16.dp)
                    )

                    OutlinedTextField(
                        value = investmentAmount,
                        onValueChange = {
                            investmentAmount = it
                        },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number
                        ),
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .fillMaxWidth()
                        ,supportingText = {
                            Text(text = "Enter the amount of the investment")
                        },
                        shape = RoundedCornerShape(size = 8.dp),
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Purchase Date",
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(horizontal =16.dp)
                    )

                    OutlinedTextField(
                        value = dateFormatted,
                        onValueChange = {

                        },
                        trailingIcon = {
                            IconButton(
                                onClick = { datePickerDialog.show() }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.DateRange,
                                    contentDescription = "Select Date"
                                )
                            }
                        },
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .fillMaxWidth()
                        ,supportingText = {
                            Text(text = "Enter the purchase date of the investment")
                        },
                        shape = RoundedCornerShape(size = 8.dp),
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
            Spacer(modifier = Modifier.weight(1f))
            Button(
                onClick = {
                    // Create investment entity with appropriate fields based on type
                    val investment = InvestmentEntity(
                        investmentName = investmentName,
                        investmentAmount = investmentAmount.toDoubleOrNull() ?: 0.0,
                        investmentDate = dateFormatted,
                        investmentType = selectedInvestmentType,
                        investmentDescription = investmentDescription,
                        userId = sharedPreferences.getUserId().orEmpty(), // Get user ID from shared preferences
                        // Default values for fields that may not be used based on investment type
                        sipType = if (selectedInvestmentType == "Mutual Funds") selectedSipType else "",
                        sipAmount = if (selectedInvestmentType == "Mutual Funds") investmentAmount.toDoubleOrNull() ?: 0.0 else 0.0,
                        fdType = if (selectedInvestmentType == "Fixed Deposits") selectedInterestCompounded else "",
                        fdAmount = if (selectedInvestmentType == "Fixed Deposits") investmentAmount.toDoubleOrNull() ?: 0.0 else 0.0,
                        fdDuration = if (selectedInvestmentType == "Fixed Deposits") {
                            "${fixedDepositTenureYear}y ${fixedDepositTenureMonth}m ${fixedDepositTenureDay}d"
                        } else "",
                        fdInterest = if (selectedInvestmentType == "Fixed Deposits" || selectedInvestmentType == "Bonds")
                            fixedDepositInterestRate.toDoubleOrNull() ?: 0.0 else 0.0,
                        fdMaturityDate = if (selectedInvestmentType == "Fixed Deposits") {
                            // Calculate maturity date - simplified for now
                            val maturityCalendar = Calendar.getInstance()
                            maturityCalendar.timeInMillis = investmentDateMillis
                            maturityCalendar.add(Calendar.YEAR, fixedDepositTenureYear.toIntOrNull() ?: 0)
                            maturityCalendar.add(Calendar.MONTH, fixedDepositTenureMonth.toIntOrNull() ?: 0)
                            maturityCalendar.add(Calendar.DAY_OF_MONTH, fixedDepositTenureDay.toIntOrNull() ?: 0)
                            SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).format(maturityCalendar.time)
                        } else "",
                        fdMaturityAmount = if (selectedInvestmentType == "Fixed Deposits")
                            fixedDepositMaturityAmount.toDoubleOrNull() ?: 0.0 else 0.0,
                        sharePrice = if (selectedInvestmentType == "Stocks") sharePrice.toDoubleOrNull() ?: 0.0 else 0.0,
                        shareQuantity = if (selectedInvestmentType == "Stocks") shareQuantity.toDoubleOrNull() ?: 0.0 else 0.0
                    )

                    // Save investment to database through view model
                    viewModel.insertInvestment(investment)
                    onSave()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                shape = RoundedCornerShape(size = 8.dp)
            ) {
                Text(text = "Save Investment")
            }

        }
    }
}

@Preview
@Composable
fun AddInvestmentScreenPreview() {
    AddInvestmentScreen(
        onBackPressed = {},
        onSave = {},
        onDelete = {}
    )
}