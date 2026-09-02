package com.numerology.app.ui.screens

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.numerology.app.core.CompatibilityResult
import com.numerology.app.core.CompatibilityTier
import com.numerology.app.core.calculateCompatibility
import com.numerology.app.ui.components.NumberChip
import com.numerology.app.ui.theme.ErrorRed
import com.numerology.app.ui.theme.SuccessGreen
import com.numerology.app.ui.theme.WarningAmber

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CompatibilityScreen(onBack: () -> Unit) {
    var numberAText by remember { mutableStateOf("") }
    var numberBText by remember { mutableStateOf("") }
    var error by remember { mutableStateOf<String?>(null) }
    var result by remember { mutableStateOf<CompatibilityResult?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Compatibility") },
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
                Text("Compare two Life Path Numbers", style = MaterialTheme.typography.titleLarge)
                Text(
                    "Don't know them yet? Calculate each person's Life Path Number first.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(Modifier.height(20.dp))

                OutlinedTextField(
                    value = numberAText,
                    onValueChange = { numberAText = it; error = null },
                    label = { Text("Your Life Path Number") },
                    keyboardOptions = androidx.compose.foundation.text.KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(Modifier.height(12.dp))
                OutlinedTextField(
                    value = numberBText,
                    onValueChange = { numberBText = it; error = null },
                    label = { Text("Their Life Path Number") },
                    keyboardOptions = androidx.compose.foundation.text.KeyboardOptions(keyboardType = KeyboardType.Number),
                    isError = error != null,
                    supportingText = { error?.let { Text(it) } },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(Modifier.height(20.dp))

                Button(
                    onClick = {
                        val a = numberAText.trim().toIntOrNull()
                        val b = numberBText.trim().toIntOrNull()
                        if (a == null || b == null || a < 1 || b < 1) {
                            error = "Enter valid numbers (1-9, or 11/22/33)"
                        } else {
                            result = calculateCompatibility(a, b)
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Compare")
                }
            } else {
                CompatibilityResultView(result!!, onReset = { result = null })
            }
        }
    }
}

@Composable
private fun CompatibilityResultView(result: CompatibilityResult, onReset: () -> Unit) {
    val tierColor = when (result.tier) {
        CompatibilityTier.HIGH -> SuccessGreen
        CompatibilityTier.MEDIUM -> WarningAmber
        CompatibilityTier.LOW -> ErrorRed
    }
    val tierLabel = when (result.tier) {
        CompatibilityTier.HIGH -> "Strong compatibility"
        CompatibilityTier.MEDIUM -> "Moderate compatibility"
        CompatibilityTier.LOW -> "Needs conscious effort"
    }
    val animatedProgress by animateFloatAsState(
        targetValue = result.score / 100f,
        animationSpec = tween(durationMillis = 600),
        label = "compatibilityScore"
    )

    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            NumberChip(number = result.numberA)
            Text(
                "  +  ",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            NumberChip(number = result.numberB)
        }

        Spacer(Modifier.height(20.dp))

        Text(
            "${result.score}%",
            style = MaterialTheme.typography.displayLarge,
            fontWeight = FontWeight.Bold,
            color = tierColor
        )
        Text(tierLabel, style = MaterialTheme.typography.titleMedium, color = tierColor)

        Spacer(Modifier.height(16.dp))

        LinearProgressIndicator(
            progress = { animatedProgress },
            modifier = Modifier
                .fillMaxWidth()
                .height(10.dp),
            color = tierColor,
            trackColor = MaterialTheme.colorScheme.surfaceVariant
        )

        Spacer(Modifier.height(24.dp))

        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                compatibilityBlurb(result.tier),
                modifier = Modifier.padding(16.dp),
                style = MaterialTheme.typography.bodyMedium
            )
        }

        Spacer(Modifier.height(20.dp))

        Button(onClick = onReset, modifier = Modifier.fillMaxWidth()) {
            Text("Compare different numbers")
        }
    }
}

private fun compatibilityBlurb(tier: CompatibilityTier): String = when (tier) {
    CompatibilityTier.HIGH ->
        "These numbers naturally complement each other, with shared values or balancing strengths that make communication easier."
    CompatibilityTier.MEDIUM ->
        "There's a solid foundation here, though differing approaches to life may need occasional compromise."
    CompatibilityTier.LOW ->
        "These numbers see the world quite differently. It's not a dealbreaker, but it means understanding and patience matter more."
}
