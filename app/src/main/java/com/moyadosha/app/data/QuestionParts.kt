package com.moyadosha.app.data

/**
 * Вопросы, разбитые на части (сон, волосы, кожа, пищеварение): у каждой части своя шкала «насколько похоже»
 * с утверждениями Ваты, Питты и Капхи. Части вместе весят как один вопрос.
 */
object QuestionParts {
    /** Номера вопросов в основном списке. */
    private const val ADULT_SLEEP = 14
    private const val ADULT_HAIR = 3
    private const val KID_SLEEP = 6
    private const val KID_HAIR = 12
    private const val ADULT_SKIN = 2
    private const val ADULT_DIGESTION = 8
    private const val KID_SKIN = 11
    private const val KID_TUMMY = 5

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

    val skinAdultRu = listOf(
        part("Сухость и жирность",
            "Сухая, часто стягивает и шелушится",
            "Нормальная, но в жару быстро становится влажной и блестит",
            "Плотная, жирноватая, почти никогда не сохнет"),
        part("На ощупь",
            "Прохладная, особенно руки и ноги",
            "Тёплая, бывает горячей",
            "Прохладная и чуть влажная"),
        part("Толщина и цвет",
            "Тонкая, вены просвечивают, рано появляются морщинки",
            "Мягкая, розоватая, много родинок или веснушек",
            "Толстая, гладкая, ровного цвета, морщин мало"),
        part("Что бывает с кожей",
            "Трескается и шелушится в холод и на ветру",
            "Легко краснеет и обгорает, бывают сыпь, воспаления, прыщи",
            "Расширенные поры, чёрные точки, бывает отёчность"),
    )

    val skinAdultEn = listOf(
        part("Dry or oily",
            "Dry, often feels tight and flaky",
            "Normal, but gets damp and shiny quickly in the heat",
            "Thick, slightly oily, almost never dry"),
        part("To the touch",
            "Cool, especially hands and feet",
            "Warm, sometimes hot",
            "Cool and slightly moist"),
        part("Thickness and colour",
            "Thin, veins show through, fine lines appear early",
            "Soft, pinkish, lots of moles or freckles",
            "Thick, smooth, even-toned, few wrinkles"),
        part("What happens to your skin",
            "Cracks and flakes in cold and wind",
            "Reddens and burns easily, rashes, inflammation, spots",
            "Enlarged pores, blackheads, sometimes puffiness"),
    )

    val digestionAdultRu = listOf(
        part("Стул",
            "Нерегулярный, бывает раз в 2–3 дня, склонность к запорам",
            "2–3 раза в день, бывает жидкий",
            "Раз в день, регулярно, плотный"),
        part("После еды",
            "Вздутие, газы, урчание",
            "Изжога, жжение, кислый привкус",
            "Тяжесть, сонливость, тянет полежать"),
        part("Скорость пищеварения",
            "То быстро, то медленно — зависит от нервов и еды",
            "Быстро: через 2–3 часа снова голоден",
            "Медленно: сытость держится 4–5 часов и дольше"),
    )

    val digestionAdultEn = listOf(
        part("Bowel movements",
            "Irregular, sometimes once in 2–3 days, prone to constipation",
            "2–3 times a day, sometimes loose",
            "Once a day, regular, firm"),
        part("After eating",
            "Bloating, gas, rumbling",
            "Heartburn, burning, sour taste",
            "Heaviness, sleepiness, want to lie down"),
        part("Speed of digestion",
            "Sometimes fast, sometimes slow — depends on nerves and food",
            "Fast: hungry again after 2–3 hours",
            "Slow: I stay full for 4–5 hours or longer"),
    )

    val skinKidRu = listOf(
        part("Сухость",
            "Сухая, шершавая",
            "Тёплая, бывает влажной",
            "Мягкая, гладкая, чуть влажная"),
        part("Цвет",
            "Тусклая, бывают синячки под глазами",
            "Розовая, быстро краснеет, бывают веснушки",
            "Светлая и ровная"),
        part("Что бывает с кожей",
            "Трескается на холоде и ветру",
            "Бывает сыпь или раздражение",
            "Утром бывает припухшее личико"),
    )

    val skinKidEn = listOf(
        part("Dryness",
            "Dry and rough",
            "Warm, sometimes damp",
            "Soft, smooth, slightly moist"),
        part("Colour",
            "Dull, sometimes dark circles under the eyes",
            "Pink, reddens quickly, sometimes freckles",
            "Fair and even"),
        part("What happens to your skin",
            "Cracks in cold and wind",
            "Sometimes a rash or irritation",
            "Sometimes a puffy face in the morning"),
    )

    val tummyKidRu = listOf(
        part("Туалет",
            "«По-большому» хожу не каждый день",
            "Хожу 2 раза в день и чаще, бывает жидкий",
            "Хожу раз в день, без проблем"),
        part("После еды",
            "Часто пучит и урчит",
            "Расстраивается от острого или жирного, бывает «жжёт»",
            "Тяжело, хочется полежать"),
        part("Когда болит животик",
            "Когда я волнуюсь",
            "Если долго не кушать",
            "Почти никогда не болит"),
    )

    val tummyKidEn = listOf(
        part("Going to the toilet",
            "I don't poo every day",
            "I poo twice a day or more, sometimes it's runny",
            "Once a day, no problems"),
        part("After eating",
            "Often bloated and rumbly",
            "Gets upset from spicy or greasy food, sometimes burns",
            "Feels heavy, I want to lie down"),
        part("When your tummy hurts",
            "When I'm nervous",
            "If I haven't eaten for a long time",
            "It almost never hurts"),
    )

    fun forQuestion(kid: Boolean, lang: Lang, index: Int): List<Question>? {
        val ru = lang == Lang.RU
        return when {
            kid && index == KID_SLEEP -> if (ru) sleepKidRu else sleepKidEn
            kid && index == KID_HAIR -> if (ru) hairKidRu else hairKidEn
            kid && index == KID_SKIN -> if (ru) skinKidRu else skinKidEn
            kid && index == KID_TUMMY -> if (ru) tummyKidRu else tummyKidEn
            !kid && index == ADULT_SLEEP -> if (ru) sleepAdultRu else sleepAdultEn
            !kid && index == ADULT_HAIR -> if (ru) hairAdultRu else hairAdultEn
            !kid && index == ADULT_SKIN -> if (ru) skinAdultRu else skinAdultEn
            !kid && index == ADULT_DIGESTION -> if (ru) digestionAdultRu else digestionAdultEn
            else -> null
        }
    }
}
