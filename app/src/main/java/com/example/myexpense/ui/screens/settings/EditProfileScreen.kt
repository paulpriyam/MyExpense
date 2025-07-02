package com.example.myexpense.ui.screens.settings

import android.app.DatePickerDialog
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.myexpense.ui.screens.settings.viewmodel.SettingsScreenViewModel
import java.text.SimpleDateFormat
import java.util.*
import kotlinx.coroutines.launch

@Composable
fun EditProfileScreen(
    viewModel: SettingsScreenViewModel = hiltViewModel(),
    onProfileSaved: () -> Unit = {}
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    // Collect profile state from ViewModel
    val profile by viewModel.profileState.collectAsState()

    // Initialize state with values from profile
    var name by remember { mutableStateOf(profile?.name ?: "") }
    var email by remember { mutableStateOf(profile?.email ?: "") }
    var phoneNumber by remember { mutableStateOf(profile?.phoneNumber ?: "") }
    var dobMillis by remember { mutableStateOf<Long?>(profile?.dob?.takeIf { it > 0 }) }

    // Update state values when profile changes
    LaunchedEffect(profile) {
        profile?.let {
            name = it.name ?: ""
            email = it.email ?: ""
            phoneNumber = it.phoneNumber ?: ""
            dobMillis = it.dob.takeIf { dob -> dob > 0 }
        }
    }

    val dobFormatted = dobMillis?.let {
        SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).format(Date(it))
    } ?: "Select Date of Birth"

    val calendar = Calendar.getInstance()
    dobMillis?.let { calendar.timeInMillis = it }

    val datePickerDialog = DatePickerDialog(
        context,
        { _, year, month, dayOfMonth ->
            val cal = Calendar.getInstance()
            cal.set(year, month, dayOfMonth, 0, 0, 0)
            cal.set(Calendar.MILLISECOND, 0)
            dobMillis = cal.timeInMillis
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(text = "Edit Profile", style = MaterialTheme.typography.headlineSmall)
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Name") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = phoneNumber,
            onValueChange = { phoneNumber = it },
            label = { Text("Phone Number") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedButton(
            onClick = { datePickerDialog.show() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(dobFormatted)
        }
        Spacer(modifier = Modifier.weight(1f))
        Button(
            onClick = {
                val updatedProfile = com.example.myexpense.entity.ProfileEntity(
                    name = name,
                    email = email,
                    phoneNumber = phoneNumber,
                    dob = dobMillis ?: 0L
                )
                coroutineScope.launch {
                    viewModel.saveOrUpdateProfile(updatedProfile)
                    onProfileSaved() // Call the callback when profile is saved
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Save")
        }
    }
}

@Preview
@Composable
fun EditProfileScreenPreview() {
    EditProfileScreen()
}
