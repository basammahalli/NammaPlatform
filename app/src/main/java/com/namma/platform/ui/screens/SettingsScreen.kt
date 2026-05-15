package com.namma.platform.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Language
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.namma.platform.ui.theme.NammaBlue
import com.namma.platform.utils.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(onBack: () -> Unit) {
    val langManager = LocalLanguageManager.current
    val isKn = langManager.currentLanguage == AppLanguage.KANNADA
    
    var isDarkMode by remember { mutableStateOf(false) }
    var isNotificationsEnabled by remember { mutableStateOf(true) }
    var ttsSpeed by remember { mutableFloatStateOf(1.0f) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (isKn) "ಸೆಟ್ಟಿಂಗ್‌ಗಳು" else "Settings") },
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
                .padding(16.dp)
        ) {
            Text(if (isKn) "ಅಪ್ಲಿಕೇಶನ್ ಸೆಟ್ಟಿಂಗ್‌ಗಳು" else "App Settings", style = MaterialTheme.typography.titleLarge, modifier = Modifier.padding(bottom = 24.dp))
            
            // Dark Mode Toggle
            SettingsToggleItem(
                label = if (isKn) "ಡಾರ್ಕ್ ಮೋಡ್" else "Dark Mode",
                checked = isDarkMode,
                onCheckedChange = { isDarkMode = it }
            )
            
            // Notifications Toggle
            SettingsToggleItem(
                label = if (isKn) "ಅಧಿಸೂಚನೆಗಳು" else "Notifications",
                checked = isNotificationsEnabled,
                onCheckedChange = { isNotificationsEnabled = it }
            )
            
            Spacer(modifier = Modifier.height(24.dp))
            
            // TTS Speed Slider
            Text(if (isKn) "ಘೋಷಣೆ ವೇಗ (TTS Speed)" else "Announcement Speed (TTS)", style = MaterialTheme.typography.bodyLarge)
            Slider(
                value = ttsSpeed,
                onValueChange = { ttsSpeed = it },
                valueRange = 0.5f..2.0f,
                modifier = Modifier.padding(vertical = 8.dp)
            )
            Text(text = String.format("%.1fx", ttsSpeed), modifier = Modifier.padding(bottom = 16.dp))
            
            Spacer(modifier = Modifier.weight(1f))
            
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(if (isKn) "ಬಗ್ಗೆ (About)" else "About Namma Platform", fontWeight = FontWeight.Bold)
                    Text(if (isKn) "ಆವೃತ್ತಿ: 1.0.0" else "Version: 1.0.0", fontSize = 12.sp)
                    Text(if (isKn) "ಈ ಅಪ್ಲಿಕೇಶನ್ ಪ್ರಯಾಣಿಕರಿಗೆ ಸುಲಭವಾಗಿ ನಿಲ್ದಾಣದ ಮಾಹಿತಿ ಪಡೆಯಲು ಸಹಾಯ ಮಾಡುತ್ತದೆ." else "This app helps passengers easily get station and coach information.", fontSize = 14.sp)
                }
            }
        }
    }
}

@Composable
fun SettingsToggleItem(label: String, checked: Boolean, onCheckedChange: (Boolean) -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(label, fontSize = 18.sp)
        Switch(checked = checked, onCheckedChange = onCheckedChange)
    }
}
