package com.moyadosha.app.data

/** Все надписи интерфейса на одном языке. */
class UiStrings(val lang: Lang) {
    private val ru = lang == Lang.RU
    private fun t(ru: String, en: String) = if (this.ru) ru else en

    val appName = "Dosha test"
    val intro = t(
        "Узнайте свой тип по Аюрведе — Вата, Питта или Капха — и получите советы по питанию, режиму дня и образу жизни.",
        "Discover your Ayurvedic type — Vata, Pitta or Kapha — and get tips on food, daily routine and lifestyle.",
    )
    val meet = t("Давайте познакомимся", "Let's get acquainted")
    val name = t("Имя", "Name")
    val age = t("Возраст (полных лет)", "Age (years)")
    val ageError = t("Введите возраст от 1 до 110", "Enter an age from 1 to 110")
    val howMany = t("Сколько вопросов?", "How many questions?")
    fun lengthHint(n: Int) = when (n) {
        20 -> t("быстро, ~4 мин", "quick, ~4 min")
        35 -> t("оптимально, ~7 мин", "optimal, ~7 min")
        else -> t("точнее всего, ~10 мин", "most accurate, ~10 min")
    }
    val kidNote = t(
        "🧸 Будет детская версия теста: простые вопросы про игры, еду и сон. Отвечай, как бывает обычно, а не только сегодня или когда болеешь. Если читать пока трудно — пусть помогут мама или папа.",
        "🧸 This will be the kids' version: simple questions about games, food and sleep. Answer how things usually are, not just today or when you're ill. If reading is still hard, ask mum or dad to help.",
    )
    val adultNote = t(
        "📝 Тест определяет вашу природную конституцию — то, какой вы от рождения. Отвечайте, как обычно бывает у вас на протяжении жизни, когда вы здоровы и спокойны, а не как в последние недели, при стрессе или болезни. Выбирайте самый близкий вариант.",
        "📝 The test finds your natural constitution — how you are by birth. Answer how things usually are for you throughout life when you're healthy and calm, not how they've been in recent weeks, under stress or when ill. Choose the closest option.",
    )
    fun questionHint(kid: Boolean) =
        if (kid) t("Как бывает обычно, а не только сегодня", "How it usually is, not just today")
        else t("Как обычно бывает на протяжении жизни, в спокойном состоянии", "How it usually is throughout your life, when you're calm and well")
    val start = t("Начать тест", "Start the test")
    val threeDoshas = t("Три доши", "The three doshas")
    fun doshaIntro(d: Dosha) = when (d) {
        Dosha.VATA -> t("лёгкость, движение, творчество", "lightness, movement, creativity")
        Dosha.PITTA -> t("огонь, сила воли, ясный ум", "fire, willpower, a clear mind")
        Dosha.KAPHA -> t("спокойствие, выносливость, доброта", "calm, stamina, kindness")
    }

    fun questionOf(i: Int, n: Int) = t("Вопрос $i из $n", "Question $i of $n")
    val kidBadge = t("🧸 детский", "🧸 kids")
    val back = t("Назад", "Back")
    val next = t("Далее", "Next")
    val seeResult = t("Узнать результат", "See my result")
    val quitTitle = t("Прервать тест?", "Quit the test?")
    val quitText = t("Ответы не сохранятся.", "Your answers won't be saved.")
    val quit = t("Прервать", "Quit")
    val continueTest = t("Продолжить", "Continue")

    fun yourDosha(kid: Boolean) = if (kid) t("твоя доша", "your dosha") else t("ваша доша", "your dosha")
    fun proportions(kid: Boolean) = if (kid) t("Из чего ты состоишь", "What you're made of") else t("Соотношение дош", "Dosha balance")
    fun yourType(kid: Boolean) = if (kid) t("Какой ты", "What you're like") else t("Ваш тип", "Your type")
    fun tipsTitle(kid: Boolean) = if (kid) t("Советы для тебя", "Tips for you") else t("Рекомендации", "Recommendations")
    fun forDosha(d: Dosha) = t("Для доши ${d.title(lang)}", "For ${d.title(lang)}")
    fun strengths(kid: Boolean) = if (kid) t("💪 Твои сильные стороны", "💪 Your strengths") else t("💪 Сильные стороны", "💪 Strengths")
    fun imbalance(d: Dosha, kid: Boolean) =
        if (kid) t("⚠️ Когда ${d.title(lang)} «шалит»", "⚠️ When ${d.title(lang)} gets naughty")
        else t("⚠️ Признаки дисбаланса", "⚠️ Signs of imbalance")
    val collapse = t("Свернуть", "Collapse")
    val expand = t("Развернуть", "Expand")
    val share = t("Поделиться результатом", "Share my result")
    val retake = t("Пройти заново", "Take the test again")
    val shareSubject = t("Мой результат: Dosha test", "My Dosha test result")

    fun years(n: Int): String {
        if (!ru) return if (n == 1) "year old" else "years old"
        val mod100 = n % 100
        val mod10 = n % 10
        return when {
            mod100 in 11..14 -> "лет"
            mod10 == 1 -> "год"
            mod10 in 2..4 -> "года"
            else -> "лет"
        }
    }
}
