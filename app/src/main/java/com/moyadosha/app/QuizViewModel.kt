package com.moyadosha.app

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.moyadosha.app.data.Dosha
import com.moyadosha.app.data.DoshaResult
import com.moyadosha.app.data.KID_MAX_AGE
import com.moyadosha.app.data.Lang
import com.moyadosha.app.data.Mix
import com.moyadosha.app.data.Recommendations
import com.moyadosha.app.data.Scoring
import com.moyadosha.app.data.UiStrings
import com.moyadosha.app.data.VikritiQuestions
import com.moyadosha.app.data.VikritiResult
import com.moyadosha.app.data.VikritiScoring
import com.moyadosha.app.data.VikritiTexts
import com.moyadosha.app.data.questionsFor
import kotlin.random.Random

enum class Screen { Welcome, Quiz, Result }

/** PRAKRITI — врождённая доша, VIKRITI — баланс сейчас. */
enum class TestMode { PRAKRITI, VIKRITI }

/** Вопрос для экрана: текст и варианты, без привязки к типу теста. */
data class QuizItem(val text: String, val options: List<String>)

class QuizViewModel : ViewModel() {
    /** Язык выбирается по языку системы: русский или английский. */
    val lang: Lang = Lang.system()
    val strings = UiStrings(lang)
    val recommendations = Recommendations(lang)
    val vtexts = VikritiTexts(lang)

    var screen by mutableStateOf(Screen.Welcome)
        private set

    var name by mutableStateOf("")
    var ageText by mutableStateOf("")
    var length by mutableIntStateOf(35)
    var mode by mutableStateOf(TestMode.PRAKRITI)

    val age: Int? get() = ageText.trim().toIntOrNull()?.takeIf { it in 1..110 }
    val isKid: Boolean get() = (age ?: 99) <= KID_MAX_AGE
    val canStart: Boolean get() = name.isNotBlank() && age != null

    /** Вопросы с перемешанными вариантами ответа, чтобы не было угадываемого порядка. */
    var items: List<QuizItem> = emptyList()
        private set
    private var prakritiMixes: List<List<Mix>> = emptyList()
    private var vikritiDoshas: List<List<Dosha?>> = emptyList()

    /** Индекс выбранного варианта для каждого вопроса (или null). */
    val answers = mutableStateListOf<Int?>()

    var index by mutableIntStateOf(0)
        private set

    var result: DoshaResult? by mutableStateOf(null)
        private set
    var vikritiResult: VikritiResult? by mutableStateOf(null)
        private set

    fun start(newMode: TestMode = mode) {
        val a = age ?: return
        mode = newMode
        val rnd = Random(System.nanoTime())
        if (newMode == TestMode.PRAKRITI) {
            val qs = questionsFor(a, length, lang).map { it.copy(options = it.options.shuffled(rnd)) }
            items = qs.map { q -> QuizItem(q.text, q.options.map { it.text }) }
            prakritiMixes = qs.map { q -> q.options.map { it.mix } }
        } else {
            val qs = VikritiQuestions.forAge(a, lang).map { it.copy(options = it.options.shuffled(rnd)) }
            items = qs.map { q -> QuizItem(q.text, q.options.map { it.text }) }
            vikritiDoshas = qs.map { q -> q.options.map { it.dosha } }
        }
        answers.clear()
        repeat(items.size) { answers.add(null) }
        index = 0
        result = null
        vikritiResult = null
        screen = Screen.Quiz
    }

    fun choose(optionIndex: Int) {
        answers[index] = optionIndex
    }

    fun next() {
        if (answers[index] == null) return
        if (index < items.lastIndex) {
            index++
            return
        }
        if (mode == TestMode.PRAKRITI) {
            result = Scoring.score(prakritiMixes.mapIndexed { i, opts -> opts[answers[i]!!] })
        } else {
            vikritiResult = VikritiScoring.score(vikritiDoshas.mapIndexed { i, opts -> opts[answers[i]!!] })
        }
        screen = Screen.Result
    }

    /** @return false, если назад идти некуда (первый вопрос). */
    fun back(): Boolean {
        if (index == 0) return false
        index--
        return true
    }

    fun toWelcome() {
        screen = Screen.Welcome
    }
}
