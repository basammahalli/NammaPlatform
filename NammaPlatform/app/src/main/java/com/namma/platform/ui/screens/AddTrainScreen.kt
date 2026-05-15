package com.namma.platform.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.namma.platform.data.Coach
import com.namma.platform.data.DataRepository
import com.namma.platform.data.Train
import com.namma.platform.ui.theme.NammaBlue
import com.namma.platform.ui.theme.NammaYellow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTrainScreen(onBack: () -> Unit) {
    var trainId by remember { mutableStateOf("") }
    var trainNameKannada by remember { mutableStateOf("") }
    var trainNameEnglish by remember { mutableStateOf("") }
    var stationId by remember { mutableStateOf("") }
    var platform by remember { mutableStateOf("") }
    var departureTime by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Add Train Details") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = NammaBlue,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Text("Enter Train Information", style = MaterialTheme.typography.titleLarge, modifier = Modifier.padding(bottom = 16.dp))

            OutlinedTextField(
                value = trainId,
                onValueChange = { trainId = it },
                label = { Text("Train Number (e.g. 16521)") },
                modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
            )

            OutlinedTextField(
                value = trainNameKannada,
                onValueChange = { trainNameKannada = it },
                label = { Text("Train Name Kannada") },
                modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
            )

            OutlinedTextField(
                value = trainNameEnglish,
                onValueChange = { trainNameEnglish = it },
                label = { Text("Train Name English") },
                modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
            )

            OutlinedTextField(
                value = stationId,
                onValueChange = { stationId = it },
                label = { Text("Station ID (e.g. TK, HAS, MYS)") },
                modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
            )

            OutlinedTextField(
                value = platform,
                onValueChange = { platform = it },
                label = { Text("Platform Number") },
                modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
            )
            
            OutlinedTextField(
                value = departureTime,
                onValueChange = { departureTime = it },
                label = { Text("Departure Time (e.g. 11:23)") },
                modifier = Modifier.fillMaxWidth().padding(bottom = 24.dp)
            )

            Button(
                onClick = {
                    if (trainId.isNotBlank() && stationId.isNotBlank()) {
                        val newTrain = Train(
                            id = trainId,
                            stationId = stationId,
                            nameKannada = trainNameKannada,
                            nameEnglish = trainNameEnglish,
                            departureTime = departureTime,
                            platform = platform,
                            coaches = listOf(Coach("Engine"), Coach("General"), Coach("General"), Coach("Guard"))
                        )
                        DataRepository.addTrain(newTrain)
                        onBack()
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = NammaYellow, contentColor = NammaBlue),
                modifier = Modifier.fillMaxWidth().height(50.dp)
            ) {
                Text("Save Train", fontWeight = FontWeight.Bold)
            }
        }
    }
}
