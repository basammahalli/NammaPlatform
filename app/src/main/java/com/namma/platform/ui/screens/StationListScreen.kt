package com.namma.platform.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.res.stringResource
import com.namma.platform.R
import com.namma.platform.data.Station
import com.namma.platform.ui.theme.NammaBlue

import androidx.compose.material.icons.filled.Language
import com.namma.platform.utils.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StationListScreen(
    stations: List<Station>,
    onStationClick: (String) -> Unit,
    onNavigateToSettings: () -> Unit,
    onNavigateToProfile: () -> Unit
) {
    var searchQuery by remember { mutableStateOf("") }
    val langManager = LocalLanguageManager.current
    val isKn = langManager.currentLanguage == AppLanguage.KANNADA
    
    val filteredStations = if (searchQuery.isEmpty()) {
        stations
    } else {
        stations.filter { 
            it.nameKannada.contains(searchQuery, ignoreCase = true) || 
            it.nameEnglish.contains(searchQuery, ignoreCase = true) 
        }
    }

    val user = SessionManager.currentUser
    val firstName = user?.fullName?.split(" ")?.firstOrNull() ?: "User"

    Scaffold(
        topBar = {
            TopAppBar(
                title = { 
                    Column {
                        Text(if (isKn) "$firstName ಅವರಿಗೆ ಸ್ವಾಗತ" else "Welcome, $firstName", fontWeight = FontWeight.Bold)
                        if (isKn) Text("ರೈಲ್ವೆ ನಿಲ್ದಾಣ ಮಾಹಿತಿ", fontSize = 14.sp)
                    }
                },
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
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                label = { Text(if (isKn) "ನಿಲ್ದಾಣ ಹುಡುಕಿ / Search Station" else "Search Station") },
                modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
                singleLine = true
            )
            
            Text(
                text = if (isKn) "ನಿಲ್ದಾಣ ಆಯ್ಕೆಮಾಡಿ · SELECT STATION" else "SELECT STATION",
                fontWeight = FontWeight.Bold,
                color = NammaBlue,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            LazyColumn {
                items(filteredStations) { station ->
                    StationItem(station = station, onClick = { onStationClick(station.id) })
                }
            }
        }
    }
}

@Composable
fun StationItem(station: Station, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .background(NammaBlue, shape = MaterialTheme.shapes.small),
                contentAlignment = Alignment.Center
            ) {
                Text(station.id, color = MaterialTheme.colorScheme.onPrimary, fontWeight = FontWeight.Bold)
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(station.nameKannada, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                Text(station.nameEnglish, fontSize = 16.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                Text(station.zone, fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
            Text(">", fontSize = 24.sp, color = NammaBlue, fontWeight = FontWeight.Bold)
        }
    }
}
