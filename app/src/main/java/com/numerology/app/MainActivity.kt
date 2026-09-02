package com.numerology.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.numerology.app.ui.navigation.NumerologyNavGraph
import com.numerology.app.ui.theme.NumerologyAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NumerologyAppTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    NumerologyNavGraph()
                }
            }
        }
    }
}
