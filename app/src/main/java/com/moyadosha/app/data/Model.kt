package com.moyadosha.app.data

import java.util.Locale

/** Язык приложения: русский, если система на русском, иначе английский. */
enum class Lang {
    RU, EN;

    companion object {
        fun system(locale: Locale = Locale.getDefault()): Lang = if (locale.language == "ru") RU else EN
    }
}

enum class Dosha(
    val emoji: String,
    private val titleRu: String,
    private val titleEn: String,
    private val elementRu: String,
    private val elementEn: String,
) {
    VATA("🌬️", "Вата", "Vata", "воздух и эфир", "air and space"),
    PITTA("🔥", "Питта", "Pitta", "огонь и вода", "fire and water"),
    KAPHA("🌿", "Капха", "Kapha", "земля и вода", "earth and water");

    fun title(lang: Lang) = if (lang == Lang.RU) titleRu else titleEn
    fun element(lang: Lang) = if (lang == Lang.RU) elementRu else elementEn
}

/**
 * Каждый вариант ответа даёт 2 балла: либо целиком одной доше,
 * либо поровну двум (смешанные варианты). Так 6 вариантов покрывают
 * все оттенки: В, П, К, В+П, П+К, В+К.
 */
enum class Mix(val vata: Int, val pitta: Int, val kapha: Int) {
    V(2, 0, 0), P(0, 2, 0), K(0, 0, 2),
    VP(1, 1, 0), PK(0, 1, 1), VK(1, 0, 1);

    fun points(d: Dosha) = when (d) {
        Dosha.VATA -> vata
        Dosha.PITTA -> pitta
        Dosha.KAPHA -> kapha
    }
}

data class Option(val mix: Mix, val text: String)

/** alt — вопрос-ситуация для переключателя «Вопрос / Ситуация» (с теми же баллами). */
data class Question(val text: String, val options: List<Option>, val alt: Question? = null)

/** Утверждения трёх «чистых» дош для шкалы «насколько похоже» (Вата, Питта, Капха). */
fun Question.pureStatements(): List<Pair<Dosha, String>> = listOf(
    Dosha.VATA to options.first { it.mix == Mix.V }.text,
    Dosha.PITTA to options.first { it.mix == Mix.P }.text,
    Dosha.KAPHA to options.first { it.mix == Mix.K }.text,
)

internal fun q(text: String, vararg options: Pair<Mix, String>) =
    Question(text, options.map { Option(it.first, it.second) })

/** Возраст, до которого показывается детская версия теста. */
const val KID_MAX_AGE = 12

/** Варианты длины теста. */
val TEST_LENGTHS = listOf(20, 35, 50)

fun allQuestions(kid: Boolean, lang: Lang): List<Question> = when {
    kid && lang == Lang.RU -> KidQuestions.ru
    kid -> KidQuestionsEn.en
    lang == Lang.RU -> AdultQuestions.ru
    else -> AdultQuestionsEn.en
}

/**
 * Вопросы для теста нужной длины: у 20 и 35 — свои отдельные наборы ([ExtraQuestions]),
 * у 50 — основной список с ситуациями для переключателя.
 */
fun questionsForLength(age: Int, length: Int, lang: Lang): List<Question> {
    val kid = age <= KID_MAX_AGE
    val ru = lang == Lang.RU
    return when (length) {
        20 -> if (kid) (if (ru) ExtraQuestions.kid20 else ExtraQuestionsEn.kid20)
        else (if (ru) ExtraQuestions.adult20 else ExtraQuestionsEn.adult20)
        35 -> if (kid) (if (ru) ExtraQuestions.kid35 else ExtraQuestionsEn.kid35)
        else (if (ru) ExtraQuestions.adult35 else ExtraQuestionsEn.adult35)
        else -> questionsByIndices(age, (0 until 50).toList(), lang)
    }
}

fun questionsByIndices(age: Int, indices: List<Int>, lang: Lang): List<Question> {
    val kid = age <= KID_MAX_AGE
    val situations = Situations.forSet(kid, lang)
    val all = allQuestions(kid, lang)
    return indices.map { i -> all[i].copy(alt = situations[i]) }
}
