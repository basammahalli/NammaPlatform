package com.namma.platform.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Language
import androidx.compose.material3.*
import androidx.compose.runtime.*
import com.namma.platform.utils.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.namma.platform.R
import com.namma.platform.ui.theme.NammaBlue
import com.namma.platform.ui.theme.NammaYellow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpScreen(
    onSignUpSuccess: () -> Unit,
    onNavigateToLogin: () -> Unit
) {
    var fullName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isAdminSignUp by remember { mutableStateOf(false) }

    val langManager = LocalLanguageManager.current
    val isKn = langManager.currentLanguage == AppLanguage.KANNADA

    var errorMessage by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.TopEnd) {
            IconButton(onClick = { langManager.toggleLanguage() }) {
                Icon(Icons.Filled.Language, contentDescription = "Toggle Language", tint = NammaBlue)
            }
        }

        Text(
            text = if (isKn) "ಹೊಸ ಖಾತೆ ತೆರೆಯಿರಿ" else "Create New Account",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = NammaBlue
        )
        
        if (errorMessage.isNotEmpty()) {
            Text(errorMessage, color = MaterialTheme.colorScheme.error, modifier = Modifier.padding(bottom = 8.dp))
        }

        Text(
            text = if (isKn) "ನಿಮ್ಮ ವಿವರಗಳನ್ನು ನಮೂದಿಸಿ" else "Enter your details below",
            fontSize = 18.sp,
            color = NammaBlue,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        Row(
            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            FilterChip(
                selected = !isAdminSignUp,
                onClick = { isAdminSignUp = false; errorMessage = "" },
                label = { Text(if (isKn) "ಬಳಕೆದಾರ / User" else "User / ಪ್ರಯಾಣಿಕ") },
                modifier = Modifier.padding(end = 8.dp)
            )
            FilterChip(
                selected = isAdminSignUp,
                onClick = { isAdminSignUp = true; errorMessage = "" },
                label = { Text(if (isKn) "ನಿರ್ವಾಹಕ / Admin" else "Admin / ನಿರ್ವಾಹಕ") }
            )
        }

        OutlinedTextField(
            value = fullName,
            onValueChange = { fullName = it; errorMessage = "" },
            label = { Text(if (isKn) "ಪೂರ್ಣ ಹೆಸರು / Full Name" else "Full Name") },
            modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
            isError = errorMessage.isNotEmpty() && fullName.isEmpty()
        )

        OutlinedTextField(
            value = email,
            onValueChange = { email = it; errorMessage = "" },
            label = { Text(if (isKn) "ಇಮೇಲ್ / Email" else "Email") },
            modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
            isError = errorMessage.contains("email") || errorMessage.contains("ಇಮೇಲ್") || (errorMessage.isNotEmpty() && email.isEmpty())
        )

        OutlinedTextField(
            value = phone,
            onValueChange = { phone = it; errorMessage = "" },
            label = { Text(if (isKn) "ಮೊಬೈಲ್ ಸಂಖ್ಯೆ / Phone" else "Phone Number") },
            modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
            isError = errorMessage.contains("phone") || errorMessage.contains("ಮೊಬೈಲ್") || (errorMessage.isNotEmpty() && phone.isEmpty())
        )

        OutlinedTextField(
            value = username,
            onValueChange = { username = it; errorMessage = "" },
            label = { Text(if (isKn) "ಬಳಕೆದಾರ ಹೆಸರು / Username" else "Username") },
            modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
            isError = errorMessage.isNotEmpty() && username.isEmpty()
        )

        OutlinedTextField(
            value = password,
            onValueChange = { password = it; errorMessage = "" },
            label = { Text(if (isKn) "ಪಾಸ್‌ವರ್ಡ್ / Password" else "Password") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth().padding(bottom = 24.dp),
            isError = errorMessage.isNotEmpty() && password.isEmpty()
        )

        Button(
            onClick = {
                val isPhoneValid = phone.length == 10 && phone.all { it.isDigit() }
                val isEmailValid = email.endsWith("@gmail.com") && email.length > 10

                if (fullName.isBlank() || email.isBlank() || phone.isBlank() || username.isBlank() || password.isBlank()) {
                    errorMessage = if (isKn) "ದಯವಿಟ್ಟು ಎಲ್ಲಾ ವಿವರಗಳನ್ನು ಭರ್ತಿ ಮಾಡಿ" else "Please fill in all details"
                } else if (!isEmailValid) {
                    errorMessage = if (isKn) "ಅಮಾನ್ಯ ಇಮೇಲ್ (@gmail.com ಅಗತ್ಯವಿದೆ)" else "Invalid email (@gmail.com required)"
                } else if (!isPhoneValid) {
                    errorMessage = if (isKn) "ಅಮಾನ್ಯ ಮೊಬೈಲ್ ಸಂಖ್ಯೆ (10 ಅಂಕಿಗಳು ಅಗತ್ಯವಿದೆ)" else "Invalid phone number (10 digits required)"
                } else {
                    val newUser = UserAccount(fullName, email, phone, username, password, isAdminSignUp)
                    if (SessionManager.signUp(newUser)) {
                        onSignUpSuccess()
                    } else {
                        errorMessage = if (isKn) "ಈ ಬಳಕೆದಾರ ಹೆಸರು ಈಗಾಗಲೇ ಬಳಕೆಯಲ್ಲಿದೆ" else "Username already exists"
                    }
                }
            },
            colors = ButtonDefaults.buttonColors(containerColor = NammaYellow, contentColor = NammaBlue),
            modifier = Modifier.fillMaxWidth().height(50.dp)
        ) {
            Text(if (isKn) "ಸೈನ್ ಅಪ್" else "Sign Up", fontSize = 18.sp, fontWeight = FontWeight.Bold)
        }

        TextButton(onClick = onNavigateToLogin) {
            Text("Already have an account? Login / ಲಾಗಿನ್ ಮಾಡಿ")
        }
    }
}
