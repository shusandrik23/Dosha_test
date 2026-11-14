package com.moyadosha.app

import android.content.Context
import com.moyadosha.app.data.Dosha
import com.moyadosha.app.data.SavedResult
import org.json.JSONArray
import org.json.JSONObject

/** Хранит сохранённые результаты в памяти телефона (SharedPreferences, JSON). */
class HistoryStore(context: Context) {
    private val prefs = context.getSharedPreferences("history", Context.MODE_PRIVATE)

    fun load(): List<SavedResult> = try {
        val arr = JSONArray(prefs.getString(KEY, "[]"))
        (0 until arr.length()).map { fromJson(arr.getJSONObject(it)) }.sortedByDescending { it.timestamp }
    } catch (e: Exception) {
        emptyList()
    }

    fun save(items: List<SavedResult>) {
        val arr = JSONArray()
        items.forEach { arr.put(toJson(it)) }
        prefs.edit().putString(KEY, arr.toString()).apply()
    }

    private fun toJson(r: SavedResult) = JSONObject().apply {
        put("id", r.id)
        put("ts", r.timestamp)
        put("name", r.name)
        put("age", r.age)
        put("kid", r.kid)
        put("vikriti", r.vikriti)
        put("leading", JSONArray(r.leading.map { it.name }))
        put("percent", mapToJson(r.percent))
        put("counts", mapToJson(r.counts))
        put("norm", r.normCount)
        put("total", r.total)
    }

    private fun fromJson(o: JSONObject): SavedResult {
        val lead = o.getJSONArray("leading")
        return SavedResult(
            id = o.getLong("id"),
            timestamp = o.getLong("ts"),
            name = o.optString("name"),
            age = o.optInt("age"),
            kid = o.optBoolean("kid"),
            vikriti = o.optBoolean("vikriti"),
            leading = (0 until lead.length()).map { Dosha.valueOf(lead.getString(it)) },
            percent = jsonToMap(o.optJSONObject("percent")),
            counts = jsonToMap(o.optJSONObject("counts")),
            normCount = o.optInt("norm"),
            total = o.optInt("total"),
        )
    }

    private fun mapToJson(m: Map<Dosha, Int>) = JSONObject().apply { m.forEach { (k, v) -> put(k.name, v) } }

    private fun jsonToMap(o: JSONObject?): Map<Dosha, Int> =
        if (o == null || o.length() == 0) emptyMap() else Dosha.entries.associateWith { o.optInt(it.name) }

    private companion object {
        const val KEY = "items"
    }
}
