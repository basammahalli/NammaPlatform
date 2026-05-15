package com.namma.platform.utils

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

data class UserAccount(
    val fullName: String,
    val email: String,
    val phone: String,
    val username: String,
    val password: String,
    val isAdmin: Boolean
)

object SessionManager {
    // Registered users (in-memory mock database)
    private val _registeredUsers = mutableStateListOf<UserAccount>(
        UserAccount("Admin Master", "admin@nammaplatform.com", "+91 9999999999", "admin", "admin", true),
        UserAccount("Basam User", "user@nammaplatform.com", "+91 9876543210", "user", "user", false)
    )

    var currentUser by mutableStateOf<UserAccount?>(null)

    fun signUp(user: UserAccount): Boolean {
        if (_registeredUsers.any { it.username == user.username }) return false
        _registeredUsers.add(user)
        return true
    }

    fun login(username: String, password: String): UserAccount? {
        val user = _registeredUsers.find { it.username == username && it.password == password }
        if (user != null) {
            currentUser = user
        }
        return user
    }

    fun logout() {
        currentUser = null
    }
}
