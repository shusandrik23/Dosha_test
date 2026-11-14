package com.moyadosha.app.data

/** Надписи и пояснения к тесту «Мой баланс сейчас». */
class VikritiTexts(private val lang: Lang) {
    private val ru = lang == Lang.RU
    private fun t(ru: String, en: String) = if (this.ru) ru else en

    val chooseTest = t("Что хотите узнать?", "What would you like to find out?")
    val prakritiTitle = t("Моя природа", "My nature")
    val prakritiSub = t("Врождённая доша (Пракрити)", "Dosha you were born with (Prakriti)")
    val vikritiTitle = t("Мой баланс сейчас", "My balance now")
    val vikritiSub = t("Что вышло из равновесия (Викрити), 20 вопросов", "What is out of balance (Vikriti), 20 questions")

    fun note(kid: Boolean) =
        if (kid) t(
            "🧸 Вспомни, как ты себя чувствовал последние 2–3 недели. Можно отвечать вместе с мамой или папой.",
            "🧸 Think about how you've felt in the last 2–3 weeks. You can answer together with mum or dad.",
        ) else t(
            "📝 Отвечайте, опираясь на своё самочувствие за последние 2–4 недели. Если всё как обычно — выбирайте этот вариант.",
            "📝 Answer based on how you've felt over the last 2–4 weeks. If everything is as usual, choose that option.",
        )

    fun hint(kid: Boolean) =
        if (kid) t("Как было в последние 2–3 недели", "How it's been in the last 2–3 weeks")
        else t("Как было в последние 2–4 недели", "How it's been over the last 2–4 weeks")

    val norm = t("Норма", "Normal")

    fun header(kid: Boolean) = if (kid) t("твой баланс сейчас", "your balance now") else t("ваш баланс сейчас", "your balance now")

    fun title(r: VikritiResult) = when (r.leading.size) {
        0 -> t("Доши в балансе", "Doshas in balance")
        1 -> t("${r.leading[0].title(lang)} вне равновесия", "${r.leading[0].title(lang)} out of balance")
        else -> t(
            "Двойной дисбаланс: ${r.leading.joinToString("-") { it.title(lang) }}",
            "Double imbalance: ${r.leading.joinToString("-") { it.title(lang) }}",
        )
    }

    fun proportions(kid: Boolean) = if (kid) t("Твои ответы", "Your answers") else t("Ваши ответы", "Your answers")
    fun whatItMeans(kid: Boolean) = if (kid) t("Что это значит", "What it means") else t("Что это значит", "What it means")
    fun tipsTitle(kid: Boolean) = if (kid) t("Как вернуть баланс", "How to get back in balance") else t("Как вернуть баланс", "How to restore balance")
    fun forDosha(d: Dosha) = t("Чтобы успокоить ${d.title(lang)}", "To calm ${d.title(lang)}")
    val signs = t("⚠️ Признаки, что доша вышла из равновесия", "⚠️ Signs this dosha is out of balance")

    val checkBalance = t("Проверить баланс сейчас", "Check my balance now")
    val takePrakriti = t("Узнать свою природную дошу", "Find out my natural dosha")

    fun summary(r: VikritiResult, kid: Boolean): String {
        if (r.balanced) return if (kid)
            t("Отличный баланс: организм работает слаженно. Продолжай в том же духе!",
                "Great balance: your body is working in harmony. Keep it up!")
        else
            t("Сейчас у вас хороший баланс: в большинстве вопросов всё как обычно. Достаточно поддерживать привычный образ жизни.",
                "Your balance is good right now: in most questions everything is as usual. Just keep up your usual lifestyle.")
        val parts = r.leading.map { cause(it, kid) }
        val lead = when {
            r.leading.size == 1 && kid -> t("Сейчас больше всего «шалит» ${r.leading[0].title(lang)}.", "Right now ${r.leading[0].title(lang)} is the most out of balance.")
            r.leading.size == 1 -> t("Сейчас из равновесия вышла ${r.leading[0].title(lang)}, и советы ниже помогут её успокоить.",
                "Right now ${r.leading[0].title(lang)} is out of balance, and the tips below will help calm it.")
            kid -> t("Сейчас «шалят» сразу две доши: ${r.leading.joinToString(" и ") { it.title(lang) }}.",
                "Right now two doshas are out of balance: ${r.leading.joinToString(" and ") { it.title(lang) }}.")
            else -> t("Сейчас у вас двойной дисбаланс: ${r.leading.joinToString(" и ") { it.title(lang) }} набрали почти поровну. Советы ниже — для обеих дош.",
                "You have a double imbalance right now: ${r.leading.joinToString(" and ") { it.title(lang) }} scored almost equally. The tips below are for both doshas.")
        }
        val extra = if (!kid && r.leading.toSet() == setOf(Dosha.VATA, Dosha.PITTA))
            " " + t("Такое сочетание часто бывает от сильного стресса и переработок.", "This combination often comes from strong stress and overwork.")
        else ""
        return lead + extra + "\n\n" + parts.joinToString("\n\n")
    }

    private fun cause(d: Dosha, kid: Boolean): String = when (d) {
        Dosha.VATA -> if (kid) t(
            "🌬️ Вата: ты переутомился, перевозбудился от впечатлений или гаджетов, или сбился режим. Нужно больше тепла, объятий, спокойных игр, тёплой еды и пораньше ложиться спать.",
            "🌬️ Vata: you're overtired, overexcited by impressions or gadgets, or your routine is off. You need more warmth, hugs, calm games, warm food and an early bedtime.",
        ) else t(
            "🌬️ Вата чаще всего выходит из равновесия от стресса, недосыпа, поездок, нерегулярной еды, холода и ветра.",
            "🌬️ Vata usually goes out of balance from stress, lack of sleep, travel, irregular meals, cold and wind.",
        )
        Dosha.PITTA -> if (kid) t(
            "🔥 Питта: ты «перегрелся» от нагрузок, соревнований или жары. Помогут прохладные напитки, спокойное плавание и прогулки, меньше экранов и игр на победу.",
            "🔥 Pitta: you've \"overheated\" from activity, competitions or heat. Cool drinks, calm swimming and walks, less screen time and fewer games about winning will help.",
        ) else t(
            "🔥 Питта чаще всего выходит из равновесия от переработок, жары, острой и жирной еды, алкоголя, соревнований и раздражения.",
            "🔥 Pitta usually goes out of balance from overwork, heat, spicy and fatty food, alcohol, competition and irritation.",
        )
        Dosha.KAPHA -> if (kid) t(
            "🌿 Капха: накопилась вялость или сопли — так бывает после сладостей, когда мало двигаешься, или в сырую погоду. Нужно больше бегать на свежем воздухе, есть лёгкую тёплую еду и поменьше сладкого.",
            "🌿 Kapha: sluggishness or a runny nose has built up — this happens after sweets, too little movement or in damp weather. You need more running outdoors, light warm food and fewer sweets.",
        ) else t(
            "🌿 Капха чаще всего выходит из равновесия от малоподвижности, переедания, сладкого и мучного, дневного сна и сырой прохладной погоды.",
            "🌿 Kapha usually goes out of balance from inactivity, overeating, sweets and starchy food, daytime naps and damp, cool weather.",
        )
    }

    fun shareText(name: String, age: Int, kid: Boolean, r: VikritiResult): String = buildString {
        val s = UiStrings(lang)
        val rec = Recommendations(lang)
        val who = name.trim().ifEmpty { t("Я", "Me") }
        appendLine(t("🪷 Dosha test — баланс сейчас", "🪷 Dosha test — balance now"))
        appendLine()
        appendLine("$who, $age ${s.years(age)}")
        appendLine(title(r))
        appendLine("$norm ${r.percents.getValue(null)}% · " + Dosha.entries.joinToString(" · ") { "${it.title(lang)} ${r.percents.getValue(it)}%" })
        appendLine()
        appendLine(summary(r, kid))
        if (!r.balanced) {
            appendLine()
            appendLine(t("Главные советы:", "Top tips:"))
            val per = if (r.leading.size == 1) 3 else 2
            r.leading.forEach { d ->
                rec.profile(d, kid).sections.take(per).forEach { sec -> appendLine("• ${sec.emoji} ${sec.tips.first()}") }
            }
        }
        appendLine()
        append(t("А какой баланс у тебя? Пройди Dosha test 🌬️🔥🌿", "What's your balance? Take the Dosha test 🌬️🔥🌿"))
    }
}
