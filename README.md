# ألماني بالدارجة 🇲🇦 🇩🇪 (Almaniya Darija)

تطبيق أندرويد عصري واحترافي مبني من الصفر لتعلم اللغة الألمانية بالدارجة المغربية، مستوحى من الهندسة العكسية لتطبيق `com.german.zeemish` مع تحويل كامل لكل المحتوى إلى الدارجة، وإتاحة جميع المزايا مجاناً 100% وبدون إنترنت (Offline First).

---

## 🌟 الميزات الأساسية (Key Features)

1. **محتوى بالدارجة المغربية بالكامل**:
   - جميع الترجمات والشروحات مكتوبة بدارجة مغربية مفهومة وسلسة.
   - أمثلة حية من واقع الحياة اليومية لتسهيل الفهم والحفظ.

2. **قواعد مبسطة (Grammatik بالدارجة)**:
   - شرح أدوات التعريف (`der`, `die`, `das`) وأدوات النكرة (`ein`, `eine`).
   - شرح الأفعال القابلة للانفصال (Trennbare Verben).
   - تركيب الأسئلة والجمل في الألمانية.

3. **الأبجدية والنطق الصوتي (Alphabet & Pronunciation)**:
   - الحروف الألمانية الـ 30 كاملة بما فيها الـ Umlaute (`Ä`, `Ö`, `Ü`) و `ß`.
   - شرح صوتي لطريقة نطق كل حرف بالدارجة مع أمثلة.
   - استماع فوري عبر محرك نطق أندرويد الأصيل (Android Native TextToSpeech).

4. **بطاقات المراجعة ثلاثية الأبعاد (3D Interactive Flashcards)**:
   - بطاقات تفاعلية تقلب بلمسة واحدة لمراجعة الكلمات بسرعة.
   - زر "عقلت عليها مزيان! حفظتها ✅" لحفظ التقدم في قاعدة البيانات.

5. **نظام كويزات تفاعلي (Interactive Quiz Engine)**:
   - كويز قراءة: اختيار المعنى بالدارجة.
   - كويز استماع: الاستماع لنطق الكلمة واختيار الترجمة.
   - تصحيح فوري مع احتساب النتيجة والنقاط.

6. **قائمة المفضلة والكلمات الصعبة (Favorites)**:
   - إمكانية وسم أي كلمة بنجمة والرجوع إليها في أي وقت.

7. **إحصائيات التعلم والـ Streak (Progress Tracker)**:
   - عداد الأيام المتتالية للدراسة.
   - نسبة الكلمات المحفوظة وإجمالي الكويزات المنجزة ونسبة الدقة.

8. **100% مجاني وبلا إنترنت (Free & Offline)**:
   - لا توجد إعلانات (Zero Ads).
   - لا توجد حواجز دفع (No Paywalls).
   - لا يحتاج لأي اتصال بالإنترنت (Offline First via Room Database).

---

## 🏗️ البنية التقنية (Architecture & Tech Stack)

- **اللغة**: Kotlin 2.0+
- **واجهة المستخدم**: Jetpack Compose + Material 3 Design
- **المعمارية**: Clean Architecture (Data, Domain, Presentation) + MVVM
- **قاعدة البيانات المحلية**: Room Database (SQLite) مع Prepopulated Seed
- **التفضيلات**: DataStore Preferences
- **الصوت**: Android Native `TextToSpeech` (Locale.GERMANY)
- **التنقل**: Navigation Compose

---

## 📁 هيكلية المشروع (Project Tree)

```
app/
 ├── src/main/java/com/almaniya/darija/
 │    ├── MainActivity.kt
 │    ├── AlmaniyaApp.kt
 │    ├── data/
 │    │    ├── local/
 │    │    │    ├── AppDatabase.kt
 │    │    │    ├── dao/Daos.kt
 │    │    │    └── entity/Entities.kt
 │    │    ├── repository/AlmaniyaRepositoryImpl.kt
 │    │    └── seed/DatabaseSeeder.kt
 │    ├── domain/
 │    │    ├── model/Models.kt
 │    │    └── repository/AlmaniyaRepository.kt
 │    ├── presentation/
 │    │    ├── theme/ (Color.kt, Theme.kt)
 │    │    ├── navigation/ (NavRoutes.kt, AppNavHost.kt)
 │    │    ├── components/ (Components.kt)
 │    │    └── screens/
 │    │         ├── home/ (HomeScreen.kt, HomeViewModel.kt)
 │    │         ├── topic/ (TopicDetailScreen.kt, TopicViewModel.kt)
 │    │         ├── flashcards/ (FlashcardsScreen.kt, FlashcardsViewModel.kt)
 │    │         ├── quiz/ (QuizScreen.kt, QuizViewModel.kt)
 │    │         ├── alphabet/ (AlphabetScreen.kt)
 │    │         ├── favorites/ (FavoritesScreen.kt, FavoritesViewModel.kt)
 │    │         ├── progress/ (ProgressScreen.kt, ProgressViewModel.kt)
 │    │         └── settings/ (SettingsScreen.kt, SettingsViewModel.kt)
 │    └── utils/SpeechHelper.kt
 └── src/main/res/ (strings, colors, themes, adaptive mipmap icons)
```

---

## 🚀 تشغيل وبناء التطبيق

1. افتح المشروع في **Android Studio** (نسخة Ladybug أو أحدث).
2. انتظر اكتمال الـ Gradle Sync.
3. شغل التطبيق على هاتفك أو على المحاكي عبر زر **Run** (أو `Shift + F10`).
