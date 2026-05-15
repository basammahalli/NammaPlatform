package com.namma.platform

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.namma.platform.data.DataRepository
import com.namma.platform.ui.screens.*
import com.namma.platform.ui.theme.NammaPlatformTheme
import com.namma.platform.utils.*

class MainActivity : ComponentActivity() {
    private var ttsHelper: TTSHelper? = null
    private val languageManager = LanguageManager()

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        
        // Initialize Data and TTS
        DataRepository.loadData(this)
        ttsHelper = TTSHelper(this)

        setContent {
            CompositionLocalProvider(LocalLanguageManager provides languageManager) {
                NammaPlatformTheme {
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        val helper = ttsHelper
                        if (helper != null) {
                            AppNavigation(helper)
                        }
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        ttsHelper?.shutdown()
        super.onDestroy()
    }
}

@Composable
fun AppNavigation(ttsHelper: TTSHelper) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "login") {
        composable("login") {
            LoginScreen(
                onLoginSuccess = { isAdmin ->
                    if (isAdmin) {
                        navController.navigate("admin_dashboard") {
                            popUpTo("login") { inclusive = true }
                        }
                    } else {
                        navController.navigate("stations") {
                            popUpTo("login") { inclusive = true }
                        }
                    }
                },
                onNavigateToSignUp = { navController.navigate("signup") }
            )
        }

        composable("signup") {
            SignUpScreen(
                onSignUpSuccess = {
                    navController.navigate("login") {
                        popUpTo("signup") { inclusive = true }
                    }
                },
                onNavigateToLogin = { navController.popBackStack() }
            )
        }

        composable("admin_dashboard") {
            AdminDashboardScreen(
                onLogout = {
                    navController.navigate("login") {
                        popUpTo("admin_dashboard") { inclusive = true }
                    }
                },
                onNavigateToSettings = { navController.navigate("settings") },
                onNavigateToProfile = { navController.navigate("admin_profile") },
                onNavigateToAddTrain = { navController.navigate("add_train") }
            )
        }

        composable("add_train") {
            AddTrainScreen(
                onBack = { navController.popBackStack() }
            )
        }

        composable("stations") {
            val stations = DataRepository.getStations()
            StationListScreen(
                stations = stations,
                onStationClick = { stationId ->
                    navController.navigate("trains/$stationId")
                },
                onNavigateToSettings = { navController.navigate("settings") },
                onNavigateToProfile = { navController.navigate("user_profile") }
            )
        }

        composable(
            "trains/{stationId}",
            arguments = listOf(navArgument("stationId") { type = NavType.StringType })
        ) { backStackEntry ->
            val stationId = backStackEntry.arguments?.getString("stationId") ?: ""
            val station = DataRepository.getStations().find { it.id == stationId }
            val trains = DataRepository.getTrainsForStation(stationId)

            if (station != null) {
                TrainListScreen(
                    station = station,
                    trains = trains,
                    onBack = { navController.popBackStack() },
                    onTrainClick = { trainId ->
                        navController.navigate("coach_layout/$stationId/$trainId")
                    }
                )
            }
        }

        composable(
            "coach_layout/{stationId}/{trainId}",
            arguments = listOf(
                navArgument("stationId") { type = NavType.StringType },
                navArgument("trainId") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val stationId = backStackEntry.arguments?.getString("stationId") ?: ""
            val trainId = backStackEntry.arguments?.getString("trainId") ?: ""
            
            val station = DataRepository.getStations().find { it.id == stationId }
            val train = DataRepository.getTrainsForStation(stationId).find { it.id == trainId }

            if (station != null && train != null) {
                CoachLayoutScreen(
                    station = station,
                    train = train,
                    onBack = { navController.popBackStack() },
                    onSpeakAnnouncement = { announcement ->
                        ttsHelper.speak(announcement)
                    }
                )
            }
        }

        composable("user_profile") {
            UserProfileScreen(
                isAdmin = false,
                onBack = { navController.popBackStack() },
                onLogout = {
                    navController.navigate("login") {
                        popUpTo("login") { inclusive = true }
                    }
                }
            )
        }

        composable("admin_profile") {
            UserProfileScreen(
                isAdmin = true,
                onBack = { navController.popBackStack() },
                onLogout = {
                    navController.navigate("login") {
                        popUpTo("login") { inclusive = true }
                    }
                }
            )
        }

        composable("settings") {
            SettingsScreen(onBack = { navController.popBackStack() })
        }
    }
}
