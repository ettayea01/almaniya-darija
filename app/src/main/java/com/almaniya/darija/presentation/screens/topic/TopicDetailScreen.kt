package com.almaniya.darija.presentation.screens.topic

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.almaniya.darija.presentation.components.AlmaniyaTopBar
import com.almaniya.darija.presentation.components.WordCard

@Composable
fun TopicDetailScreen(
    uiState: TopicDetailUiState,
    onNavigateBack: () -> Unit,
    onTabSelect: (Int) -> Unit,
    onSpeak: (String) -> Unit,
    onToggleFavorite: (com.almaniya.darija.domain.model.Word) -> Unit,
    onStartFlashcards: () -> Unit,
    onStartQuiz: () -> Unit
) {
    Scaffold(
        topBar = {
            AlmaniyaTopBar(
                title = uiState.topic?.titleDarija ?: "تفاصيل الدرس",
                canNavigateBack = true,
                onNavigateBack = onNavigateBack
            )
        },
        bottomBar = {
            // Action bar with Flashcards and Quiz buttons
            Surface(
                modifier = Modifier.fillMaxWidth(),
                tonalElevation = 8.dp,
                color = MaterialTheme.colorScheme.surface
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    OutlinedButton(
                        onClick = onStartFlashcards,
                        modifier = Modifier
                            .weight(1f)
                            .height(50.dp),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Icon(imageVector = Icons.Default.Style, contentDescription = null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("البطاقات", fontWeight = FontWeight.Bold)
                    }

                    Button(
                        onClick = onStartQuiz,
                        modifier = Modifier
                            .weight(1f)
                            .height(50.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                    ) {
                        Icon(imageVector = Icons.Default.Quiz, contentDescription = null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("كويز وسؤال", fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(MaterialTheme.colorScheme.background)
        ) {
            // Tab row: Vocabulary vs Grammar
            TabRow(
                selectedTabIndex = uiState.selectedTab,
                containerColor = MaterialTheme.colorScheme.surface
            ) {
                Tab(
                    selected = uiState.selectedTab == 0,
                    onClick = { onTabSelect(0) },
                    text = { Text("الكلمات (${uiState.words.size})", fontWeight = FontWeight.Bold) },
                    icon = { Icon(Icons.Default.FormatListBulleted, contentDescription = null) }
                )
                Tab(
                    selected = uiState.selectedTab == 1,
                    onClick = { onTabSelect(1) },
                    text = { Text("قواعد الدرس", fontWeight = FontWeight.Bold) },
                    icon = { Icon(Icons.Default.MenuBook, contentDescription = null) }
                )
            }

            if (uiState.selectedTab == 0) {
                // Vocabulary list
                if (uiState.words.isEmpty() && !uiState.isLoading) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "ما كاين حتى كلمة فهاد الدرس حالياً.",
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                        )
                    }
                } else {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(vertical = 12.dp)
                    ) {
                        items(uiState.words) { word ->
                            WordCard(
                                word = word,
                                onSpeak = onSpeak,
                                onToggleFavorite = onToggleFavorite
                            )
                        }
                    }
                }
            } else {
                // Grammar notes in Moroccan Darija
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {
                    item {
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                        ) {
                            Column(modifier = Modifier.padding(20.dp)) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        imageVector = Icons.Default.Info,
                                        contentDescription = null,
                                        tint = MaterialTheme.colorScheme.primary
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = "شرح مبسط لقاعدة ${uiState.topic?.titleGerman}",
                                        fontSize = 17.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.onSurface
                                    )
                                }
                                Spacer(modifier = Modifier.height(16.dp))
                                Text(
                                    text = uiState.topic?.grammarNoteDarija?.ifBlank {
                                        "فهاد الدرس، ركز على نطق الكلمات بالألمانية وحفظ الأداة (der, die, das) مع كل اسم باش يسهل عليك تكوين الجمل من بعد."
                                    } ?: "",
                                    fontSize = 15.sp,
                                    lineHeight = 24.sp,
                                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.85f)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
