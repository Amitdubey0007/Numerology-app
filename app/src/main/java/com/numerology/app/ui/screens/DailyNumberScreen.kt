package com.numerology.app.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.numerology.app.core.NumberMeanings
import com.numerology.app.core.calculateDailyNumber
import com.numerology.app.ui.components.NumberResultContent
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

/**
 * @param savedLifePathNumber the user's Life Path Number, calculated
 *   previously. If null, prompts the user to calculate it first since
 *   the Daily Number is derived from it.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DailyNumberScreen(
    savedLifePathNumber: Int?,
    onBack: () -> Unit,
    onGoToLifePath: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Daily Number") },
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
            if (savedLifePathNumber == null) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(Modifier.height(48.dp))
                    Text(
                        "Calculate your Life Path Number first",
                        style = MaterialTheme.typography.titleLarge,
                        textAlign = TextAlign.Center
                    )
                    Text(
                        "Your Daily Number is personalized using your Life Path Number.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        textAlign = TextAlign.Center
                    )
                    Spacer(Modifier.height(20.dp))
                    Button(onClick = onGoToLifePath, modifier = Modifier.fillMaxWidth()) {
                        Text("Go to Life Path")
                    }
                }
            } else {
                val today = LocalDate.now()
                val daily = calculateDailyNumber(savedLifePathNumber, today)
                val meaning = NumberMeanings.get(daily.personalDayNumber)
                val dateLabel = today.format(DateTimeFormatter.ofPattern("EEEE, d MMMM", Locale.getDefault()))

                Text(
                    dateLabel,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(Modifier.height(8.dp))
                NumberResultContent(number = daily.personalDayNumber, meaning = meaning)
            }
        }
    }
}
