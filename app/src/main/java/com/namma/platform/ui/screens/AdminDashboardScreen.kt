package com.namma.platform.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.res.stringResource
import com.namma.platform.R
import com.namma.platform.ui.theme.NammaBlue

import androidx.compose.material.icons.filled.Language
import com.namma.platform.utils.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminDashboardScreen(
    onLogout: () -> Unit,
    onNavigateToSettings: () -> Unit,
    onNavigateToProfile: () -> Unit,
    onNavigateToAddTrain: () -> Unit
) {
    val langManager = LocalLanguageManager.current
    val isKn = langManager.currentLanguage == AppLanguage.KANNADA

    val user = SessionManager.currentUser
    val adminName = user?.fullName ?: "Admin"

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (isKn) "ನಿರ್ವಾಹಕ ಡ್ಯಾಶ್‌ಬೋರ್ಡ್" else "Admin Dashboard") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = NammaBlue,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    actionIconContentColor = MaterialTheme.colorScheme.onPrimary
                ),
                actions = {
                    IconButton(onClick = { langManager.toggleLanguage() }) {
                        Icon(Icons.Filled.Language, contentDescription = "Language")
                    }
                    IconButton(onClick = onNavigateToProfile) {
                        Icon(Icons.Filled.Person, contentDescription = "Profile")
                    }
                    TextButton(onClick = {
                        SessionManager.logout()
                        onLogout()
                    }) {
                        Text(if (isKn) "ನಿರ್ಗಮನ" else "Logout", color = MaterialTheme.colorScheme.onPrimary)
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            Text(if (isKn) "$adminName ಅವರಿಗೆ ಸ್ವಾಗತ" else "Welcome, $adminName", fontSize = 24.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(bottom = 24.dp))
            
            Button(
                onClick = onNavigateToAddTrain,
                modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
            ) {
                Text(if (isKn) "ರೈಲು ವಿವರ ಸೇರಿಸಿ" else "Add Train Details")
            }
            
            Button(
                onClick = { /* TODO: Implement add platform */ },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(if (isKn) "ಪ್ಲಾಟ್‌ಫಾರ್ಮ್ ವಿವರ ಸೇರಿಸಿ" else "Add Platform Details")
            }
        }
    }
}
