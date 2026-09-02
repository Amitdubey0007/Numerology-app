package com.numerology.app.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Face
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.numerology.app.core.*
import com.numerology.app.ui.components.NumberChip
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

/** One row in the full profile summary list. */
private data class ProfileNumberEntry(
    val number: Int,
    val title: String,
    val framingText: String
)

/** Holds the computed profile so the summary list and detail screen share one source. */
private data class FullProfile(
    val lifePath: Int,
    val destiny: Int,
    val soulUrge: Int,
    val personality: Int,
    val birthday: Int,
    val maturity: Int,
    val personalYear: Int,
    val attitude: Int
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FullProfileScreen(onBack: () -> Unit) {
    var dateText by remember { mutableStateOf("") }
    var nameText by remember { mutableStateOf("") }
    var error by remember { mutableStateOf<String?>(null) }
    var profile by remember { mutableStateOf<FullProfile?>(null) }
    var selectedEntry by remember { mutableStateOf<ProfileNumberEntry?>(null) }

    // Detail screen takes over when a row is tapped.
    selectedEntry?.let { entry ->
        NumberDetailScreen(
            number = entry.number,
            screenTitle = entry.title,
            framingText = entry.framingText,
            onBack = { selectedEntry = null }
        )
        return
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Full Numerology Profile") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(20.dp)
        ) {
            if (profile == null) {
                Text("Enter your details", style = MaterialTheme.typography.titleLarge)
                Text(
                    "We'll calculate all 8 of your core numbers at once.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(Modifier.height(20.dp))

                OutlinedTextField(
                    value = dateText,
                    onValueChange = { dateText = it; error = null },
                    label = { Text("Birth date (DD/MM/YYYY)") },
                    placeholder = { Text("e.g. 07/02/1999") },
                    leadingIcon = { Icon(Icons.Filled.DateRange, contentDescription = null) },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(Modifier.height(12.dp))
                OutlinedTextField(
                    value = nameText,
                    onValueChange = { nameText = it; error = null },
                    label = { Text("Full name") },
                    placeholder = { Text("e.g. Priya Sharma") },
                    leadingIcon = { Icon(Icons.Filled.Face, contentDescription = null) },
                    isError = error != null,
                    supportingText = { error?.let { Text(it) } },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(Modifier.height(20.dp))

                Button(
                    onClick = {
                        val date = parseProfileDate(dateText)
                        val nameValid = nameText.trim().any { it.isLetter() }
                        if (date == null || !nameValid) {
                            error = "Enter a valid date (DD/MM/YYYY) and name"
                        } else {
                            val lifePath = calculateLifePath(date).number
                            val nameNumbers = calculateNameNumbers(nameText.trim())
                            val maturity = calculateMaturityNumber(lifePath, nameNumbers.destinyNumber)
                            val birthday = calculateBirthdayNumber(date)
                            val personalYear = calculatePersonalYear(date)
                            val attitude = calculateAttitudeNumber(date)

                            profile = FullProfile(
                                lifePath = lifePath,
                                destiny = nameNumbers.destinyNumber,
                                soulUrge = nameNumbers.soulUrgeNumber,
                                personality = nameNumbers.personalityNumber,
                                birthday = birthday.number,
                                maturity = maturity.number,
                                personalYear = personalYear.number,
                                attitude = attitude.number
                            )
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Calculate my profile")
                }
            } else {
                ProfileSummaryList(
                    profile = profile!!,
                    onEntryClick = { selectedEntry = it }
                )
            }
        }
    }
}

@Composable
private fun ProfileSummaryList(profile: FullProfile, onEntryClick: (ProfileNumberEntry) -> Unit) {
    val currentYear = LocalDate.now().year
    val entries = listOf(
        ProfileNumberEntry(
            profile.lifePath, "Life Path Number",
            "Your Life Path Number is the core theme of your life's journey, calculated from your full birth date."
        ),
        ProfileNumberEntry(
            profile.destiny, "Destiny Number",
            "Your Destiny (Expression) Number reflects the talents and path suggested by your full name."
        ),
        ProfileNumberEntry(
            profile.soulUrge, "Soul Urge Number",
            "Your Soul Urge Number, drawn from the vowels in your name, reveals your inner desires and motivations."
        ),
        ProfileNumberEntry(
            profile.personality, "Personality Number",
            "Your Personality Number, drawn from the consonants in your name, reflects the impression you make on others."
        ),
        ProfileNumberEntry(
            profile.birthday, "Birthday Number",
            "Your Birthday Number highlights a specific natural talent, layered on top of your broader Life Path."
        ),
        ProfileNumberEntry(
            profile.maturity, "Maturity Number",
            "Your Maturity Number blends your Life Path and Destiny — often described as the person you grow into by mid-life."
        ),
        ProfileNumberEntry(
            profile.personalYear, "Personal Year ($currentYear)",
            "Your Personal Year Number reveals the overarching theme and energy for you during $currentYear."
        ),
        ProfileNumberEntry(
            profile.attitude, "Attitude Number",
            "Your Attitude Number reflects the instinctive first impression others pick up from you, before they know you well."
        )
    )

    LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        items(entries) { entry ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onEntryClick(entry) },
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
            ) {
                Row(
                    modifier = Modifier.padding(14.dp),
                    verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
                ) {
                    NumberChip(number = entry.number)
                    Text(
                        entry.title,
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier
                            .padding(start = 14.dp)
                            .weight(1f)
                    )
                    Icon(
                        Icons.Filled.ChevronRight,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}

private fun parseProfileDate(text: String): LocalDate? {
    val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
    return try {
        LocalDate.parse(text.trim(), formatter)
    } catch (e: DateTimeParseException) {
        null
    }
}
