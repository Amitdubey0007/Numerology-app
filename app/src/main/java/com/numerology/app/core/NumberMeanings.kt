package com.numerology.app.core

/**
 * Content model + static data for what each number "means".
 * Used to populate result screens for Life Path, Destiny, Daily, etc.
 */
data class NumberMeaning(
    val number: Int,
    val title: String,
    val summary: String,
    val positiveTraits: List<String>,
    val challenges: List<String>,
    val careerFit: String,
    val relationshipNote: String,
    val luckyColor: String,
    val luckyDay: String
)

object NumberMeanings {

    private val data: Map<Int, NumberMeaning> = listOf(
        NumberMeaning(
            number = 1,
            title = "The Leader",
            summary = "Independent, driven, and a natural trailblazer.",
            positiveTraits = listOf("Leadership", "Independence", "Confidence", "Innovation", "Determination"),
            challenges = listOf("Stubbornness", "Impatience", "Can be domineering", "Reluctant to ask for help"),
            careerFit = "Entrepreneur, manager, founder, or any role with autonomy and decision-making power.",
            relationshipNote = "Needs a partner who respects independence rather than competing for control.",
            luckyColor = "Gold / Orange",
            luckyDay = "Sunday"
        ),
        NumberMeaning(
            number = 2,
            title = "The Peacemaker",
            summary = "Diplomatic, cooperative, and deeply intuitive.",
            positiveTraits = listOf("Diplomacy", "Patience", "Sensitivity", "Teamwork", "Loyalty"),
            challenges = listOf("Over-sensitivity", "Indecisiveness", "Fear of conflict", "Can be overly dependent"),
            careerFit = "Counselor, mediator, HR, diplomat, or supportive/partnership-based roles.",
            relationshipNote = "Thrives in close partnerships but should guard against losing their own voice.",
            luckyColor = "Silver / White",
            luckyDay = "Monday"
        ),
        NumberMeaning(
            number = 3,
            title = "The Communicator",
            summary = "Expressive, creative, and naturally social.",
            positiveTraits = listOf("Creativity", "Charisma", "Optimism", "Expression", "Humor"),
            challenges = listOf("Scattered focus", "Superficiality", "Mood swings", "Avoids hard truths"),
            careerFit = "Artist, writer, performer, marketer, or any expressive/creative field.",
            relationshipNote = "Brings fun and spontaneity, but needs a partner who provides some grounding.",
            luckyColor = "Yellow",
            luckyDay = "Thursday"
        ),
        NumberMeaning(
            number = 4,
            title = "The Builder",
            summary = "Practical, disciplined, and reliably hardworking.",
            positiveTraits = listOf("Discipline", "Reliability", "Organization", "Honesty", "Persistence"),
            challenges = listOf("Rigidity", "Overly cautious", "Resistant to change", "Can be workaholic"),
            careerFit = "Engineer, accountant, project manager, or structured/technical fields.",
            relationshipNote = "Offers stability and loyalty; needs a partner who values consistency over spontaneity.",
            luckyColor = "Blue / Green",
            luckyDay = "Saturday"
        ),
        NumberMeaning(
            number = 5,
            title = "The Free Spirit",
            summary = "Adventurous, adaptable, and craves change.",
            positiveTraits = listOf("Adaptability", "Curiosity", "Freedom-loving", "Versatility", "Quick thinking"),
            challenges = listOf("Restlessness", "Inconsistency", "Impulsiveness", "Commitment issues"),
            careerFit = "Travel, sales, journalism, or any dynamic/fast-changing career.",
            relationshipNote = "Needs a partner who won't feel threatened by their need for freedom and variety.",
            luckyColor = "Turquoise",
            luckyDay = "Wednesday"
        ),
        NumberMeaning(
            number = 6,
            title = "The Caregiver",
            summary = "Nurturing, responsible, and deeply family-oriented.",
            positiveTraits = listOf("Compassion", "Responsibility", "Reliability", "Generosity", "Harmony-seeking"),
            challenges = listOf("Self-sacrificing", "Overly controlling", "Worries excessively", "Hard to say no"),
            careerFit = "Healthcare, teaching, counseling, or family/community-oriented work.",
            relationshipNote = "Devoted and protective; should be careful not to smother a partner with over-care.",
            luckyColor = "Pink / Blue",
            luckyDay = "Friday"
        ),
        NumberMeaning(
            number = 7,
            title = "The Seeker",
            summary = "Analytical, introspective, and drawn to deeper truths.",
            positiveTraits = listOf("Analytical mind", "Wisdom", "Intuition", "Independence", "Depth"),
            challenges = listOf("Isolation", "Overthinking", "Skepticism", "Difficulty opening up"),
            careerFit = "Research, science, spirituality, analysis, or specialist/expert roles.",
            relationshipNote = "Needs alone time to recharge; a partner should respect their need for solitude.",
            luckyColor = "Violet / Grey",
            luckyDay = "Monday"
        ),
        NumberMeaning(
            number = 8,
            title = "The Powerhouse",
            summary = "Ambitious, authoritative, and business-minded.",
            positiveTraits = listOf("Ambition", "Confidence", "Financial acumen", "Leadership", "Efficiency"),
            challenges = listOf("Workaholism", "Materialistic tendencies", "Impatience", "Can be controlling"),
            careerFit = "Business owner, executive, finance, real estate, or high-stakes leadership roles.",
            relationshipNote = "Provides security and ambition to a relationship; must remember to make time beyond work.",
            luckyColor = "Black / Dark Red",
            luckyDay = "Saturday"
        ),
        NumberMeaning(
            number = 9,
            title = "The Humanitarian",
            summary = "Compassionate, idealistic, and globally minded.",
            positiveTraits = listOf("Compassion", "Generosity", "Idealism", "Wisdom", "Creativity"),
            challenges = listOf("Overly idealistic", "Emotional volatility", "Martyr complex", "Difficulty letting go"),
            careerFit = "Non-profit work, activism, healing professions, arts, or global/humanitarian causes.",
            relationshipNote = "Loves deeply and selflessly; needs a partner who appreciates their idealism without exploiting it.",
            luckyColor = "Red / Gold",
            luckyDay = "Tuesday"
        ),
        NumberMeaning(
            number = 11,
            title = "The Intuitive (Master Number)",
            summary = "Highly intuitive, inspirational, and spiritually attuned.",
            positiveTraits = listOf("Intuition", "Inspiration", "Idealism", "Sensitivity", "Vision"),
            challenges = listOf("Anxiety", "Nervous energy", "Self-doubt", "Overwhelmed by own sensitivity"),
            careerFit = "Counseling, spiritual teaching, art, or inspirational/visionary roles.",
            relationshipNote = "Deeply empathetic; needs a calm, grounded partner to balance their intensity.",
            luckyColor = "White / Silver",
            luckyDay = "Monday"
        ),
        NumberMeaning(
            number = 22,
            title = "The Master Builder (Master Number)",
            summary = "Visionary and practical — able to turn big dreams into reality.",
            positiveTraits = listOf("Vision", "Practicality", "Leadership", "Discipline", "Large-scale thinking"),
            challenges = listOf("Immense self-pressure", "Perfectionism", "Burnout risk", "Difficulty delegating"),
            careerFit = "Large-scale entrepreneurship, architecture, city/organizational planning.",
            relationshipNote = "Ambitious and devoted; needs a partner who supports big goals without feeling sidelined.",
            luckyColor = "Earth tones",
            luckyDay = "Saturday"
        ),
        NumberMeaning(
            number = 33,
            title = "The Master Teacher (Master Number)",
            summary = "Selfless, nurturing on a grand scale, and deeply compassionate.",
            positiveTraits = listOf("Compassion", "Healing energy", "Selflessness", "Wisdom", "Devotion"),
            challenges = listOf("Self-neglect", "Over-responsibility for others", "Emotional exhaustion"),
            careerFit = "Teaching, healing professions, humanitarian leadership.",
            relationshipNote = "Gives generously in relationships; must learn to receive care as well as give it.",
            luckyColor = "Pastel shades",
            luckyDay = "Friday"
        )
    ).associateBy { it.number }

    /**
     * Returns the meaning for a number. Falls back to reducing the number
     * to 1-9 if an unsupported value (outside 1-9, 11, 22, 33) is passed.
     */
    fun get(number: Int): NumberMeaning {
        return data[number] ?: data.getValue(reduceToSingleDigit(number, keepMasterNumbers = false).coerceIn(1, 9))
    }
}
