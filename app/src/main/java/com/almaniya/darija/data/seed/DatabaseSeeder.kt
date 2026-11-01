package com.almaniya.darija.data.seed

import com.almaniya.darija.data.local.dao.ProgressDao
import com.almaniya.darija.data.local.dao.TopicDao
import com.almaniya.darija.data.local.dao.WordDao
import com.almaniya.darija.data.local.entity.TopicEntity
import com.almaniya.darija.data.local.entity.UserProgressEntity
import com.almaniya.darija.data.local.entity.WordEntity

object DatabaseSeeder {

    suspend fun seedIfEmpty(topicDao: TopicDao, wordDao: WordDao, progressDao: ProgressDao) {
        if (topicDao.getCount() == 0) {
            val topics = listOf(
                TopicEntity(
                    id = "greetings",
                    titleDarija = "التحيات والسلام",
                    titleGerman = "Begrüßungen",
                    descriptionDarija = "كيفاش تسلم وتقدم راسك وتسول فالناس بالألمانية والدارجة.",
                    iconName = "waving_hand",
                    category = "BASICS",
                    grammarNoteDarija = "في الألمانية، التحيات كتختلف واش مع صحابك (غير رسمي: Hallo / Tschüss) ولا فإطار مهني أو رسمي (رسمي: Guten Tag / Auf Wiedersehen)."
                ),
                TopicEntity(
                    id = "numbers",
                    titleDarija = "الأرقام والأعداد",
                    titleGerman = "Zahlen",
                    descriptionDarija = "تعلم العد والحساب من صفر لفوق بسهولة تامة.",
                    iconName = "numbers",
                    category = "BASICS",
                    grammarNoteDarija = "ابتداءً من رقم 21 فما فوق، الألمان كينطقو الوحدات قبل العشرات بحال العربية تماماً! مثلاً: 21 هي einundzwanzig (واحد وعشرون)."
                ),
                TopicEntity(
                    id = "family",
                    titleDarija = "العائلة والأقارب",
                    titleGerman = "Familie",
                    descriptionDarija = "أسماء أفراد الأسرة (الأب، الأم، الخوت، العائلة).",
                    iconName = "diversity_1",
                    category = "BASICS",
                    grammarNoteDarija = "كل اسم فالعائلة عندو أداة خاصة: الأب mülk مذكر (der Vater)، الأم مؤنث (die Mutter)، الطفل محايد (das Kind)."
                ),
                TopicEntity(
                    id = "daily_routine",
                    titleDarija = "الروتين اليومي",
                    titleGerman = "Tagesablauf",
                    descriptionDarija = "الأنشطة اليومية، الفياق، الخدمة، الماكلة والنعاس.",
                    iconName = "schedule",
                    category = "DAILY_LIFE",
                    grammarNoteDarija = "الأفعال القابلة للانفصال (Trennbare Verben) شائعة بزاف فالروتين اليومي: بحال aufstehen (فاق): 'Ich stehe um 7 Uhr auf'."
                ),
                TopicEntity(
                    id = "food_drink",
                    titleDarija = "الماكلة والشراب",
                    titleGerman = "Essen & Trinken",
                    descriptionDarija = "المأكولات، الخضر والفواكه، والمشروبات وفالمطعم.",
                    iconName = "restaurant",
                    category = "DAILY_LIFE",
                    grammarNoteDarija = "فالمطعم إلا بغيتي تطلب شي حاجة بأدب كتقول: 'Ich möchte bitte...' (عفاك بغيت...)."
                ),
                TopicEntity(
                    id = "definite_articles",
                    titleDarija = "أدوات التعريف (der, die, das)",
                    titleGerman = "Bestimmte Artikel",
                    descriptionDarija = "القاعدة الذهبية لفهم المذكر والمؤنث والمحايد.",
                    iconName = "menu_book",
                    category = "GRAMMAR",
                    grammarNoteDarija = "الألمانية فيها 3 ديال الجنسيات للكلمات:\n- der للمذكر (Maskulin)\n- die للمؤنث (Feminin)\n- das للمحايد (Neutral)\n- die للجمع (Plural)."
                ),
                TopicEntity(
                    id = "indefinite_articles",
                    titleDarija = "أدوات التنكير (ein, eine)",
                    titleGerman = "Unbestimmte Artikel",
                    descriptionDarija = "كيفاش تقول 'شي كتاب' ولا 'شي طوموبيل' بدون تعريف.",
                    iconName = "article",
                    category = "GRAMMAR",
                    grammarNoteDarija = "أداة النكرة فالمذكر والمحايد هي 'ein'، وفالمؤنث هي 'eine'. والجمع ما عندوش أداة نكرة أبداً!"
                ),
                TopicEntity(
                    id = "conversations",
                    titleDarija = "محادثات وأسئلة شائعة",
                    titleGerman = "Gespräche & Fragen",
                    descriptionDarija = "الجمل اللي غتحتاجها فالسوق، عند الطبيب، ولا فالمحطة.",
                    iconName = "chat",
                    category = "CONVERSATION",
                    grammarNoteDarija = "الأسئلة اللي كتبدا بـ W-Fragen بحال: Wo (فين)، Wann (فوقاش)، Warum (علاش)، Wie (كيفاش) ديما الفعل كيكون هو الرقم 2 فالجملة."
                )
            )
            topicDao.insertTopics(topics)
        }

        if (wordDao.getCount() == 0) {
            val words = listOf(
                // Greetings
                WordEntity(topicId = "greetings", german = "Hallo!", darija = "أهلاً / السلام عليكم", phonetic = "هالو", exampleGerman = "Hallo! Wie geht's?", exampleDarija = "أهلاً! كيداير؟"),
                WordEntity(topicId = "greetings", german = "Guten Morgen", darija = "صباح الخير", phonetic = "ڭوتن موغڭن", exampleGerman = "Guten Morgen, mein Freund!", exampleDarija = "صباح الخير يا صاحبي!"),
                WordEntity(topicId = "greetings", german = "Guten Tag", darija = "نهاركم مبروك / السلام عليكم (فالنهار)", phonetic = "ڭوتن تاك", exampleGerman = "Guten Tag, Herr Müller.", exampleDarija = "نهاركم مبروك أ سي مولر."),
                WordEntity(topicId = "greetings", german = "Guten Abend", darija = "مساء الخير", phonetic = "ڭوتن آبند", exampleGerman = "Guten Abend allerseits!", exampleDarija = "مساء الخير على الجميع!"),
                WordEntity(topicId = "greetings", german = "Gute Nacht", darija = "تصبح على خير (قبل النعاس)", phonetic = "ڭوته ناخت", exampleGerman = "Gute Nacht, schlaf gut!", exampleDarija = "تصبح على خير، نعس مزيان!"),
                WordEntity(topicId = "greetings", german = "Wie geht es dir?", darija = "كيداير؟ / كيدايرة؟ (مع صحابك)", phonetic = "في ڭيت إس ديا؟", exampleGerman = "Hallo Sara, wie geht es dir?", exampleDarija = "أهلاً سارة، كيدايرة؟"),
                WordEntity(topicId = "greetings", german = "Wie geht es Ihnen?", darija = "كي داير سعادتك؟ (باحترام ورسمي)", phonetic = "في ڭيت إس إينن؟", exampleGerman = "Guten Tag, wie geht es Ihnen?", exampleDarija = "السلام عليكم، كي داير سيادتك؟"),
                WordEntity(topicId = "greetings", german = "Mir geht es gut, danke!", darija = "أنا بخير، شكراً!", phonetic = "ميا ڭيت إس ڭوت، دانكه", exampleGerman = "Mir geht es gut, danke! Und dir?", exampleDarija = "أنا بيخير، شكراً! ونتا؟"),
                WordEntity(topicId = "greetings", german = "Auf Wiedersehen!", darija = "بالسلامة / الله يعاون (رسمي)", phonetic = "أوف فيدازين", exampleGerman = "Auf Wiedersehen und danke!", exampleDarija = "بالسلامة وشكراً بزاف!"),
                WordEntity(topicId = "greetings", german = "Tschüss!", darija = "تشاو / بسلامة (بين الصحاب)", phonetic = "تشوس", exampleGerman = "Tschüss! Bis morgen!", exampleDarija = "بسلامة! حتى لغدا!"),
                WordEntity(topicId = "greetings", german = "Bis bald!", darija = "نشوفك قريب إن شاء الله", phonetic = "بس بالت", exampleGerman = "Bis bald, pass auf dich auf!", exampleDarija = "نشوفك قريب، تهلا فراسك!"),
                WordEntity(topicId = "greetings", german = "Bitte", darija = "عفاك / مرحبا (العفو)", phonetic = "بيته", exampleGerman = "Ein Wasser, bitte.", exampleDarija = "كاس د الما، عفاك."),

                // Numbers
                WordEntity(topicId = "numbers", german = "Null", darija = "صفر", phonetic = "نول"),
                WordEntity(topicId = "numbers", german = "Eins", darija = "واحد", phonetic = "آينس"),
                WordEntity(topicId = "numbers", german = "Zwei", darija = "جوج", phonetic = "تسفاي"),
                WordEntity(topicId = "numbers", german = "Drei", darija = "تلاتة", phonetic = "دغاي"),
                WordEntity(topicId = "numbers", german = "Vier", darija = "ربعة", phonetic = "فيا"),
                WordEntity(topicId = "numbers", german = "Fünf", darija = "خمسة", phonetic = "فونف"),
                WordEntity(topicId = "numbers", german = "Sechs", darija = "ستة", phonetic = "زيكس"),
                WordEntity(topicId = "numbers", german = "Sieben", darija = "سبعة", phonetic = "زيبن"),
                WordEntity(topicId = "numbers", german = "Acht", darija = "تمنية", phonetic = "أخت"),
                WordEntity(topicId = "numbers", german = "Neun", darija = "تسعود", phonetic = "نوين"),
                WordEntity(topicId = "numbers", german = "Zehn", darija = "عشرة", phonetic = "تسين"),
                WordEntity(topicId = "numbers", german = "Zwanzig", darija = "عشرين", phonetic = "تسفانتسيش"),
                WordEntity(topicId = "numbers", german = "Einundzwanzig", darija = "واحد وعشرين", phonetic = "آين-أوند-تسفانتسيش"),
                WordEntity(topicId = "numbers", german = "Hundert", darija = "مية (100)", phonetic = "هوندغت"),
                WordEntity(topicId = "numbers", german = "Tausend", darija = "ألف (1000)", phonetic = "تاوزند"),

                // Family
                WordEntity(topicId = "family", german = "der Vater", darija = "الواليد / الأب", phonetic = "ديا فاتا", gender = "DER"),
                WordEntity(topicId = "family", german = "die Mutter", darija = "الواليدة / الأم", phonetic = "دي موتا", gender = "DIE"),
                WordEntity(topicId = "family", german = "der Sohn", darija = "الولد / الابن", phonetic = "ديا زون", gender = "DER"),
                WordEntity(topicId = "family", german = "die Tochter", darija = "البنت / الابنة", phonetic = "دي توختا", gender = "DIE"),
                WordEntity(topicId = "family", german = "der Bruder", darija = "الأخ", phonetic = "ديا بغودا", gender = "DER"),
                WordEntity(topicId = "family", german = "die Schwester", darija = "الأخت", phonetic = "دي شفستا", gender = "DIE"),
                WordEntity(topicId = "family", german = "der Großvater", darija = "الجد / با سيدي", phonetic = "ديا غوس فاتا", gender = "DER"),
                WordEntity(topicId = "family", german = "die Großmutter", darija = "الجدة / مي لالة", phonetic = "دي غوس موتا", gender = "DIE"),
                WordEntity(topicId = "family", german = "das Kind", darija = "الدري / الطفل", phonetic = "داس كينت", gender = "DAS"),

                // Food & Drink
                WordEntity(topicId = "food_drink", german = "das Wasser", darija = "الما", phonetic = "داس فاصا", gender = "DAS"),
                WordEntity(topicId = "food_drink", german = "das Brot", darija = "الخبز", phonetic = "داس بغوت", gender = "DAS"),
                WordEntity(topicId = "food_drink", german = "der Kaffee", darija = "القهوة", phonetic = "ديا كافيه", gender = "DER"),
                WordEntity(topicId = "food_drink", german = "der Tee", darija = "أتاي / الشاي", phonetic = "ديا تيه", gender = "DER"),
                WordEntity(topicId = "food_drink", german = "die Milch", darija = "الحليب", phonetic = "دي ميلش", gender = "DIE"),
                WordEntity(topicId = "food_drink", german = "der Apfel", darija = "التفاحة", phonetic = "ديا أبفل", gender = "DER"),
                WordEntity(topicId = "food_drink", german = "das Fleisch", darija = "اللحم", phonetic = "داس فلايش", gender = "DAS"),
                WordEntity(topicId = "food_drink", german = "der Fisch", darija = "الحوت / السمك", phonetic = "ديا فيش", gender = "DER"),
                WordEntity(topicId = "food_drink", german = "das Frühstück", darija = "الفطور", phonetic = "داس فغوشتوك", gender = "DAS"),
                WordEntity(topicId = "food_drink", german = "das Abendessen", darija = "العشا", phonetic = "داس آبند-إسن", gender = "DAS"),

                // Conversations
                WordEntity(topicId = "conversations", german = "Wie heißen Sie?", darija = "شنو سميتك؟ (باحترام)", phonetic = "في هايسن زي؟", exampleGerman = "Wie heißen Sie? - Ich heiße Mehdi.", exampleDarija = "شنو سميتك؟ - سميتي مهدي."),
                WordEntity(topicId = "conversations", german = "Woher kommen Sie?", darija = "منين نتا أ سيدي؟", phonetic = "فوهيا كومن زي؟", exampleGerman = "Ich komme aus Marokko.", exampleDarija = "أنا جاي من المغرب."),
                WordEntity(topicId = "conversations", german = "Sprechen Sie Arabisch?", darija = "واش كتهضر بالعربية؟", phonetic = "شبغشن زي أغابيش؟", exampleGerman = "Ja, ein bisschen.", exampleDarija = "إيه، شوية."),
                WordEntity(topicId = "conversations", german = "Ich verstehe nicht", darija = "ما فهمتش", phonetic = "إش فغشتيه نيشت", exampleGerman = "Entschuldigung, ich verstehe nicht.", exampleDarija = "سمح ليا، ما فهمتش."),
                WordEntity(topicId = "conversations", german = "Wo ist die Toilette?", darija = "فين كاين بيت الما (المرحاض)؟", phonetic = "فو إست دي تواليته؟"),
                WordEntity(topicId = "conversations", german = "Wie viel kostet das?", darija = "بشحال هادي؟", phonetic = "في فيل كوستت داس؟", exampleGerman = "Wie viel kostet das Brot?", exampleDarija = "بشحال هاد الخبز؟")
            )
            wordDao.insertWords(words)
        }

        if (progressDao.getUserProgressDirect() == null) {
            progressDao.insertOrUpdate(
                UserProgressEntity(
                    id = 1,
                    streakDays = 1,
                    lastStudyDate = System.currentTimeMillis(),
                    totalQuizzesCompleted = 0,
                    totalCorrectAnswers = 0,
                    totalQuestionsAnswered = 0
                )
            )
        }
    }
}
