package com.example.myexpense.ui.screens.settings

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myexpense.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(onEditProfile: () -> Unit = {}) {
    Scaffold (
        topBar = {
            TopAppBar(
                title = { Text(text = "Settings") },
                navigationIcon = {
                    IconButton(onClick = { /* Handle back navigation */ }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer, // Replace with your theme color
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer // Replace with your theme text color
                ),
                actions = {
                    IconButton(onClick = onEditProfile) {
                        Icon(imageVector = Icons.Default.Settings, contentDescription = "Edit Profile")
                    }
                }
            )
        },
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background) // Replace with your theme background color
    ) {innerPadding ->
        SettingsContent(modifier = Modifier.padding(innerPadding))
    }
}

@Preview
@Composable
fun SettingsScreenPreview() {
    SettingsScreen()
}

@Composable
fun SettingsContent(modifier: Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .padding(top = 32.dp)
                .size(120.dp)
                .clip(CircleShape)
                .border(2.dp, color = androidx.compose.ui.graphics.Color.Gray)
                .padding(2.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_profile), // Replace with your settings icon
                contentDescription = "profile image",
                modifier = Modifier.fillMaxSize()
            )
        }
        Text(
            text = "Priyam Paul",
            style = androidx.compose.material3.MaterialTheme.typography.headlineMedium
        )
        Text(
            text = "@ppaul2204@gmail.com",
            style = androidx.compose.material3.MaterialTheme.typography.bodyLarge
        )
        SettingsItem(
            title = "Personal Information",
            subtitle = "Manage your personal information",
            image = Icons.Default.AccountCircle,
            onClick = { /* Handle click */ }
        )

        SettingsItem(
            title = "Security",
            subtitle = "Manage your security settings",
            image = Icons.Default.Settings,
            onClick = { /* Handle click */ }
        )

        SettingsItem(
            title = "Notifications",
            subtitle = "Manage your notification preferences",
            image = Icons.Default.Notifications,
            onClick = { /* Handle click */ }
        )
        SettingsItem(
            title = "Privacy",
            subtitle = "Manage your privacy settings",
            image = Icons.Default.Lock,
            onClick = { /* Handle click */ }
        )

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = {},
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 24.dp)
        ) {
            Icon(
                imageVector = Icons.Default.ExitToApp,
                contentDescription = null
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "Logout")
        }
    }
}

@Composable
@Preview
fun SettingsItem(
    title: String = "Personal Information",
    subtitle: String = "Manage your personal information",
    image: ImageVector = Icons.Default.AccountCircle,
    onClick: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .background(color = Color(0xFFF5F5F5)),
        verticalAlignment = androidx.compose.ui.Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
        ) {
            Image(
                imageVector = image,
                contentDescription = "Settings Icon",
                modifier = Modifier
                    .size(24.dp)
            )

            Column(
                modifier = Modifier
                    .padding(8.dp)
            ) {
                Text(
                    text = title,
                    style = androidx.compose.material3.MaterialTheme.typography.labelMedium
                )

                Text(
                    text = subtitle,
                    style = androidx.compose.material3.MaterialTheme.typography.labelSmall
                )
            }
        }

        Image(
            imageVector = Icons.Default.ArrowForward,
            contentDescription = "arrow",
            modifier = Modifier
                .size(24.dp)
                .padding(end = 8.dp)
        )
    }
}

