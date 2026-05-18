package com.example.smartenergy

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.smartenergy.ui.navigation.AppNavigation
import com.example.smartenergy.ui.theme.SmartEnergyTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SmartEnergyTheme {
                AppNavigation()
            }
        }
    }
}
