package com.example.myexpense.ui.screens.expense

import android.app.DatePickerDialog
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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.myexpense.entity.ExpenseEntity
import com.example.myexpense.utils.PreferenceManager
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.UUID
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddExpenseScreen(
    onClose: () -> Unit = {}
) {
    val viewModel: ExpenseViewModel = hiltViewModel()
    val context = LocalContext.current
    val preferenceManager = remember { PreferenceManager(context) }
    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    // Collect categories from ViewModel
    val categories by viewModel.categories.collectAsState()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Add New Expense",
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onClose) {
                        Icon(
                            imageVector = androidx.compose.material.icons.Icons.Default.Close,
                            contentDescription = "Close"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White,
                    titleContentColor = Color.Black
                )
            )
        }
    ) { innerPadding ->

        var amount by remember { mutableStateOf("") }
        var category by remember { mutableStateOf("") }
        var description by remember { mutableStateOf("") }
        var dateMillis by remember { mutableStateOf(System.currentTimeMillis()) }

        // States for category dropdown
        var isDropdownExpanded by remember { mutableStateOf(false) }

        // State for new category dialog
        var showNewCategoryDialog by remember { mutableStateOf(false) }
        var newCategoryName by remember { mutableStateOf("") }

        val context = LocalContext.current
        val dateFormatted = SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).format(Date(dateMillis))

        val calendar = Calendar.getInstance()
        calendar.timeInMillis = dateMillis
        val datePickerDialog = DatePickerDialog(
            context,
            { _, year, month, dayOfMonth ->
                val cal = Calendar.getInstance()
                cal.set(year, month, dayOfMonth, 0, 0, 0)
                cal.set(Calendar.MILLISECOND, 0)
                dateMillis = cal.timeInMillis
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )

        // New Category Dialog
        if (showNewCategoryDialog) {
            AlertDialog(
                onDismissRequest = { showNewCategoryDialog = false },
                title = { Text("Add New Category") },
                text = {
                    OutlinedTextField(
                        value = newCategoryName,
                        onValueChange = { newCategoryName = it },
                        label = { Text("Category Name") },
                        modifier = Modifier.fillMaxWidth()
                    )
                },
                confirmButton = {
                    TextButton(
                        onClick = {
                            if (newCategoryName.isNotBlank()) {
                                viewModel.addCategory(newCategoryName)
                                category = newCategoryName
                                newCategoryName = ""
                                showNewCategoryDialog = false
                            }
                        }
                    ) {
                        Text("Add")
                    }
                },
                dismissButton = {
                    TextButton(
                        onClick = { showNewCategoryDialog = false }
                    ) {
                        Text("Cancel")
                    }
                }
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            OutlinedTextField(
                value = amount,
                onValueChange = { newAmount ->
                    // Handle amount input change
                    amount = newAmount
                },
                keyboardOptions = KeyboardOptions(keyboardType = androidx.compose.ui.text.input.KeyboardType.Number),
                label = { Text("Amount") },
                placeholder = { Text("Enter amount") },
                supportingText = { Text("Enter the expense amount") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))

            // Category dropdown with add new category option
            Box(modifier = Modifier.fillMaxWidth()) {
                OutlinedTextField(
                    value = category,
                    onValueChange = { },
                    label = { Text("Category") },
                    readOnly = true,
                    trailingIcon = {
                        IconButton(
                            onClick = { isDropdownExpanded = true }
                        ) {
                            Icon(
                                imageVector = androidx.compose.material.icons.Icons.Default.KeyboardArrowDown,
                                contentDescription = "Select Category",
                                modifier = Modifier.padding(8.dp)
                            )
                        }
                    },
                    placeholder = { Text("Select category") },
                    supportingText = { Text("Select or add a new expense category") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { isDropdownExpanded = true }
                )

                DropdownMenu(
                    expanded = isDropdownExpanded,
                    onDismissRequest = { isDropdownExpanded = false },
                    modifier = Modifier.fillMaxWidth(0.9f)
                ) {
                    categories.filter { it != "All" }.forEach { categoryName ->
                        DropdownMenuItem(
                            text = { Text(categoryName) },
                            onClick = {
                                category = categoryName
                                isDropdownExpanded = false
                            }
                        )
                    }

                    // Add new category option
                    DropdownMenuItem(
                        text = {
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Add,
                                    contentDescription = "Add new category"
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text("Add new category")
                            }
                        },
                        onClick = {
                            isDropdownExpanded = false
                            showNewCategoryDialog = true
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = dateFormatted,
                onValueChange = { },
                readOnly = true,
                trailingIcon = {
                    IconButton(
                        onClick = { datePickerDialog.show() }
                    ) {
                        Icon(
                            imageVector = androidx.compose.material.icons.Icons.Default.DateRange,
                            contentDescription = "Select Date",
                            modifier = Modifier.padding(8.dp)
                        )
                    }
                },
                label = { Text("Date") },
                placeholder = { Text("Enter date") },
                supportingText = { Text("Enter the expense date") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = description,
                onValueChange = { newDescription ->
                    // Handle description input change
                    description = newDescription
                },
                label = { Text("Description") },
                placeholder = { Text("Enter description") },
                supportingText = { Text("Enter a brief description of the expense") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.weight(1F))
            Button(
                onClick = {
                    // Get current user ID from shared preferences
                    val userId = preferenceManager.getUserId()

                    // Validate inputs
                    if (amount.isBlank() || category.isBlank() || description.isBlank() || userId == null) {
                        coroutineScope.launch {
                            snackbarHostState.showSnackbar("Please fill all fields")
                        }
                        return@Button
                    }

                    val amountValue = try {
                        amount.toDouble()
                    } catch (e: NumberFormatException) {
                        coroutineScope.launch {
                            snackbarHostState.showSnackbar("Invalid amount format")
                        }
                        return@Button
                    }

                    // Create expense entity
                    val expense = ExpenseEntity(
                        id = UUID.randomUUID().toString(),
                        amount = amountValue,
                        description = description,
                        date = dateMillis,
                        category = category,
                        userId = userId
                    )

                    // Save to database using ViewModel
                    viewModel.insertExpense(expense)

                    coroutineScope.launch {
                        snackbarHostState.showSnackbar("Expense saved successfully")
                    }

                    // Optional: Close screen or clear fields
                    onClose()
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF0028EE),
                    contentColor = Color.White
                )
            ) {
                Text(text = "Save Expense")
            }
        }
    }
}

@Composable
@Preview
fun AddExpenseScreenPreview() {
    AddExpenseScreen()
}

