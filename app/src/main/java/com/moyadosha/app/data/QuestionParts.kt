package com.moyadosha.app.data

/**
 * Вопросы, разбитые на части (сон, волосы): у каждой части своя шкала «насколько похоже»
 * с утверждениями Ваты, Питты и Капхи. Части вместе весят как один вопрос.
 */
object QuestionParts {
    /** Номера вопросов в основном списке. */
    private const val ADULT_SLEEP = 14
    private const val ADULT_HAIR = 3
    private const val KID_SLEEP = 6
    private const val KID_HAIR = 12

    private fun part(title: String, vata: String, pitta: String, kapha: String) =
        q(title, Mix.V to vata, Mix.P to pitta, Mix.K to kapha)

    val sleepAdultRu = listOf(
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

    val sleepAdultEn = listOf(
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

    val sleepKidRu = listOf(
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

    val sleepKidEn = listOf(
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

    val hairAdultRu = listOf(
        part("Толщина и густота",
            "Тонкие и редкие, хвост получается тонким",
            "Тонкие и мягкие, средней густоты",
            "Густые и толстые, хвост тяжёлый"),
        part("Сухость и жирность",
            "Сухие и тусклые, кончики секутся",
            "Быстро жирнеют у корней — уже на 1–2 день после мытья",
            "Блестящие, остаются свежими 3–4 дня"),
        part("Форма",
            "Вьются, пушатся, путаются",
            "Прямые и мягкие, лежат гладко",
            "Волнистые, тяжёлые, хорошо держат укладку"),
        part("Цвет, седина и выпадение",
            "Ломаются и выпадают при стрессе, недосыпе или в холод",
            "Светлые или рыжеватые; рано седеют (до 30–35 лет) или редеют",
            "Тёмные, насыщенного цвета, седеют и редеют поздно"),
    )

    val hairAdultEn = listOf(
        part("Thickness and density",
            "Thin and sparse, my ponytail is thin",
            "Fine and soft, medium density",
            "Thick and dense, my ponytail is heavy"),
        part("Dry or oily",
            "Dry and dull, split ends",
            "Get oily at the roots quickly — by day 1–2 after washing",
            "Shiny, stay fresh for 3–4 days"),
        part("Texture",
            "Curly or frizzy, tangle easily",
            "Straight and soft, lie smooth",
            "Wavy and heavy, hold a style well"),
        part("Colour, greying and hair loss",
            "Break and fall out with stress, lack of sleep or cold",
            "Light or reddish; grey early (before 30–35) or thin out",
            "Dark, rich colour, grey and thin out late"),
    )

    val hairKidRu = listOf(
        part("Густота",
            "Тонкие и редкие",
            "Мягкие, средней густоты",
            "Очень густые и толстые"),
        part("Форма",
            "Пушистые, путаются и торчат",
            "Прямые и мягкие",
            "Волнистые и тяжёлые"),
        part("Цвет и блеск",
            "Тусклые, сухие на кончиках",
            "Светлые или рыжеватые",
            "Тёмные и блестящие"),
    )

    val hairKidEn = listOf(
        part("Thickness",
            "Thin and sparse",
            "Soft, medium thickness",
            "Very thick and dense"),
        part("Texture",
            "Fluffy, tangle and stick out",
            "Straight and soft",
            "Wavy and heavy"),
        part("Colour and shine",
            "Dull, dry at the ends",
            "Light or reddish",
            "Dark and shiny"),
    )

    fun forQuestion(kid: Boolean, lang: Lang, index: Int): List<Question>? {
        val ru = lang == Lang.RU
        return when {
            kid && index == KID_SLEEP -> if (ru) sleepKidRu else sleepKidEn
            kid && index == KID_HAIR -> if (ru) hairKidRu else hairKidEn
            !kid && index == ADULT_SLEEP -> if (ru) sleepAdultRu else sleepAdultEn
            !kid && index == ADULT_HAIR -> if (ru) hairAdultRu else hairAdultEn
            else -> null
        }
    }
}
