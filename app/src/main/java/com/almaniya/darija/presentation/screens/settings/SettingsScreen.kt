package com.almaniya.darija.presentation.screens.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class SettingsUiState(
    val isDarkMode: Boolean = false,
    val speechRate: Float = 0.9f,
    val showAboutDialog: Boolean = false
)

class SettingsViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(SettingsUiState())
    val uiState: StateFlow<SettingsUiState> = _uiState.asStateFlow()

    fun toggleDarkMode(enabled: Boolean) {
        _uiState.value = _uiState.value.copy(isDarkMode = enabled)
    }

    fun updateSpeechRate(rate: Float) {
        _uiState.value = _uiState.value.copy(speechRate = rate)
    }

    fun toggleAboutDialog(show: Boolean) {
        _uiState.value = _uiState.value.copy(showAboutDialog = show)
    }
}

@Composable
fun SettingsScreen(
    uiState: SettingsUiState,
    onToggleDarkMode: (Boolean) -> Unit,
    onSpeechRateChange: (Float) -> Unit,
    onToggleAboutDialog: (Boolean) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Text(
                text = "الإعدادات والتفضيلات",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )
            Text(
                text = "خصص تجربة التعلم ديالك بما يناسبك.",
                fontSize = 13.sp,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
            )
        }

        // Appearance & Voice Card
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    // Dark Mode Toggle
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.DarkMode,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Column {
                                Text("الوضع المظلم (Dark Mode)", fontWeight = FontWeight.Bold, fontSize = 15.sp)
                                Text("مريح للعينين فالمراجعة بالليل", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f))
                            }
                        }
                        Switch(
                            checked = uiState.isDarkMode,
                            onCheckedChange = onToggleDarkMode
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))
                    HorizontalDivider()
                    Spacer(modifier = Modifier.height(16.dp))

                    // Speech Rate Slider
                    Column(modifier = Modifier.fillMaxWidth()) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Speed,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Text("سرعة نطق الصوت الألماني", fontWeight = FontWeight.Bold, fontSize = 15.sp)
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("بطيء (للمبتدئين)", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f))
                            Text("عادي", fontSize = 12.sp, color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)
                            Text("سريع", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f))
                        }
                        Slider(
                            value = uiState.speechRate,
                            onValueChange = onSpeechRateChange,
                            valueRange = 0.6f..1.2f,
                            steps = 5
                        )
                    }
                }
            }
        }

        // About Application Card
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Info,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Column {
                                Text("حول تطبيق ألماني بالدارجة", fontWeight = FontWeight.Bold, fontSize = 15.sp)
                                Text("إصدار 1.0.0 (مجاني وبلا إنترنت 100%)", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f))
                            }
                        }
                        TextButton(onClick = { onToggleAboutDialog(true) }) {
                            Text("المزيد")
                        }
                    }
                }
            }
        }
    }

    if (uiState.showAboutDialog) {
        AlertDialog(
            onDismissRequest = { onToggleAboutDialog(false) },
            confirmButton = {
                TextButton(onClick = { onToggleAboutDialog(false) }) {
                    Text("واضح ومفهوم")
                }
            },
            title = { Text("ألماني بالدارجة 🇲🇦 🇩🇪", fontWeight = FontWeight.Bold) },
            text = {
                Text(
                    text = "تطبيق صُمم لمساعدة المغاربة على تعلم اللغة الألمانية بكل سهولة بالاعتماد على الدارجة المغربية.\n\n" +
                            "✅ مجاني بالكامل بدون اشتراكات أو قفل للدروس\n" +
                            "✅ بدون إعلانات مزعجة\n" +
                            "✅ يشتغل بدون إنترنت (Offline First)\n" +
                            "✅ معتمد على محرك النطق الأصيل لـ Android",
                    fontSize = 14.sp,
                    lineHeight = 22.sp
                )
            }
        )
    }
}
