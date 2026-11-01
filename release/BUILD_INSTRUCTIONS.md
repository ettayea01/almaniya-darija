# دليل تثبيت وبناء حزمة ألماني بالدارجة (Release & Build Guide)

## 1. فتح المشروع في Android Studio
1. افتح **Android Studio**.
2. اختر **Open** وحدد المجلد:
   `c:\Users\elmah\Downloads\tisting`
3. سيقوم Android Studio تلقائياً بعمل Gradle Sync وتحميل المكتبات المحددة في `libs.versions.toml`.

## 2. بناء ملف الـ APK (Build APK)
يمكنك بناء الـ APK مباشرة من سطر الأوامر أو من واجهة Android Studio:

### من سطر الأوامر (Terminal):
```bash
./gradlew assembleRelease
# أو لبناء نسخة تجريبية سريعة:
./gradlew assembleDebug
```

الملف الناتج سيتواجد تلقائياً في:
`app/build/outputs/apk/release/app-release-unsigned.apk`
أو
`app/build/outputs/apk/debug/app-debug.apk`

### من واجهة Android Studio:
1. من القائمة العلوية اختر: **Build ⬅ Build Bundle(s) / APK(s) ⬅ Build APK(s)**.
2. ستظهر لك رسالة عند اكتمال البناء مع رابط مباشر لفتح مجلد ملف الـ APK.

## 3. التثبيت على الهاتف أو المحاكي (Installation)
لتثبيت التطبيق على هاتفك عبر سلك USB:
```bash
adb install -r app/build/outputs/apk/debug/app-debug.apk
```
أو إرسال الملف مباشرة للهاتف وتثبيته كأي ملف APK عادي.
