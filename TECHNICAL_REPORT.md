# التقرير التقني والمعماري (TECHNICAL_REPORT.md)

## 1. المعمارية المعتمدة (Clean Architecture + MVVM)
تم اختيار معمارية الطبقات النظيفة (Clean Architecture) مع MVVM لضمان استقرار الكود، سهولة الصيانة، وإمكانية اختبار الوظائف:

```
app/
 ├── data/
 │    ├── local/
 │    │    ├── AppDatabase.kt          (قاعدة بيانات Room)
 │    │    ├── dao/                    (WordDao, TopicDao, ProgressDao)
 │    │    └── entities/               (WordEntity, TopicEntity, UserProgressEntity)
 │    ├── repository/                  (تنفيذ واجهات المستودعات وجلب البيانات المحلية)
 │    └── preferences/                 (UserPreferencesDataStore)
 ├── domain/
 │    ├── model/                       (Word, Topic, QuizQuestion, ProgressStats)
 │    ├── repository/                  (واجهات Repository Interfaces)
 │    └── usecases/                    (GetTopicsUseCase, GetWordsByTopicUseCase, SubmitQuizUseCase...)
 ├── presentation/
 │    ├── theme/                       (Color, Type, Shape, Theme - Dark/Light)
 │    ├── navigation/                  (AppNavigation, NavRoutes)
 │    ├── components/                  (Card, QuizOption, TopBar, BottomBar, AudioButton)
 │    └── screens/
 │         ├── splash/                 (SplashViewModel, SplashScreen)
 │         ├── onboarding/             (OnboardingViewModel, OnboardingScreen)
 │         ├── home/                   (HomeViewModel, HomeScreen)
 │         ├── alphabet/               (AlphabetViewModel, AlphabetScreen)
 │         ├── topic/                  (TopicDetailViewModel, TopicDetailScreen)
 │         ├── flashcards/             (FlashcardsViewModel, FlashcardsScreen)
 │         ├── quiz/                   (QuizViewModel, QuizScreen)
 │         ├── favorites/              (FavoritesViewModel, FavoritesScreen)
 │         ├── progress/               (ProgressViewModel, ProgressScreen)
 │         └── settings/               (SettingsViewModel, SettingsScreen)
 └── utils/
      ├── SoundPlayer.kt               (مُشغّل الصوت ونظام TextToSpeech الألماني)
      └── HapticFeedbackUtil.kt        (التغذية اللمسية عند الإجابات)
```

## 2. محرك النطق والصوت (Offline Speech Engine)
بدل الاعتماد على ملفات MP3 خارجية أو اتصالات إنترنت:
- نستخدم محرك **Android Text-To-Speech (`android.speech.tts.TextToSpeech`)** مع تحديد `Locale.GERMANY`.
- هذا المحرك مدمج في جميع أجهزة أندرويد الحديثة ويوفر نطقاً ألمانياً أصيلاً (Native German Accent) بدون الحاجة لأي اتصال بالإنترنت أو تحميل ملفات صوتية ضخمة.

## 3. خطة تحويل البيانات إلى الدارجة المغربية
التطبيق الأصلي يتضمن 50 ملف بيانات XML بآلاف الكلمات والمصطلحات.
في هذا المشروع:
1. تمت قراءة وتحليل ملفات الـ XML.
2. يتم توفير الترجمة والشرح بالدارجة المغربية السلسة والمفهومة (مثلاً: "Wie geht's?" ⬅ "كيداير؟ / كيدايرة؟"، "Guten Morgen" ⬅ "صباح الخير"، "Auf Wiedersehen" ⬅ "بالسلامة / الله يعاون").
3. يتم إدراج البيانات تلقائياً في قاعدة بيانات Room عند أول تشغيل عبر `RoomDatabase.Callback` أو ملف JSON/SQLite محلي.
