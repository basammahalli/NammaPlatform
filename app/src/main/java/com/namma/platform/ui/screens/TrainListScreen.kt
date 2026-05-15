package com.namma.platform.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.namma.platform.data.Station
import com.namma.platform.data.Train
import com.namma.platform.ui.theme.*

import androidx.compose.material.icons.filled.Language
import com.namma.platform.utils.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TrainListScreen(
    station: Station,
    trains: List<Train>,
    onBack: () -> Unit,
    onTrainClick: (String) -> Unit
) {
    val langManager = LocalLanguageManager.current
    val isKn = langManager.currentLanguage == AppLanguage.KANNADA

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(if (isKn) station.nameKannada else station.nameEnglish, fontWeight = FontWeight.Bold, color = NammaYellow)
                        Text(if (isKn) "ಮುಂದಿನ ರೈಲುಗಳು" else "Next Trains", fontSize = 14.sp, color = Color.White)
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back", tint = Color.White)
                    }
                },
                actions = {
                    IconButton(onClick = { langManager.toggleLanguage() }) {
                        Icon(Icons.Filled.Language, contentDescription = "Language", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = NammaBlue
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
            Text(
                text = if (isKn) "ಮುಂದಿನ 3 ರೈಲುಗಳು" else "NEXT 3 TRAINS",
                fontWeight = FontWeight.Bold,
                color = NammaBlue,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            LazyColumn {
                items(trains) { train ->
                    TrainItem(train = train, onClick = { onTrainClick(train.id) })
                }
            }
        }
    }
}

@Composable
fun TrainItem(train: Train, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp)
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Box(
                modifier = Modifier
                    .background(NammaBlue, shape = MaterialTheme.shapes.small)
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                Text("ಮುಂದಿನ ರೈಲು · NEXT TRAIN", color = NammaYellow, fontSize = 12.sp, fontWeight = FontWeight.Bold)
            }
            Spacer(modifier = Modifier.height(8.dp))
            
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Column {
                    Text(train.nameKannada, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                    Text("${train.nameEnglish} #${train.id}", fontSize = 14.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
                Box(
                    modifier = Modifier
                        .size(60.dp)
                        .background(if (train.platform == "1") NammaBlue else NammaRed, shape = MaterialTheme.shapes.medium),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("ಪ್ಲಾಟ್‌ಫಾರ್ಮ್", color = Color.White, fontSize = 10.sp)
                        Text(train.platform, color = Color.White, fontSize = 24.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(train.departureTime, fontSize = 18.sp, fontWeight = FontWeight.Bold, color = NammaBlue)
                Text(" ಗಂಟೆಗೆ ಹೊರಡುತ್ತದೆ", fontSize = 14.sp, modifier = Modifier.padding(start = 4.dp))
                Spacer(modifier = Modifier.weight(1f))
                
                // Mini coach layout preview
                Row {
                    train.coaches.take(6).forEach { coach ->
                        val color = when(coach.type) {
                            "Engine" -> CoachEngine
                            "General" -> CoachGeneral
                            "Ladies" -> CoachLadies
                            "Sleeper" -> CoachSleeper
                            "AC" -> CoachAC
                            else -> CoachGuard
                        }
                        Box(
                            modifier = Modifier
                                .padding(end = 2.dp)
                                .size(20.dp)
                                .background(color, shape = MaterialTheme.shapes.extraSmall),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(coach.type.take(1), color = if (coach.type == "General") Color.Black else Color.White, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                    if (train.coaches.size > 6) {
                         Text("+${train.coaches.size - 6}", fontSize = 12.sp, modifier = Modifier.padding(start = 4.dp))
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            Text("ವಿವರ ನೋಡಿ ->", color = NammaBlue, fontSize = 14.sp, modifier = Modifier.align(Alignment.End))
        }
    }
}
