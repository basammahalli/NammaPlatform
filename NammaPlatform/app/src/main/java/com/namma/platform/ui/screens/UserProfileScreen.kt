package com.namma.platform.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.namma.platform.ui.theme.NammaBlue
import com.namma.platform.ui.theme.NammaYellow

import androidx.compose.material.icons.filled.Language
import com.namma.platform.utils.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserProfileScreen(isAdmin: Boolean = false, onBack: () -> Unit, onLogout: () -> Unit) {
    val langManager = LocalLanguageManager.current
    val isKn = langManager.currentLanguage == AppLanguage.KANNADA
    
    val user = SessionManager.currentUser
    val name = user?.fullName ?: (if (isKn) "ಅತಿಥಿ" else "Guest")
    val phone = user?.phone ?: "---"
    val email = user?.email ?: "---"
    val role = if (user?.isAdmin == true) (if (isKn) "ನಿರ್ವಾಹಕ" else "Admin") else (if (isKn) "ಪ್ರಯಾಣಿಕ" else "Passenger")

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (isAdmin) (if (isKn) "ನಿರ್ವಾಹಕ ಪ್ರೊಫೈಲ್" else "Admin Profile") else (if (isKn) "ಬಳಕೆದಾರ ಪ್ರೊಫೈಲ್" else "User Profile")) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = { langManager.toggleLanguage() }) {
                        Icon(Icons.Filled.Language, contentDescription = "Language")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = NammaBlue,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
                    actionIconContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Profile Avatar Placeholder
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .background(if (isAdmin) NammaBlue else NammaYellow, shape = CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Filled.Person, contentDescription = "Avatar", modifier = Modifier.size(60.dp), tint = if (isAdmin) NammaYellow else NammaBlue)
            }
            
            Spacer(modifier = Modifier.height(24.dp))
            
            ProfileDetailItem(icon = Icons.Filled.Person, label = if (isKn) "ಹೆಸರು" else "Name", value = name)
            ProfileDetailItem(icon = Icons.Filled.Person, label = if (isKn) "ಪಾತ್ರ" else "Role", value = role)
            ProfileDetailItem(icon = Icons.Filled.Phone, label = if (isKn) "ಮೊಬೈಲ್ ಸಂಖ್ಯೆ" else "Phone Number", value = phone)
            ProfileDetailItem(icon = Icons.Filled.Email, label = if (isKn) "ಇಮೇಲ್" else "Email", value = email)
            ProfileDetailItem(icon = Icons.Filled.Language, label = if (isKn) "ಭಾಷಾ ಆದ್ಯತೆ" else "Language Preference", value = if (isKn) "ಕನ್ನಡ" else "English")
            
            Spacer(modifier = Modifier.weight(1f))
            
            Button(
                onClick = {
                    SessionManager.logout()
                    onLogout()
                },
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error),
                modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
            ) {
                Text(if (isKn) "ನಿರ್ಗಮಿಸಿ" else "Logout")
            }
        }
    }
}

@Composable
fun ProfileDetailItem(icon: ImageVector, label: String, value: String) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(icon, contentDescription = label, tint = NammaBlue, modifier = Modifier.size(28.dp))
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(label, fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                Text(value, fontSize = 16.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)
            }
        }
    }
}
