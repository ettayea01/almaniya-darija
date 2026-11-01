package com.almaniya.darija.presentation.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Alphabet : Screen("alphabet")
    object Favorites : Screen("favorites")
    object Progress : Screen("progress")
    object Settings : Screen("settings")

    object TopicDetail : Screen("topic_detail/{topicId}") {
        fun createRoute(topicId: String) = "topic_detail/$topicId"
    }

    object Flashcards : Screen("flashcards/{topicId}") {
        fun createRoute(topicId: String) = "flashcards/$topicId"
    }

    object Quiz : Screen("quiz/{topicId}") {
        fun createRoute(topicId: String) = "quiz/$topicId"
    }

    object Search : Screen("search")
}
