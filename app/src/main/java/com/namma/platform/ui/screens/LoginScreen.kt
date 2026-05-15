package com.namma.platform.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.res.stringResource
import com.namma.platform.R
import com.namma.platform.ui.theme.NammaBlue
import com.namma.platform.ui.theme.NammaYellow

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Language
import com.namma.platform.utils.*

@Composable
fun LoginScreen(
    onLoginSuccess: (isAdmin: Boolean) -> Unit,
    onNavigateToSignUp: () -> Unit
) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isAdminLogin by remember { mutableStateOf(false) }
    
    val langManager = LocalLanguageManager.current
    val isKn = langManager.currentLanguage == AppLanguage.KANNADA
    var errorMessage by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.TopEnd) {
            IconButton(onClick = { langManager.toggleLanguage() }) {
                Icon(Icons.Filled.Language, contentDescription = "Toggle Language", tint = NammaBlue)
            }
        }

        Text(
            text = if (isKn) "ನಮ್ಮ ಪ್ಲಾಟ್‌ಫಾರ್ಮ್" else "Namma Platform",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = NammaBlue,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        
        if (errorMessage.isNotEmpty()) {
            Text(errorMessage, color = MaterialTheme.colorScheme.error, modifier = Modifier.padding(bottom = 8.dp))
        }

        Text(
            text = if (isKn) "ಪ್ರಯಾಣ ಸುಲಭಗೊಳಿಸಿ" else "Making Travel Easy",
            fontSize = 18.sp,
            color = NammaBlue,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        Row(
            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Button(
                onClick = { isAdminLogin = false; errorMessage = "" },
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (!isAdminLogin) NammaBlue else MaterialTheme.colorScheme.surfaceVariant,
                    contentColor = if (!isAdminLogin) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface
                ),
                modifier = Modifier.padding(end = 8.dp)
            ) {
                Text(if (isKn) "ಬಳಕೆದಾರ" else "User")
            }
            Button(
                onClick = { isAdminLogin = true; errorMessage = "" },
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (isAdminLogin) NammaBlue else MaterialTheme.colorScheme.surfaceVariant,
                    contentColor = if (isAdminLogin) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface
                )
            ) {
                Text(if (isKn) "ನಿರ್ವಾಹಕ" else "Admin")
            }
        }

        OutlinedTextField(
            value = username,
            onValueChange = { username = it; errorMessage = "" },
            label = { Text(if (isKn) "ಬಳಕೆದಾರ ಹೆಸರು / Username" else "Username") },
            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
            isError = errorMessage.isNotEmpty()
        )

        OutlinedTextField(
            value = password,
            onValueChange = { password = it; errorMessage = "" },
            label = { Text(if (isKn) "ಪಾಸ್‌ವರ್ಡ್ / Password" else "Password") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth().padding(bottom = 24.dp),
            isError = errorMessage.isNotEmpty()
        )

        Button(
            onClick = {
                if (username.isEmpty() || password.isEmpty()) {
                    errorMessage = if (isKn) "ದಯವಿಟ್ಟು ಎಲ್ಲಾ ವಿವರಗಳನ್ನು ಭರ್ತಿ ಮಾಡಿ" else "Please fill in all details"
                    return@Button
                }
                val user = SessionManager.login(username, password)
                if (user != null) {
                    if (user.isAdmin == isAdminLogin) {
                        onLoginSuccess(user.isAdmin)
                    } else {
                        errorMessage = if (isKn) "ತಪ್ಪಾದ ಪಾತ್ರ ಆಯ್ಕೆ ಮಾಡಲಾಗಿದೆ" else "Incorrect role selected"
                    }
                } else {
                    errorMessage = if (isKn) "ಬಳಕೆದಾರ ಹೆಸರು ಅಥವಾ ಪಾಸ್‌ವರ್ಡ್ ತಪ್ಪು" else "Invalid username or password"
                }
            },
            colors = ButtonDefaults.buttonColors(containerColor = NammaYellow, contentColor = NammaBlue),
            modifier = Modifier.fillMaxWidth().height(50.dp)
        ) {
            Text(if (isKn) "ಲಾಗಿನ್" else "Login", fontSize = 18.sp, fontWeight = FontWeight.Bold)
        }

        Spacer(modifier = Modifier.height(16.dp))

        TextButton(onClick = onNavigateToSignUp) {
            Text(if (isKn) "ಹೊಸ ಖಾತೆ ತೆರೆಯಿರಿ / Create Account" else "Don't have an account? Sign Up")
        }
    }
}
