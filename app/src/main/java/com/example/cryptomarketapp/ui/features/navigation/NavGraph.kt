package com.example.cryptomarketapp.ui.features.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.cryptomarketapp.ui.features.bottombar.BottomNavBar
import com.example.cryptomarketapp.ui.features.home.screen.HomeScreen

@Composable
fun NavGraph() {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            BottomNavBar(navController = navController)
        },
        containerColor = MaterialTheme.colorScheme.surface
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Routes.HomeScreen,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable<Routes.HomeScreen> {
                HomeScreen(navController = navController)
            }

            composable<Routes.MarketScreen> {
                HomeScreen(navController = navController)
            }

            composable<Routes.ProfileScreen> {
                HomeScreen(navController = navController)
            }
        }
    }


}