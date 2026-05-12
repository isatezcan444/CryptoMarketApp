package com.example.cryptomarketapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Scaffold
import com.example.cryptomarketapp.ui.features.bottombar.BottomNavBar
import com.example.cryptomarketapp.ui.features.navigation.NavGraph
import com.example.cryptomarketapp.ui.theme.CryptoMarketAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CryptoMarketAppTheme {
                NavGraph()
            }
        }
    }
}