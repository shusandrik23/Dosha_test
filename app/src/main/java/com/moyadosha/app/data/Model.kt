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

/**
 * alt — вопрос-ситуация для переключателя «Вопрос / Ситуация» (с теми же баллами).
 * single — вопрос с выбором одного варианта (ответы исключают друг друга): какие варианты
 * показать и в каком порядке. null — обычная шкала «насколько похоже».
 * parts — вопрос из нескольких частей (например, сон: засыпание, чуткость, продолжительность, утро).
 * Каждая часть — отдельная шкала на своём экране, а вместе они весят как один вопрос.
 */
data class Question(
    val text: String,
    val options: List<Option>,
    val alt: Question? = null,
    val single: List<Mix>? = null,
    val parts: List<Question>? = null,
)

/** Варианты для вопроса с выбором одного ответа — в заданном порядке. */
fun Question.singleOptions(): List<Option> = single.orEmpty().map { m -> options.first { it.mix == m } }

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

/** Откуда взят вопрос: основной список (M), набор X20 или X35 и номер в нём (с нуля). */
private data class Ref(val src: Char, val i: Int)

private fun m(vararg i: Int) = i.map { Ref('M', it) }
private fun x20(vararg i: Int) = i.map { Ref('A', it) }
private fun x35(vararg i: Int) = i.map { Ref('B', it) }

/**
 * Состав тестов: чем длиннее тест, тем подробнее вопросы.
 * 20 — общие ключевые признаки, 35 — подробнее, 50 — самые детальные. Вопросы не повторяются.
 */
private val ADULT_POOLS = mapOf(
    20 to m(0, 1, 2, 3, 6, 8, 10, 11, 12, 14, 18, 19, 20, 21, 22, 23, 24, 26, 27, 34),
    35 to m(4, 5, 7, 9, 13, 15, 16, 17, 25, 28, 30, 31, 32, 33, 38, 39) +
        x20(0, 1, 3, 4, 5, 7, 9, 10, 12, 15, 17, 19) + x35(11, 12, 15, 16, 20, 22, 23),
    50 to m(29, 35, 36, 37, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49) + x20(2, 6, 8, 11, 13, 14, 16, 18) +
        x35(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 13, 14, 17, 18, 19, 21, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34),
)

private val KID_POOLS = mapOf(
    20 to m(0, 1, 3, 5, 6, 7, 9, 10, 11, 12, 14, 15, 16, 17, 18, 19, 20, 22, 32, 33),
    35 to m(2, 4, 8, 13, 21, 23, 24, 25, 26, 27, 28, 29, 30, 31, 34) +
        x20(0, 1, 2, 3, 4, 5, 6, 7, 8, 10, 11, 13, 15, 16, 18) + x35(6, 7, 8, 9, 11),
    50 to m(35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49) + x20(9, 12, 14, 17, 19) +
        x35(0, 1, 2, 3, 4, 5, 10, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34),
)

/** Порядок вариантов для вопросов с выбором одного ответа. */
private val SIX = listOf(Mix.V, Mix.VP, Mix.P, Mix.PK, Mix.K, Mix.VK)
private val THREE = listOf(Mix.V, Mix.P, Mix.K)

/**
 * Вопросы о внешних признаках тела: тип у человека один, поэтому выбор одного варианта, а не шкала.
 * У вопросов основного списка — 6 вариантов (со смешанными), у дополнительных наборов — 3.
 */
private val ADULT_SINGLE: Map<Ref, List<Mix>> =
    mapOf(Ref('M', 0) to listOf(Mix.V, Mix.VP, Mix.P, Mix.PK, Mix.K)) + // тест с запястьем
        listOf(1, 2, 3, 4, 5, 9, 12, 13, 17, 29, 30, 31, 35, 36, 37, 38, 39).associate { Ref('M', it) to SIX } +
        listOf(0, 2, 6).associate { Ref('A', it) to THREE } +
        listOf(0, 1, 2, 3, 4, 7).associate { Ref('B', it) to THREE }

private val KID_SINGLE: Map<Ref, List<Mix>> =
    listOf(0, 4, 10, 11, 12, 13, 27, 30, 34, 35).associate { Ref('M', it) to SIX } +
        listOf(0, 1).associate { Ref('A', it) to THREE } +
        listOf(0, 1, 2, 3, 4, 5).associate { Ref('B', it) to THREE }

/** Вопросы для теста нужной длины (с ситуациями для переключателя, где они есть). */
fun questionsForLength(age: Int, length: Int, lang: Lang): List<Question> {
    val kid = age <= KID_MAX_AGE
    val ru = lang == Lang.RU
    val main = allQuestions(kid, lang)
    val situations = Situations.forSet(kid, lang)
    val a = if (kid) (if (ru) ExtraQuestions.kid20 else ExtraQuestionsEn.kid20) else (if (ru) ExtraQuestions.adult20 else ExtraQuestionsEn.adult20)
    val b = if (kid) (if (ru) ExtraQuestions.kid35 else ExtraQuestionsEn.kid35) else (if (ru) ExtraQuestions.adult35 else ExtraQuestionsEn.adult35)
    val pool = (if (kid) KID_POOLS else ADULT_POOLS).getValue(if (length in TEST_LENGTHS) length else 50)
    val single = if (kid) KID_SINGLE else ADULT_SINGLE
    return pool.map { r ->
        val order = single[r]
        val q = when (r.src) {
            'M' -> main[r.i].copy(alt = situations[r.i]?.copy(single = order))
            'A' -> a[r.i]
            else -> b[r.i]
        }
        val parts = if (r.src == 'M') SleepParts.forQuestion(kid, lang, r.i) else null
        when {
            order != null -> q.copy(single = order)
            parts != null -> q.copy(parts = parts)
            else -> q
        }
    }
}
