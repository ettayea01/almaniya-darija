package com.almaniya.darija.presentation.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import com.almaniya.darija.domain.repository.AlmaniyaRepository
import com.almaniya.darija.presentation.components.AlmaniyaBottomBar
import com.almaniya.darija.presentation.components.AlmaniyaTopBar
import com.almaniya.darija.presentation.screens.alphabet.AlphabetScreen
import com.almaniya.darija.presentation.screens.alphabet.AlphabetViewModel
import com.almaniya.darija.presentation.screens.favorites.FavoritesScreen
import com.almaniya.darija.presentation.screens.favorites.FavoritesViewModel
import com.almaniya.darija.presentation.screens.flashcards.FlashcardsScreen
import com.almaniya.darija.presentation.screens.flashcards.FlashcardsViewModel
import com.almaniya.darija.presentation.screens.home.HomeScreen
import com.almaniya.darija.presentation.screens.home.HomeViewModel
import com.almaniya.darija.presentation.screens.progress.ProgressScreen
import com.almaniya.darija.presentation.screens.progress.ProgressViewModel
import com.almaniya.darija.presentation.screens.quiz.QuizScreen
import com.almaniya.darija.presentation.screens.quiz.QuizViewModel
import com.almaniya.darija.presentation.screens.search.SearchScreen
import com.almaniya.darija.presentation.screens.search.SearchViewModel
import com.almaniya.darija.presentation.screens.settings.SettingsScreen
import com.almaniya.darija.presentation.screens.settings.SettingsViewModel
import com.almaniya.darija.utils.SpeechHelper

@Composable
fun AppNavHost(
    navController: NavHostController,
    repository: AlmaniyaRepository,
    speechHelper: SpeechHelper,
    settingsViewModel: SettingsViewModel,
    modifier: Modifier = Modifier
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route ?: Screen.Home.route

    val bottomNavRoutes = listOf(
        Screen.Home.route,
        Screen.Alphabet.route,
        Screen.Favorites.route,
        Screen.Progress.route,
        Screen.Settings.route
    )
    val shouldShowBottomBar = currentRoute in bottomNavRoutes

    Scaffold(
        topBar = {
            if (shouldShowBottomBar) {
                AlmaniyaTopBar(
                    title = "ألماني بالدارجة 🇲🇦",
                    onSearchClick = { navController.navigate(Screen.Search.route) }
                )
            }
        },
        bottomBar = {
            if (shouldShowBottomBar) {
                AlmaniyaBottomBar(
                    currentRoute = currentRoute,
                    onNavigateToRoute = { route ->
                        navController.navigate(route) {
                            popUpTo(Screen.Home.route) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = modifier.padding(paddingValues)
        ) {
            // Home Tab
            composable(Screen.Home.route) {
                val viewModel = remember { HomeViewModel(repository) }
                val uiState by viewModel.uiState.collectAsState()
                val settingsState by settingsViewModel.uiState.collectAsState()

                HomeScreen(
                    uiState = uiState,
                    onCategorySelect = { viewModel.selectCategory(it) },
                    onTopicClick = { topicId ->
                        navController.navigate(Screen.TopicDetail.createRoute(topicId))
                    },
                    onSpeak = { text -> speechHelper.speakGerman(text, settingsState.speechRate) }
                )
            }

            // Alphabet Tab
            composable(Screen.Alphabet.route) {
                val viewModel = remember { AlphabetViewModel(repository) }
                val settingsState by settingsViewModel.uiState.collectAsState()

                AlphabetScreen(
                    letters = viewModel.getLetters(),
                    onSpeak = { text -> speechHelper.speakGerman(text, settingsState.speechRate) }
                )
            }

            // Favorites Tab
            composable(Screen.Favorites.route) {
                val viewModel = remember { FavoritesViewModel(repository) }
                val favoriteWords by viewModel.favoriteWords.collectAsState()
                val settingsState by settingsViewModel.uiState.collectAsState()

                FavoritesScreen(
                    favoriteWords = favoriteWords,
                    onSpeak = { text -> speechHelper.speakGerman(text, settingsState.speechRate) },
                    onToggleFavorite = { viewModel.toggleFavorite(it) }
                )
            }

            // Progress Tab
            composable(Screen.Progress.route) {
                val viewModel = remember { ProgressViewModel(repository) }
                val stats by viewModel.stats.collectAsState()

                ProgressScreen(stats = stats)
            }

            // Settings Tab
            composable(Screen.Settings.route) {
                val settingsState by settingsViewModel.uiState.collectAsState()

                SettingsScreen(
                    uiState = settingsState,
                    onToggleDarkMode = { settingsViewModel.toggleDarkMode(it) },
                    onSpeechRateChange = { settingsViewModel.updateSpeechRate(it) },
                    onToggleAboutDialog = { settingsViewModel.toggleAboutDialog(it) }
                )
            }

            // Topic Details Screen
            composable(
                route = Screen.TopicDetail.route,
                arguments = listOf(navArgument("topicId") { type = NavType.StringType })
            ) { backStack ->
                val topicId = backStack.arguments?.getString("topicId") ?: return@composable
                val viewModel = remember(topicId) { TopicViewModel(topicId, repository) }
                val uiState by viewModel.uiState.collectAsState()
                val settingsState by settingsViewModel.uiState.collectAsState()

                TopicDetailScreen(
                    uiState = uiState,
                    onNavigateBack = { navController.popBackStack() },
                    onTabSelect = { viewModel.selectTab(it) },
                    onSpeak = { text -> speechHelper.speakGerman(text, settingsState.speechRate) },
                    onToggleFavorite = { viewModel.toggleFavorite(it) },
                    onStartFlashcards = { navController.navigate(Screen.Flashcards.createRoute(topicId)) },
                    onStartQuiz = { navController.navigate(Screen.Quiz.createRoute(topicId)) }
                )
            }

            // Flashcards Screen
            composable(
                route = Screen.Flashcards.route,
                arguments = listOf(navArgument("topicId") { type = NavType.StringType })
            ) { backStack ->
                val topicId = backStack.arguments?.getString("topicId") ?: return@composable
                val viewModel = remember(topicId) { FlashcardsViewModel(topicId, repository) }
                val uiState by viewModel.uiState.collectAsState()
                val settingsState by settingsViewModel.uiState.collectAsState()

                FlashcardsScreen(
                    uiState = uiState,
                    onNavigateBack = { navController.popBackStack() },
                    onFlip = { viewModel.flip() },
                    onNext = { viewModel.next() },
                    onPrevious = { viewModel.previous() },
                    onMarkLearned = { viewModel.markLearned() },
                    onSpeak = { text -> speechHelper.speakGerman(text, settingsState.speechRate) }
                )
            }

            // Quiz Screen
            composable(
                route = Screen.Quiz.route,
                arguments = listOf(navArgument("topicId") { type = NavType.StringType })
            ) { backStack ->
                val topicId = backStack.arguments?.getString("topicId") ?: return@composable
                val viewModel = remember(topicId) { QuizViewModel(topicId, repository) }
                val uiState by viewModel.uiState.collectAsState()
                val settingsState by settingsViewModel.uiState.collectAsState()

                QuizScreen(
                    uiState = uiState,
                    onNavigateBack = { navController.popBackStack() },
                    onSelectOption = { viewModel.selectOption(it) },
                    onCheckAnswer = { viewModel.checkAnswer() },
                    onNextQuestion = { viewModel.nextQuestion() },
                    onRestartQuiz = { viewModel.restartQuiz() },
                    onSpeak = { text -> speechHelper.speakGerman(text, settingsState.speechRate) }
                )
            }

            // Search Screen
            composable(Screen.Search.route) {
                val viewModel = remember { SearchViewModel(repository) }
                val query by viewModel.searchQuery.collectAsState()
                val results by viewModel.searchResults.collectAsState()
                val settingsState by settingsViewModel.uiState.collectAsState()

                SearchScreen(
                    query = query,
                    results = results,
                    onQueryChange = { viewModel.updateQuery(it) },
                    onNavigateBack = { navController.popBackStack() },
                    onSpeak = { text -> speechHelper.speakGerman(text, settingsState.speechRate) },
                    onToggleFavorite = { viewModel.toggleFavorite(it) }
                )
            }
        }
    }
}
