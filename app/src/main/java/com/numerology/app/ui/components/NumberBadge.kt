package com.numerology.app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.numerology.app.ui.theme.Gold40
import com.numerology.app.ui.theme.Gold60
import com.numerology.app.ui.theme.Indigo60
import com.numerology.app.ui.theme.Indigo80

/**
 * Large circular badge showing a numerology number with a subtle
 * gradient — the visual centerpiece of every result screen.
 */
@Composable
fun NumberBadge(
    number: Int,
    modifier: Modifier = Modifier,
    size: Int = 120
) {
    Box(
        modifier = modifier
            .size(size.dp)
            .clip(CircleShape)
            .background(
                Brush.linearGradient(listOf(Indigo80, Indigo60))
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = number.toString(),
            color = Gold40,
            fontSize = (size / 2.6).sp,
            fontWeight = FontWeight.Bold
        )
    }
}

/** Smaller inline variant used in lists (e.g. compatibility screen). */
@Composable
fun NumberChip(number: Int, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .size(40.dp)
            .clip(CircleShape)
            .background(Indigo60),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = number.toString(),
            color = Gold60,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )
    }
}
