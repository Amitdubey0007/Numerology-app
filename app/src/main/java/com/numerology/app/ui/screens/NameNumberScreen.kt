package com.numerology.app.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.numerology.app.core.NameNumberResult
import com.numerology.app.core.NumberMeanings
import com.numerology.app.core.calculateNameNumbers
import com.numerology.app.ui.components.NumberResultContent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NameNumberScreen(onBack: () -> Unit) {
    var nameText by remember { mutableStateOf("") }
    var error by remember { mutableStateOf<String?>(null) }
    var result by remember { mutableStateOf<NameNumberResult?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Name / Destiny Number") },
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
                Text("Enter your full name", style = MaterialTheme.typography.titleLarge)
                Text(
                    "As it appears on your birth certificate works best.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(Modifier.height(20.dp))

                OutlinedTextField(
                    value = nameText,
                    onValueChange = {
                        nameText = it
                        error = null
                    },
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
                        val cleaned = nameText.trim()
                        if (cleaned.any { it.isLetter() }) {
                            result = calculateNameNumbers(cleaned)
                        } else {
                            error = "Enter a valid name"
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Calculate")
                }
            } else {
                NameResultView(result!!)
            }
        }
    }
}

@Composable
private fun NameResultView(result: NameNumberResult) {
    val destinyMeaning = NumberMeanings.get(result.destinyNumber)

    LazyColumn {
        item {
            // Sub-number summary row: Destiny is the headline, Soul Urge and
            // Personality are shown as supporting context.
            Row(
                modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                SubNumberCard("Soul Urge", result.soulUrgeNumber, "Inner desire", Modifier.weight(1f))
                SubNumberCard("Personality", result.personalityNumber, "Outer impression", Modifier.weight(1f))
            }
        }
        item {
            NumberResultContent(number = result.destinyNumber, meaning = destinyMeaning)
        }
    }
}

@Composable
private fun SubNumberCard(label: String, number: Int, caption: String, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(label, style = MaterialTheme.typography.labelLarge)
            Text(
                number.toString(),
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                caption,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
