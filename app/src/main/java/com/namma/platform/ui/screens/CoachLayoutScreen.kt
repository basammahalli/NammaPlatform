package com.namma.platform.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
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
import com.namma.platform.data.Coach
import com.namma.platform.data.Station
import com.namma.platform.data.Train
import com.namma.platform.ui.theme.*

import androidx.compose.material.icons.filled.Language
import com.namma.platform.utils.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CoachLayoutScreen(
    station: Station,
    train: Train,
    onBack: () -> Unit,
    onSpeakAnnouncement: (String) -> Unit
) {
    val langManager = LocalLanguageManager.current
    val isKn = langManager.currentLanguage == AppLanguage.KANNADA

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(if (isKn) train.nameKannada else train.nameEnglish, fontWeight = FontWeight.Bold, color = NammaYellow)
                        Text(if (isKn) "ಬಂಡಿ ಸರಣಿ" else "Coach Layout", fontSize = 14.sp, color = Color.White)
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
            // Platform Info Card
            Card(
                modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
                colors = CardDefaults.cardColors(containerColor = NammaBlue)
            ) {
                Row(
                    modifier = Modifier.padding(24.dp).fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(if (isKn) "ಪ್ಲಾಟ್‌ಫಾರ್ಮ್ ಸಂಖ್ಯೆ" else "Platform Number", color = Color.White)
                        Text(train.platform, color = NammaYellow, fontSize = 64.sp, fontWeight = FontWeight.Bold)
                    }
                    Column(horizontalAlignment = Alignment.End) {
                        Text(train.departureTime, color = Color.White, fontSize = 32.sp, fontWeight = FontWeight.Bold)
                        Text(if (isKn) "ಹೊರಡುವ ಸಮಯ" else "Departure Time", color = Color.White)
                    }
                }
            }

            Text(if (isKn) "ಬಂಡಿ ಸರಣಿ (ಇಂಜಿನ್ -> ಗಾರ್ಡ್)" else "COACH LAYOUT (Engine -> Guard)", fontWeight = FontWeight.Bold, color = NammaBlue, modifier = Modifier.padding(bottom = 8.dp))
            Text(if (isKn) "<- ಈ ಕಡೆ ಸ್ವೈಪ್ ಮಾಡಿ ->" else "<- Swipe to see all coaches ->", fontSize = 12.sp, modifier = Modifier.padding(bottom = 16.dp))

            // Coach Layout Strip
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState())
                    .padding(bottom = 24.dp)
            ) {
                train.coaches.forEach { coach ->
                    CoachBox(coach)
                    Spacer(modifier = Modifier.width(8.dp))
                }
            }

            // Coach Legend and Info
            Card(
                modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
                colors = CardDefaults.cardColors(containerColor = NammaLightGray),
                border = androidx.compose.foundation.BorderStroke(1.dp, NammaYellow)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(modifier = Modifier.size(16.dp).background(CoachGeneral, shape = MaterialTheme.shapes.small))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("ಸಾಧಾರಣ ಬಂಡಿ · General Coach", fontWeight = FontWeight.Bold)
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("ಹಳದಿ ಬಣ್ಣದ ಬಂಡಿ ಸಾಧಾರಣ ಬಂಡಿ. ಎಲ್ಲಾ ಪ್ರಯಾಣಿಕರಿಗೂ ಮೀಸಲು.")
                    Text("Yellow coaches are General class - open for all passengers.", fontSize = 12.sp, style = androidx.compose.ui.text.TextStyle(fontStyle = androidx.compose.ui.text.font.FontStyle.Italic))
                }
            }

            // Full Speak Button
            Button(
                onClick = {
                    val coachSequence = getCoachSequenceText(train.coaches)
                    val announcement = if (isKn) {
                        "ದಯವಿಟ್ಟು ಗಮನಿಸಿ, ${train.nameKannada} ಪ್ಲಾಟ್‌ಫಾರ್ಮ್ ${train.platform} ರಲ್ಲಿ ಬರಲಿದೆ. ಬಂಡಿ ಸರಣಿ ಈ ರೀತಿ ಇದೆ: $coachSequence."
                    } else {
                        "Attention please, ${train.nameEnglish} is arriving on platform ${train.platform}. The coach sequence is: $coachSequence."
                    }
                    onSpeakAnnouncement(announcement)
                },
                colors = ButtonDefaults.buttonColors(containerColor = NammaYellow, contentColor = NammaBlue),
                modifier = Modifier.fillMaxWidth().height(60.dp)
            ) {
                Text(if (isKn) "ಹೇಳಿಕೆ ಕೇಳಿ (ಕನ್ನಡ)" else "Hear Announcement (English)", fontWeight = FontWeight.Bold, textAlign = androidx.compose.ui.text.style.TextAlign.Center)
            }
        }
    }
}

fun getCoachSequenceText(coaches: List<Coach>): String {
    return coaches.joinToString(", ") { coach ->
        when(coach.type) {
            "Engine" -> "ಇಂಜಿನ್"
            "General" -> "ಸಾಧಾರಣ"
            "Ladies" -> "ಮಹಿಳೆಯರು"
            "Sleeper" -> "ಸ್ಲೀಪರ್"
            "AC" -> "ಎ.ಸಿ"
            else -> "ಗಾರ್ಡ್"
        }
    }
}

@Composable
fun CoachBox(coach: Coach) {
    val color = when(coach.type) {
        "Engine" -> CoachEngine
        "General" -> CoachGeneral
        "Ladies" -> CoachLadies
        "Sleeper" -> CoachSleeper
        "AC" -> CoachAC
        else -> CoachGuard
    }
    val kannadaName = when(coach.type) {
        "Engine" -> "ಇಂಜಿನ್"
        "General" -> "ಸಾಧಾರಣ"
        "Ladies" -> "ಮಹಿಳೆಯರು"
        "Sleeper" -> "ಸ್ಲೀಪರ್"
        "AC" -> "ಎ.ಸಿ"
        else -> "ಗಾರ್ಡ್"
    }

    Box(
        modifier = Modifier
            .size(width = 80.dp, height = 100.dp)
            .background(color, shape = MaterialTheme.shapes.medium),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(coach.type, color = if (coach.type == "General") Color.Black else Color.White, fontWeight = FontWeight.Bold, fontSize = 14.sp)
            Text(kannadaName, color = if (coach.type == "General") Color.Black else Color.White, fontSize = 12.sp)
        }
    }
}
