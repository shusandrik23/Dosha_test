package com.moyadosha.app.data

/**
 * Вопрос о сне, разбитый на части: у каждой части своя шкала «насколько похоже»
 * с утверждениями Ваты, Питты и Капхи. Части вместе весят как один вопрос.
 */
object SleepParts {
    /** Номер вопроса о сне в основном списке: у взрослых «Как вы спите?», у детей «Как ты засыпаешь?». */
    private const val ADULT_INDEX = 14
    private const val KID_INDEX = 6

    private fun part(title: String, vata: String, pitta: String, kapha: String) =
        q(title, Mix.V to vata, Mix.P to pitta, Mix.K to kapha)

    val adultRu = listOf(
        part("Засыпание",
            "Засыпаю долго, больше 30 минут: кручусь, в голове крутятся мысли",
            "Засыпаю за 10–15 минут, если не думаю о делах на завтра",
            "Засыпаю за пару минут, едва коснувшись подушки"),
        part("Чуткость сна",
            "Сплю чутко: просыпаюсь от любого шороха, 1–3 раза за ночь",
            "Могу проснуться, если жарко или в голове дела, но быстро засыпаю снова",
            "Сплю крепко всю ночь, разбудить меня трудно"),
        part("Продолжительность",
            "Сплю 5–6 часов, дольше не получается",
            "Мне хватает 6–7 часов, чтобы выспаться",
            "Мне нужно 8–9 часов и больше, иначе весь день разбитость"),
        part("Утро",
            "Просыпаюсь рано или резко, утром мало сил, бывает тревожно",
            "Встаю бодрым, сразу хочется есть и браться за дела",
            "Встать тяжело, раскачиваюсь 1–2 часа, нужен кофе или зарядка"),
    )

    val adultEn = listOf(
        part("Falling asleep",
            "It takes me over 30 minutes: I toss and turn, thoughts keep spinning",
            "I fall asleep in 10–15 minutes unless I'm thinking about tomorrow's tasks",
            "I fall asleep in a couple of minutes, as soon as my head hits the pillow"),
        part("How light your sleep is",
            "I sleep lightly: any rustle wakes me, 1–3 times a night",
            "I may wake up if it's hot or my mind is busy, but I fall back asleep quickly",
            "I sleep soundly all night, it's hard to wake me"),
        part("How long you sleep",
            "I sleep 5–6 hours and can't sleep longer",
            "6–7 hours is enough for me to feel rested",
            "I need 8–9 hours or more, otherwise I feel groggy all day"),
        part("Morning",
            "I wake up early or abruptly, have little energy in the morning, sometimes feel anxious",
            "I wake up alert, hungry and ready to get going",
            "Getting up is hard, it takes me 1–2 hours to get going, I need coffee or exercise"),
    )

    val kidRu = listOf(
        part("Засыпание",
            "Засыпаю долго, больше получаса: кручусь, болтаю, зову маму",
            "Засыпаю быстро, минут за 10, если набегался",
            "Засыпаю за пару минут, как только лёг"),
        part("Сон ночью",
            "Просыпаюсь ночью от шума или страшного сна",
            "Сплю хорошо, но просыпаюсь, если жарко",
            "Сплю крепко, меня не разбудишь"),
        part("Сколько спишь",
            "Сплю меньше других детей и всё равно бегаю",
            "Сплю столько, сколько нужно, и встаю сам",
            "Сплю дольше других детей, люблю поспать"),
    )

    val kidEn = listOf(
        part("Falling asleep",
            "It takes me over half an hour: I wriggle, chat and call for mum",
            "I fall asleep quickly, in about 10 minutes, if I've played a lot",
            "I fall asleep in a couple of minutes, as soon as I lie down"),
        part("Sleep at night",
            "I wake up at night because of noise or a scary dream",
            "I sleep well but wake up if it's hot",
            "I sleep soundly, nothing wakes me"),
        part("How long you sleep",
            "I sleep less than other kids and still run around",
            "I sleep as much as I need and get up on my own",
            "I sleep longer than other kids, I love a lie-in"),
    )

    fun forQuestion(kid: Boolean, lang: Lang, index: Int): List<Question>? = when {
        kid && index == KID_INDEX -> if (lang == Lang.RU) kidRu else kidEn
        !kid && index == ADULT_INDEX -> if (lang == Lang.RU) adultRu else adultEn
        else -> null
    }
}
