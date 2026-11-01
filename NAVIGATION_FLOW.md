# تدفق التنقل في التطبيق (NAVIGATION_FLOW.md)

## 1. مسارات التنقل (Navigation Routes)

```kotlin
sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object Onboarding : Screen("onboarding")
    object Main : Screen("main")
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
```

## 2. سلوك أزرار التنقل والرجوع (Back Stack & Deep Linking)
- **زر الرجوع (Back Button / Gesture)**:
  - من `Flashcards` أو `Quiz` ⬅ يرجع لشاشة `TopicDetail`.
  - من `TopicDetail` ⬅ يرجع لشاشة `HomeScreen` مع الاحتفاظ بمكان التمرير (Scroll position).
  - من التبويبات الفرعية في الشريط السفلي (`Alphabet`, `Favorites`, `Progress`, `Settings`) ⬅ العودة لتبويب `HomeScreen`.
  - الضغط على زر الرجوع في `HomeScreen` ⬅ رسالة تأكيد لطيفة بالدارجة ("واش باغي تخرج؟") أو الخروج من التطبيق.
- **التنقل بدون إعادة تحميل**: جميع حالات الواجهات محفوظة عبر Compose State و ViewModels المرتبطة بالـ NavBackStackEntry.
