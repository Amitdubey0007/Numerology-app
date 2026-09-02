package com.numerology.app.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
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
import com.numerology.app.core.LifePathResult
import com.numerology.app.core.NumberMeanings
import com.numerology.app.core.calculateLifePath
import com.numerology.app.ui.components.NumberResultContent
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

/**
 * @param onResultCalculated called with the Life Path Number once computed,
 *   so the caller (e.g. a ViewModel) can persist it for "Today's Number"
 *   on the Home screen and for Compatibility comparisons.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LifePathScreen(
    onBack: () -> Unit,
    onResultCalculated: (Int) -> Unit = {}
) {
    var dateText by remember { mutableStateOf("") }
    var error by remember { mutableStateOf<String?>(null) }
    var result by remember { mutableStateOf<LifePathResult?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Life Path Number") },
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
            if (result == null) {
                Text(
                    "Enter your birth date",
                    style = MaterialTheme.typography.titleLarge
                )
                Text(
                    "We'll calculate your core Life Path Number from it.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(Modifier.height(20.dp))

                OutlinedTextField(
                    value = dateText,
                    onValueChange = {
                        dateText = it
                        error = null
                    },
                    label = { Text("DD/MM/YYYY") },
                    placeholder = { Text("e.g. 07/02/1999") },
                    leadingIcon = { Icon(Icons.Filled.DateRange, contentDescription = null) },
                    isError = error != null,
                    supportingText = { error?.let { Text(it) } },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(Modifier.height(20.dp))

                Button(
                    onClick = {
                        parseDate(dateText)?.let { date ->
                            val r = calculateLifePath(date)
                            result = r
                            onResultCalculated(r.number)
                        } ?: run {
                            error = "Enter a valid date as DD/MM/YYYY"
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Calculate")
                }
            } else {
                val meaning = NumberMeanings.get(result!!.number)
                NumberResultContent(number = result!!.number, meaning = meaning)
            }
        }
    }
}

private fun parseDate(text: String): LocalDate? {
    val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
    return try {
        LocalDate.parse(text.trim(), formatter)
    } catch (e: DateTimeParseException) {
        null
    }
}
