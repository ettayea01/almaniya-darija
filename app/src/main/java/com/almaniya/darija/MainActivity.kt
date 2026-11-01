package com.almaniya.darija

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.almaniya.darija.presentation.navigation.AppNavHost
import com.almaniya.darija.presentation.screens.settings.SettingsViewModel
import com.almaniya.darija.presentation.theme.AlmaniyaTheme
import com.almaniya.darija.utils.SpeechHelper

class MainActivity : ComponentActivity() {

    private lateinit var speechHelper: SpeechHelper
    private val settingsViewModel: SettingsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        speechHelper = SpeechHelper(this)
        val app = application as AlmaniyaApp

        setContent {
            val settingsState by settingsViewModel.uiState.collectAsState()

            AlmaniyaTheme(darkTheme = settingsState.isDarkMode) {
                Surface(modifier = Modifier.fillMaxSize()) {
                    val navController = rememberNavController()
                    AppNavHost(
                        navController = navController,
                        repository = app.repository,
                        speechHelper = speechHelper,
                        settingsViewModel = settingsViewModel
                    )
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        speechHelper.shutdown()
    }
}
