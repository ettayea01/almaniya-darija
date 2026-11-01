# تقرير الفحص والهندسة العكسية لـ APK (APK Reverse Engineering Report)

## 1. المعرفات الأساسية (App Identification)
- **اسم الحزمة (Package Name)**: `com.german.zeemish`
- **اسم التطبيق الأصلي**: Learn German (Zeemish)
- **إصدار التطبيق (Version Name)**: `64.0`
- **نوع الحزمة**: Android App Bundle Split APKs (`German_64.0.apks`)
- **الملفات المستخرجة**:
  - `base.apk` (التطبيق الأساسي والكود)
  - `split_config.ar.apk`, `split_config.en.apk`, `split_config.fr.apk`, `split_config.es.apk`
  - `split_config.arm64_v8a.apk`
  - `split_config.xhdpi.apk`

## 2. إعدادات النظام ومستوى SDK (SDK & System Specs)
- **Minimum SDK**: 24 (Android 7.0 Nougat)
- **Target SDK**: 34 / 35 (Android 14 / 15)
- **المعمارية المدعومة**: `arm64-v8a`, `armeabi-v7a`
- **Application Class**: `com.german.zeemish.AdMobAppClass`

## 3. الصلاحيات المطلوبة (Permissions Analysis)
- `android.permission.INTERNET` (للإعلانات وخدمات التحليلات)
- `android.permission.ACCESS_NETWORK_STATE` (التحقق من حالة الاتصال)
- `android.permission.POST_NOTIFICATIONS` (إشعارات التذكير اليومي بالدراسة)
- `android.permission.WAKE_LOCK` (تنبيهات خلفية)
- `com.android.vending.BILLING` (نظام الاشتراكات والدفع في التطبيق الأصلي)
- `android.permission.ACCESS_ADSERVICES_*` و `AD_ID` (خاصة بـ Google Mobile Ads)

> **ملاحظة التطوير الجديد**: التطبيق الجديد سيشتغل محلياً 100% بدون الحاجة لـ BILLING أو AD_ID، وسنعتمد فقط على الصلاحيات الضرورية مثل الإشعارات وحفظ التفضيلات.

## 4. مكونات التطبيق والأنشطة (Activities & Components)
1. **`com.german.zeemish.ui.onbording_flow.StartScreenActivity`**: شاشة البداية واختيار اللغة والمستوى.
2. **`com.german.zeemish.ui.onbording_flow.OnBoardingScreenActivity`**: شاشات الشرح والتعريف بمزايا التطبيق.
3. **`com.german.zeemish.ui.onboarding_end_flow.OnBoardingEndFlowActivity`**: إنهاء التهيئة والانتقال للواجهة الرئيسية.
4. **`com.german.zeemish.ui.home.HomeActivity`**: النشاط الرئيسي الذي يحتوي على القوائم، الأقسام، والبطاقات التعليمية.
5. **`com.german.zeemish.ui.home.WebViewActivity`**: شاشة عرض القواعد النظرية (Grammar & Theory) عبر HTML.

## 5. هيكلية البيانات والمحتوى (Data & Asset Architecture)
- **المحتوى التعليمي**: 50 ملف بيانات بصيغة XML في `assets/data/`:
  - `adjective.xml`, `adverb.xml`, `animals.xml`, `appemotions.xml`, `bodyparts.xml`, `calendar.xml`, `climate.xml`, `clothes.xml`, `colors.xml`, `conjunction.xml`, `constarter.xml`, `countries.xml`, `cquestions.xml`, `cropsplants.xml`, `dailyroutine.xml`, `definitearticle.xml`, `directions.xml`, `family.xml`, `fooddrink.xml`, `fruitveg.xml`, `greetings.xml`, `healthsafety.xml`, `holidaysym.xml`, `house.xml`, `hundredwords.xml`, `indefinitearticle.xml`, `instructions.xml`, `love.xml`, `moneyfinance.xml`, `nouns.xml`, `numbers.xml`, `outdoors.xml`, `personality.xml`, `politeexp.xml`, `praise.xml`, `preposition.xml`, `profession.xml`, `pronouns.xml`, `punctuation.xml`, `school.xml`, `shapes.xml`, `shopping.xml`, `shopsplaces.xml`, `technology.xml`, `time.xml`, `toolseq.xml`, `transport.xml`, `verb.xml`, `vowels.xml`.
- **المحتوى النظري (Theory)**: ملفات HTML في `assets/theory/` تدعم النمطين الفاتح والداكن.
- **الوسائط**: صور البطاقات في `assets/basics/` و `assets/main/` وصور الحروف في `assets/alphabets/`.
- **قواعد البيانات**: التطبيق الأصلي كان يعتمد على XMLs في assets مع Room Database لإدارة تفضيلات المستخدم وسجل التقدم.

## 6. القيود المستخلصة وخطة البناء
التطبيق الأصلي كان يضع بعض الأقسام خلف جدار دفع (Paywall / In-App Purchases) ويعرض إعلانات كثيفة.
في تطبيقنا المعاد بناؤه:
1. الواجهة بالكامل بالدارجة المغربية المكتوبة بحرف عربي واضح.
2. توفير جميع الأقسام والدروس والكويزات مجاناً 100%.
3. دعم كامل للوضع غير المتصل (Offline First) مع Room Database محلياً.
