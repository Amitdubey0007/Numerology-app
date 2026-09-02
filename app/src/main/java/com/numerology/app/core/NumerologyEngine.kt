package com.numerology.app.core

import java.time.LocalDate

/**
 * Core numerology calculation engine.
 * All functions are pure (no side effects) so they're easy to unit test
 * and can be called directly from Compose UI state / ViewModels.
 */

// Master numbers that are NOT reduced further in most numerology systems.
private val MASTER_NUMBERS = setOf(11, 22, 33)

/**
 * Reduces a number to a single digit (1-9), UNLESS it lands on a master
 * number (11, 22, 33) at any point during reduction, in which case the
 * master number is preserved and returned as-is.
 */
fun reduceToSingleDigit(input: Int, keepMasterNumbers: Boolean = true): Int {
    var n = input
    while (n > 9) {
        if (keepMasterNumbers && n in MASTER_NUMBERS) return n
        n = n.toString().sumOf { it.digitToInt() }
    }
    return n
}

/**
 * Sums all digits of a string of digits (ignoring non-digit characters).
 */
private fun sumDigits(s: String): Int =
    s.filter { it.isDigit() }.sumOf { it.digitToInt() }

// ---------------------------------------------------------------------
// LIFE PATH NUMBER — derived from full birth date
// ---------------------------------------------------------------------

data class LifePathResult(
    val number: Int,
    val isMasterNumber: Boolean,
    val birthDate: LocalDate
)

/**
 * Calculates the Life Path Number from a birth date.
 * Method: sum every digit of DD, MM, and YYYY together, then reduce.
 */
fun calculateLifePath(birthDate: LocalDate): LifePathResult {
    val day = birthDate.dayOfMonth.toString()
    val month = birthDate.monthValue.toString()
    val year = birthDate.year.toString()

    val total = sumDigits(day) + sumDigits(month) + sumDigits(year)
    val result = reduceToSingleDigit(total)

    return LifePathResult(
        number = result,
        isMasterNumber = result in MASTER_NUMBERS,
        birthDate = birthDate
    )
}

// ---------------------------------------------------------------------
// DESTINY / NAME NUMBER — derived from full name (Pythagorean system)
// ---------------------------------------------------------------------

// Pythagorean letter-to-number mapping (1-9 repeating across the alphabet).
private val LETTER_VALUES: Map<Char, Int> = mapOf(
    'A' to 1, 'B' to 2, 'C' to 3, 'D' to 4, 'E' to 5, 'F' to 6, 'G' to 7, 'H' to 8, 'I' to 9,
    'J' to 1, 'K' to 2, 'L' to 3, 'M' to 4, 'N' to 5, 'O' to 6, 'P' to 7, 'Q' to 8, 'R' to 9,
    'S' to 1, 'T' to 2, 'U' to 3, 'V' to 4, 'W' to 5, 'X' to 6, 'Y' to 7, 'Z' to 8
)

private val VOWELS = setOf('A', 'E', 'I', 'O', 'U')

data class NameNumberResult(
    val destinyNumber: Int,       // all letters (full name expression)
    val soulUrgeNumber: Int,      // vowels only (inner desire)
    val personalityNumber: Int,   // consonants only (outer impression)
    val isMasterNumber: Boolean
)

/**
 * Calculates Destiny (Expression), Soul Urge, and Personality numbers
 * from a full name using the Pythagorean system.
 */
fun calculateNameNumbers(fullName: String): NameNumberResult {
    val cleanName = fullName.uppercase().filter { it.isLetter() }

    var allSum = 0
    var vowelSum = 0
    var consonantSum = 0

    for (ch in cleanName) {
        val value = LETTER_VALUES[ch] ?: continue
        allSum += value
        if (ch in VOWELS) vowelSum += value else consonantSum += value
    }

    val destiny = reduceToSingleDigit(allSum)

    return NameNumberResult(
        destinyNumber = destiny,
        soulUrgeNumber = reduceToSingleDigit(vowelSum),
        personalityNumber = reduceToSingleDigit(consonantSum),
        isMasterNumber = destiny in MASTER_NUMBERS
    )
}

// ---------------------------------------------------------------------
// DAILY NUMBER — combines today's date with the user's Life Path
// ---------------------------------------------------------------------

data class DailyNumberResult(
    val personalDayNumber: Int,
    val date: LocalDate
)

/**
 * Calculates a "Personal Day Number" by combining the user's Life Path
 * Number with today's day + month, then reducing. This gives a rotating
 * daily guidance number personalized to the user.
 */
fun calculateDailyNumber(lifePathNumber: Int, date: LocalDate = LocalDate.now()): DailyNumberResult {
    val total = lifePathNumber + date.dayOfMonth + date.monthValue
    return DailyNumberResult(
        personalDayNumber = reduceToSingleDigit(total, keepMasterNumbers = false),
        date = date
    )
}

// ---------------------------------------------------------------------
// COMPATIBILITY — compares two Life Path Numbers
// ---------------------------------------------------------------------

enum class CompatibilityTier { HIGH, MEDIUM, LOW }

data class CompatibilityResult(
    val numberA: Int,
    val numberB: Int,
    val score: Int,        // 0-100
    val tier: CompatibilityTier
)

// Simple compatibility matrix based on common numerology pairing rules.
// Rows/cols indexed 1-9 (index 0 unused).
private val COMPATIBILITY_SCORES: Array<IntArray> = arrayOf(
    intArrayOf(0,  0,  0,  0,  0,  0,  0,  0,  0,  0),
    intArrayOf(0, 70, 60, 85, 55, 80, 65, 50, 75, 90),
    intArrayOf(0, 60, 90, 55, 85, 65, 95, 60, 70, 75),
    intArrayOf(0, 85, 55, 80, 50, 90, 60, 45, 85, 70),
    intArrayOf(0, 55, 85, 50, 90, 45, 80, 70, 60, 65),
    intArrayOf(0, 80, 65, 90, 45, 75, 55, 60, 65, 85),
    intArrayOf(0, 65, 95, 60, 80, 55, 90, 50, 75, 70),
    intArrayOf(0, 50, 60, 45, 70, 60, 50, 85, 55, 60),
    intArrayOf(0, 75, 70, 85, 60, 65, 75, 55, 90, 65),
    intArrayOf(0, 90, 75, 70, 65, 85, 70, 60, 65, 95)
)

/**
 * Calculates compatibility between two people based on their Life Path
 * Numbers. Master numbers are reduced further here since the
 * compatibility matrix only covers 1-9.
 */
fun calculateCompatibility(lifePathA: Int, lifePathB: Int): CompatibilityResult {
    val a = reduceToSingleDigit(lifePathA, keepMasterNumbers = false).coerceIn(1, 9)
    val b = reduceToSingleDigit(lifePathB, keepMasterNumbers = false).coerceIn(1, 9)

    val score = COMPATIBILITY_SCORES[a][b]
    val tier = when {
        score >= 80 -> CompatibilityTier.HIGH
        score >= 60 -> CompatibilityTier.MEDIUM
        else -> CompatibilityTier.LOW
    }

    return CompatibilityResult(numberA = lifePathA, numberB = lifePathB, score = score, tier = tier)
}

// ---------------------------------------------------------------------
// BIRTHDAY NUMBER — derived from just the day of the month born on
// ---------------------------------------------------------------------

data class BirthdayNumberResult(val number: Int, val day: Int)

/**
 * The Birthday Number highlights a specific natural talent layered on
 * top of the broader Life Path. Derived purely from the day-of-month.
 */
fun calculateBirthdayNumber(birthDate: LocalDate): BirthdayNumberResult {
    val reduced = reduceToSingleDigit(birthDate.dayOfMonth)
    return BirthdayNumberResult(number = reduced, day = birthDate.dayOfMonth)
}

// ---------------------------------------------------------------------
// MATURITY NUMBER — Life Path + Destiny combined, the "future self"
// ---------------------------------------------------------------------

data class MaturityNumberResult(val number: Int, val lifePath: Int, val destiny: Int)

/**
 * The Maturity Number blends Life Path and Destiny/Expression Number.
 * Traditionally interpreted as the person one grows into around
 * mid-life, once Life Path and Destiny influences have been reconciled.
 */
fun calculateMaturityNumber(lifePathNumber: Int, destinyNumber: Int): MaturityNumberResult {
    val total = lifePathNumber + destinyNumber
    return MaturityNumberResult(
        number = reduceToSingleDigit(total),
        lifePath = lifePathNumber,
        destiny = destinyNumber
    )
}

// ---------------------------------------------------------------------
// PERSONAL YEAR NUMBER — the theme for a specific calendar year
// ---------------------------------------------------------------------

data class PersonalYearResult(val number: Int, val year: Int)

/**
 * Personal Year Number combines birth day + birth month with a target
 * year (defaults to the current year) to reveal that year's theme.
 */
fun calculatePersonalYear(birthDate: LocalDate, targetYear: Int = LocalDate.now().year): PersonalYearResult {
    val total = birthDate.dayOfMonth + birthDate.monthValue + sumDigits(targetYear.toString())
    return PersonalYearResult(
        number = reduceToSingleDigit(total, keepMasterNumbers = false),
        year = targetYear
    )
}

// ---------------------------------------------------------------------
// ATTITUDE NUMBER — the first impression others pick up
// ---------------------------------------------------------------------

data class AttitudeNumberResult(val number: Int)

/**
 * The Attitude Number (sometimes called the "Sun Number") is derived
 * from birth day + birth month only, reflecting instinctive first
 * impressions rather than the deeper Life Path.
 */
fun calculateAttitudeNumber(birthDate: LocalDate): AttitudeNumberResult {
    val total = birthDate.dayOfMonth + birthDate.monthValue
    return AttitudeNumberResult(number = reduceToSingleDigit(total))
}
