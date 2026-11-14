package com.moyadosha.app.data

/** Сохранённый результат любого из двух тестов. Хранится только на устройстве. */
data class SavedResult(
    val id: Long,
    /** Время прохождения, миллисекунды с 1970 года. */
    val timestamp: Long,
    val name: String,
    val age: Int,
    val kid: Boolean,
    /** true — «Мой баланс сейчас» (Викрити), false — «Моя природа» (Пракрити). */
    val vikriti: Boolean,
    val leading: List<Dosha>,
    /** Пракрити: проценты дош. */
    val percent: Map<Dosha, Int> = emptyMap(),
    /** Викрити: число ответов по дошам, ответы «Норма» и всего вопросов. */
    val counts: Map<Dosha, Int> = emptyMap(),
    val normCount: Int = 0,
    val total: Int = 0,
) {
    fun toDoshaResult() = DoshaResult(percent, leading)
    fun toVikritiResult() = VikritiResult(normCount, counts, total, leading)

    companion object {
        fun of(r: DoshaResult, name: String, age: Int, kid: Boolean, now: Long) = SavedResult(
            id = now, timestamp = now, name = name.trim(), age = age, kid = kid,
            vikriti = false, leading = r.leading, percent = r.percent,
        )

        fun of(r: VikritiResult, name: String, age: Int, kid: Boolean, now: Long) = SavedResult(
            id = now, timestamp = now, name = name.trim(), age = age, kid = kid,
            vikriti = true, leading = r.leading, counts = r.counts, normCount = r.normCount, total = r.total,
        )
    }
}
