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


    val aboutTitle = t("Что такое доши и зачем их знать?", "What are doshas and why know them?")
    val aboutText = t(
        """Аюрведа — традиционная индийская система здоровья, которой больше 3000 лет. Она описывает три доши — три силы, из которых, по её представлениям, складываются тело и характер человека:

• Вата (воздух и эфир) — движение: дыхание, кровообращение, нервная система, мысли.
• Питта (огонь и вода) — превращение: пищеварение, обмен веществ, температура тела, ясность ума и решимость.
• Капха (земля и вода) — устойчивость: строение тела, суставы, иммунитет, спокойствие и выносливость.

Все три доши есть в каждом человеке, но в разной пропорции. Пропорция, с которой вы родились, — ваша природная конституция (Пракрити); она остаётся с вами всю жизнь. Текущее состояние (Викрити) меняется от стресса, питания, сезона и возраста: когда какая-то доша «выходит вперёд», появляется дисбаланс.

Зачем это знать:
• понять свои особенности — почему вы мёрзнете или, наоборот, вам всегда жарко, почему легко набираете вес или не можете поправиться;
• подобрать еду, режим дня и движение под себя, а не по чужим советам;
• вовремя заметить дисбаланс и мягко вернуть себя в равновесие;
• лучше понимать близких и детей — у каждого своя природа.

Тест «Моя природа» определяет врождённую дошу, тест «Мой баланс сейчас» — что вышло из равновесия за последние недели.""",
        """Ayurveda is a traditional Indian health system more than 3,000 years old. It describes three doshas — three forces that, in its view, make up a person's body and character:

• Vata (air and space) — movement: breathing, circulation, the nervous system, thoughts.
• Pitta (fire and water) — transformation: digestion, metabolism, body temperature, a clear mind and determination.
• Kapha (earth and water) — stability: body structure, joints, immunity, calm and stamina.

All three doshas are present in everyone, but in different proportions. The proportion you were born with is your natural constitution (Prakriti); it stays with you for life. Your current state (Vikriti) changes with stress, food, season and age: when one dosha "comes forward", an imbalance appears.

Why know this:
• to understand yourself — why you're always cold or always hot, why you gain weight easily or can't put it on;
• to choose food, daily routine and exercise that suit you, not someone else;
• to notice an imbalance early and gently get back into balance;
• to better understand your family and children — everyone has their own nature.

The "My nature" test finds the dosha you were born with; the "My balance now" test shows what has gone out of balance in recent weeks.""",
    )


    val save = t("Сохранить результат", "Save result")
    val saved = t("✓ Сохранено", "✓ Saved")
    fun myResults(n: Int) = t("📂 Мои результаты ($n)", "📂 My results ($n)")
    val historyTitle = t("Мои результаты", "My results")
    val historyEmpty = t("Здесь появятся сохранённые результаты.", "Saved results will appear here.")
    val delete = t("Удалить", "Delete")
    val deleteTitle = t("Удалить результат?", "Delete this result?")
    val cancel = t("Отмена", "Cancel")
    fun takenOn(date: String) = t("Пройден: $date", "Taken: $date")
    fun formatDate(ts: Long): String =
        java.text.DateFormat.getDateTimeInstance(
            java.text.DateFormat.MEDIUM, java.text.DateFormat.SHORT,
            if (ru) java.util.Locale("ru") else java.util.Locale.ENGLISH,
        ).format(java.util.Date(ts))


    val dontKnow = t("🤔 Не знаю, что ответить", "🤔 I don't know what to answer")
    val backToQuestion = t("← Вернуться к общему вопросу", "← Back to the general question")
    val situationLabel = t("Представьте ситуацию", "Imagine this situation")
    val home = t("На главную", "Home")


    val rateNo = t("Не про меня", "Not me")
    val rateBit = t("Немного", "A little")
    val rateVery = t("Очень похоже", "Very much")
    val scaleHint = t("Отметьте, насколько каждое утверждение похоже на вас", "Mark how much each statement sounds like you")

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
