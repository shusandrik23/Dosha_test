package com.moyadosha.app

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.moyadosha.app.data.Dosha
import com.moyadosha.app.data.DoshaResult
import com.moyadosha.app.data.KID_MAX_AGE
import com.moyadosha.app.data.Lang
import com.moyadosha.app.data.Recommendations
import com.moyadosha.app.data.SavedResult
import com.moyadosha.app.data.Scoring
import com.moyadosha.app.data.UiStrings
import com.moyadosha.app.data.VikritiQuestions
import com.moyadosha.app.data.VikritiResult
import com.moyadosha.app.data.VikritiScoring
import com.moyadosha.app.data.VikritiTexts
import com.moyadosha.app.data.Mix
import com.moyadosha.app.data.pureStatements
import com.moyadosha.app.data.singleOptions
import com.moyadosha.app.data.questionsForLength
import kotlin.random.Random

enum class Screen { Welcome, Quiz, Result, History }

/** PRAKRITI — врождённая доша, VIKRITI — баланс сейчас. */
enum class TestMode { PRAKRITI, VIKRITI }

/** Вопрос для экрана: текст и варианты, без привязки к типу теста. */
data class QuizItem(
    val text: String,
    val options: List<String>,
    val alt: QuizItem? = null,
    /** Выбор одного варианта вместо шкалы. */
    val single: Boolean = false,
    /** Номер вопроса для счётчика: части одного вопроса (например, сна) идут под одним номером. */
    val group: Int = 0,
    /** Часть вопроса: название, номер и сколько всего частей (null — обычный вопрос). */
    val part: String? = null,
    val partNo: Int = 0,
    val partCount: Int = 0,
)

class QuizViewModel(app: Application) : AndroidViewModel(app) {
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
    /** Пракрити: доша каждого из трёх утверждений (в показанном порядке) у вопроса и у ситуации. */
    private var prakritiDoshas: List<List<Dosha>> = emptyList()
    private var altDoshas: List<List<Dosha>?> = emptyList()
    /** Пракрити: варианты вопросов с выбором одного ответа (null — шкала). */
    private var singleMixes: List<List<Mix>?> = emptyList()
    /** Пракрити: вес экрана в итоге — обычный вопрос 12, часть вопроса 12 / число частей. */
    private var weights: List<Int> = emptyList()

    /** Сколько вопросов в тесте (части одного вопроса считаются одним). */
    var groupCount by mutableIntStateOf(0)
        private set

    /** Пракрити: оценки трёх утверждений текущего варианта вопроса (0/1/2 или null). */
    val ratings = mutableStateListOf<List<Int?>>()
    private var vikritiDoshas: List<List<Dosha?>> = emptyList()

    /** Индекс выбранного варианта для каждого вопроса (или null). */
    val answers = mutableStateListOf<Int?>()

    var index by mutableIntStateOf(0)
        private set

    var result: DoshaResult? by mutableStateOf(null)
        private set
    var vikritiResult: VikritiResult? by mutableStateOf(null)
        private set

    /** Чей результат на экране: только что пройденный тест или открытый из истории. */
    var resultName by mutableStateOf("")
        private set
    var resultAge by mutableIntStateOf(0)
        private set
    var resultKid by mutableStateOf(false)
        private set
    var resultTimestamp by mutableStateOf(0L)
        private set
    /** id сохранённой записи для текущего результата (null — ещё не сохранён). */
    var savedId: Long? by mutableStateOf(null)
        private set
    /** Результат открыт из истории — «назад» ведёт в историю. */
    var fromHistory by mutableStateOf(false)
        private set

    private val store = HistoryStore(app)
    val history = mutableStateListOf<SavedResult>().apply { addAll(store.load()) }

    fun start(newMode: TestMode = mode) {
        val a = age ?: return
        mode = newMode
        val rnd = Random(System.nanoTime())
        if (newMode == TestMode.PRAKRITI) {
            val qs = questionsForLength(a, length, lang)
            val flat = mutableListOf<QuizItem>()
            val doshas = mutableListOf<List<Dosha>>()
            val altD = mutableListOf<List<Dosha>?>()
            val singles = mutableListOf<List<Mix>?>()
            val w = mutableListOf<Int>()
            qs.forEachIndexed { g, q ->
                val parts = q.parts
                if (parts != null) {
                    // вопрос из частей: каждая часть — своя шкала на отдельном экране
                    parts.forEachIndexed { p, part ->
                        val st = part.pureStatements().shuffled(rnd)
                        flat += QuizItem(q.text, st.map { it.second }, group = g, part = part.text, partNo = p + 1, partCount = parts.size)
                        doshas += st.map { it.first }
                        altD += null
                        singles += null
                        w += WEIGHT / parts.size
                    }
                    return@forEachIndexed
                }
                val main = q.pureStatements().shuffled(rnd)
                val alt = q.alt?.pureStatements()?.shuffled(rnd)
                flat += if (q.single != null) {
                    QuizItem(
                        q.text, q.singleOptions().map { it.text },
                        q.alt?.let { a2 -> QuizItem(a2.text, a2.singleOptions().map { it.text }, single = true) },
                        single = true, group = g,
                    )
                } else {
                    QuizItem(q.text, main.map { it.second }, q.alt?.let { a2 -> QuizItem(a2.text, alt!!.map { it.second }) }, group = g)
                }
                doshas += main.map { it.first }
                altD += alt?.map { it.first }
                singles += q.single
                w += WEIGHT
            }
            items = flat
            prakritiDoshas = doshas
            altDoshas = altD
            singleMixes = singles
            weights = w
            groupCount = qs.size
        } else {
            val qs = VikritiQuestions.forAge(a, lang).map { it.copy(options = it.options.shuffled(rnd)) }
            items = qs.mapIndexed { i, q -> QuizItem(q.text, q.options.map { it.text }, group = i) }
            vikritiDoshas = qs.map { q -> q.options.map { it.dosha } }
            groupCount = qs.size
        }
        answers.clear()
        repeat(items.size) { answers.add(null) }
        useAlt.clear()
        repeat(items.size) { useAlt.add(false) }
        ratings.clear()
        repeat(items.size) { ratings.add(listOf(null, null, null)) }
        index = 0
        result = null
        vikritiResult = null
        screen = Screen.Quiz
    }

    /** true — вместо общего вопроса показана ситуация. */
    val useAlt = mutableStateListOf<Boolean>()

    /** Переключатель над вопросом: общий вопрос ↔ ситуация. */
    fun toggleAlt() {
        if (items.getOrNull(index)?.alt == null) return
        useAlt[index] = !useAlt[index]
        answers[index] = null
        ratings[index] = listOf(null, null, null)
    }

    fun rate(statement: Int, value: Int) {
        ratings[index] = ratings[index].toMutableList().also { it[statement] = value }
    }

    /** Можно идти дальше: в Викрити выбран ответ, в Пракрити оценены все три утверждения. */
    val currentAnswered: Boolean
        get() = if (mode == TestMode.PRAKRITI && items.getOrNull(index)?.single != true)
            ratings.getOrNull(index)?.all { it != null } == true
        else answers.getOrNull(index) != null

    fun choose(optionIndex: Int) {
        answers[index] = optionIndex
    }

    fun next() {
        if (!currentAnswered) return
        if (index < items.lastIndex) {
            index++
            return
        }
        if (mode == TestMode.PRAKRITI) {
            result = Scoring.scoreRatings(ratings.mapIndexed { i, r ->
                val single = singleMixes[i]
                if (single != null) {
                    // выбранный вариант: «чистый» — 2 балла своей доше, смешанный — по 1 двум
                    val mix = single[answers[i]!!]
                    Dosha.entries.associateWith { mix.points(it) * weights[i] }
                } else {
                    val doshas = if (useAlt[i]) altDoshas[i]!! else prakritiDoshas[i]
                    doshas.zip(r).associate { (d, v) -> d to (v ?: 0) * weights[i] }
                }
            })
        } else {
            vikritiResult = VikritiScoring.score(vikritiDoshas.mapIndexed { i, opts -> opts[answers[i]!!] })
        }
        resultName = name.trim()
        resultAge = age ?: 0
        resultKid = isKid
        resultTimestamp = System.currentTimeMillis()
        savedId = null
        fromHistory = false
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

    fun saveCurrent() {
        if (savedId != null) return
        val item = if (mode == TestMode.VIKRITI) {
            SavedResult.of(vikritiResult ?: return, resultName, resultAge, resultKid, resultTimestamp)
        } else {
            SavedResult.of(result ?: return, resultName, resultAge, resultKid, resultTimestamp)
        }
        history.add(0, item)
        store.save(history)
        savedId = item.id
    }

    fun openHistory() {
        screen = Screen.History
    }

    fun openSaved(item: SavedResult) {
        mode = if (item.vikriti) TestMode.VIKRITI else TestMode.PRAKRITI
        result = if (item.vikriti) null else item.toDoshaResult()
        vikritiResult = if (item.vikriti) item.toVikritiResult() else null
        resultName = item.name
        resultAge = item.age
        resultKid = item.kid
        resultTimestamp = item.timestamp
        savedId = item.id
        fromHistory = true
        // чтобы с экрана результата можно было сразу пройти второй тест за этого человека
        if (name.isBlank()) name = item.name
        if (age == null) ageText = item.age.toString()
        screen = Screen.Result
    }

    fun deleteSaved(item: SavedResult) {
        history.removeAll { it.id == item.id }
        store.save(history)
        if (savedId == item.id) savedId = null
    }

    /** «Назад» с экрана результата. */
    fun leaveResult() {
        screen = if (fromHistory) Screen.History else Screen.Welcome
    }

    private companion object {
        /** Вес обычного вопроса; делится на 3 и на 4, чтобы части вопроса давали целые баллы. */
        const val WEIGHT = 12
    }
}
