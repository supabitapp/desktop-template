package com.example.desktoptemplate

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent { Hello() }
    }
}

@Composable
fun Hello() {
    Box(
        modifier = Modifier.fillMaxSize().background(Color(0xFF121314)),
        contentAlignment = Alignment.Center,
    ) {
        Text("Hello, world", color = Color(0xFFEFF0F2), fontSize = 17.sp, fontWeight = FontWeight.SemiBold)
    }
}
