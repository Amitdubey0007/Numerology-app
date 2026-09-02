package com.numerology.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.numerology.app.core.calculateDailyNumber
import com.numerology.app.core.NumberMeanings
import com.numerology.app.ui.components.NumberBadge
import java.time.LocalDate

/**
 * Home / dashboard screen.
 *
 * @param savedLifePathNumber the user's previously saved Life Path Number,
 *   if they've calculated one before — used to personalize "today's number".
 *   Pass null if no profile has been saved yet.
 */
@Composable
fun HomeScreen(
    savedLifePathNumber: Int?,
    onNavigateLifePath: () -> Unit,
    onNavigateName: () -> Unit,
    onNavigateDaily: () -> Unit,
    onNavigateCompatibility: () -> Unit,
    onNavigateFullProfile: () -> Unit
) {
    Scaffold { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(20.dp)
        ) {
            Text("Numerology", style = MaterialTheme.typography.headlineLarge)
            Text(
                "Discover what your numbers say",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(Modifier.height(24.dp))

            if (savedLifePathNumber != null) {
                TodaysNumberCard(
                    lifePathNumber = savedLifePathNumber,
                    onClick = onNavigateDaily
                )
                Spacer(Modifier.height(24.dp))
            }

            Text(
                "Explore",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(Modifier.height(12.dp))

            FeatureCard(
                icon = Icons.Filled.Star,
                title = "Full Numerology Profile",
                subtitle = "All 8 core numbers from your name and birth date",
                onClick = onNavigateFullProfile
            )
            Spacer(Modifier.height(12.dp))
            FeatureCard(
                icon = Icons.Filled.Person,
                title = "Life Path Number",
                subtitle = "What your birth date reveals about your purpose",
                onClick = onNavigateLifePath
            )
            Spacer(Modifier.height(12.dp))
            FeatureCard(
                icon = Icons.Filled.Face,
                title = "Name / Destiny Number",
                subtitle = "The meaning hidden in your full name",
                onClick = onNavigateName
            )
            Spacer(Modifier.height(12.dp))
            FeatureCard(
                icon = Icons.Filled.DateRange,
                title = "Daily Number",
                subtitle = "Your personalized guidance for today",
                onClick = onNavigateDaily
            )
            Spacer(Modifier.height(12.dp))
            FeatureCard(
                icon = Icons.Filled.FavoriteBorder,
                title = "Compatibility",
                subtitle = "Compare your number with someone else's",
                onClick = onNavigateCompatibility
            )
        }
    }
}

@Composable
private fun TodaysNumberCard(lifePathNumber: Int, onClick: () -> Unit) {
    val daily = calculateDailyNumber(lifePathNumber, LocalDate.now())
    val meaning = NumberMeanings.get(daily.personalDayNumber)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            NumberBadge(number = daily.personalDayNumber, size = 64)
            Column(modifier = Modifier.padding(start = 16.dp).weight(1f)) {
                Text(
                    "Today's Number",
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
                Text(
                    meaning.title,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
            Icon(Icons.Filled.ChevronRight, contentDescription = null)
        }
    }
}

@Composable
private fun FeatureCard(
    icon: ImageVector,
    title: String,
    subtitle: String,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                icon,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary
            )
            Column(modifier = Modifier.padding(start = 16.dp).weight(1f)) {
                Text(title, style = MaterialTheme.typography.titleMedium)
                Text(
                    subtitle,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            Icon(
                Icons.Filled.ChevronRight,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
