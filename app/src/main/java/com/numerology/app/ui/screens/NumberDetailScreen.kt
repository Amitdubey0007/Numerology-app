package com.numerology.app.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.numerology.app.core.NumberMeanings
import com.numerology.app.ui.components.NumberResultContent

/**
 * Generic detail screen for any numerology number. Used by the Full
 * Profile screen so each secondary number (Birthday, Maturity, Personal
 * Year, Attitude) gets its own explanation without needing a bespoke
 * screen per number type.
 *
 * @param screenTitle shown in the top bar (e.g. "Birthday Number")
 * @param framingText a short sentence explaining what THIS number type
 *   means, distinct from the general trait meaning (e.g. "Your Birthday
 *   Number highlights a natural talent layered on top of your Life Path.")
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NumberDetailScreen(
    number: Int,
    screenTitle: String,
    framingText: String,
    onBack: () -> Unit
) {
    val meaning = NumberMeanings.get(number)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(screenTitle) },
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
                .padding(horizontal = 20.dp)
        ) {
            Spacer(Modifier.height(4.dp))
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    framingText,
                    modifier = Modifier.padding(16.dp),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
            NumberResultContent(number = number, meaning = meaning)
        }
    }
}
