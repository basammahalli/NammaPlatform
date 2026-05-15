package com.namma.platform.utils

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

enum class AppLanguage {
    KANNADA, ENGLISH
}

class LanguageManager {
    var currentLanguage by mutableStateOf(AppLanguage.KANNADA)
    
    fun toggleLanguage() {
        currentLanguage = if (currentLanguage == AppLanguage.KANNADA) {
            AppLanguage.ENGLISH
        } else {
            AppLanguage.KANNADA
        }
    }
}

val LocalLanguageManager = compositionLocalOf<LanguageManager> {
    error("No LanguageManager provided")
}
