package com.example.smartenergy

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.smartenergy.ui.screen.DashboardScreen
import com.example.smartenergy.ui.screen.EdificiosScreen
import com.example.smartenergy.ui.screen.LoginScreen
import com.example.smartenergy.ui.theme.SmartEnergyTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SmartEnergyTheme {
                LoginScreen(onLoginClick = {email, pass -> {}}, onGuestClick = {})
            }
        }
    }
}
