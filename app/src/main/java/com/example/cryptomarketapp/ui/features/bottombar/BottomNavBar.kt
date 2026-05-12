package com.example.cryptomarketapp.ui.features.bottombar

import android.annotation.SuppressLint
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalRippleConfiguration
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.cryptomarketapp.R
import com.example.cryptomarketapp.ui.features.navigation.Routes
import com.example.cryptomarketapp.ui.theme.CryptoMarketAppTheme

@SuppressLint("RestrictedApi")
@Composable
fun BottomNavBar(
    navController: NavController
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val navItems = remember {
        listOf(
            NavItem(R.string.nav_home, R.drawable.home, R.drawable.home_selected, Routes.HomeScreen),
            NavItem(R.string.nav_market, R.drawable.bag, R.drawable.bag_selected, Routes.MarketScreen),
            NavItem(R.string.nav_profile, R.drawable.profile, R.drawable.profile_selected, Routes.ProfileScreen),
        )
    }

    val selectedIndex by remember(currentDestination) {
        derivedStateOf {
            navItems.indexOfFirst { item ->
                currentDestination?.hierarchy?.any { it.hasRoute(item.route::class) } == true
            }.coerceAtLeast(0)
        }
    }

    BoxWithConstraints {
        val totalWidth = maxWidth
        val itemWidth = totalWidth / navItems.size
        val indicatorWidth = 72.dp
        val targetX = (itemWidth * selectedIndex) + (itemWidth / 2) - (indicatorWidth / 2)

        val animatedX by animateDpAsState(
            targetValue = targetX,
            animationSpec = spring(
                dampingRatio = Spring.DampingRatioNoBouncy,
                stiffness = Spring.StiffnessMedium
            ),
            label = "line_movement"
        )

        NavigationBar(
            containerColor = MaterialTheme.colorScheme.background,
            modifier = Modifier.height(100.dp),
            tonalElevation = 0.dp
        ) {
            navItems.forEachIndexed { index, item ->
                val isSelected = index == selectedIndex
                val selectedColor = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary

                CompositionLocalProvider(LocalRippleConfiguration provides null) {
                    NavigationBarItem(
                        selected = isSelected,
                        onClick = {
                            if (!isSelected) {
                                navController.navigate(item.route) {
                                    popUpTo(navController.graph.startDestinationId) { saveState = true }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        },
                        icon = {
                            Icon(
                                painter = painterResource(if (isSelected) item.iconSelected else item.icon),
                                contentDescription = null,
                                tint = selectedColor
                            )
                        },
                        label = {
                            Text(
                                text = stringResource(item.name),
                                style = MaterialTheme.typography.labelLarge,
                                color = selectedColor
                            )
                        },
                        colors = NavigationBarItemDefaults.colors(
                            indicatorColor = Color.Transparent
                        )
                    )
                }
            }
        }

        HorizontalDivider(
            modifier = Modifier
                .width(indicatorWidth)
                .graphicsLayer {
                    translationX = animatedX.toPx()
                },
            thickness = 3.dp,
            color = MaterialTheme.colorScheme.primary
        )
    }
}

@Preview
@Composable
private fun BottomNavBarPreview() {
    val navController = rememberNavController()
    CryptoMarketAppTheme {
        BottomNavBar(navController = navController)
    }
}

data class NavItem<T : Any>(
    @StringRes val name: Int,
    @DrawableRes val icon: Int,
    @DrawableRes val iconSelected: Int,
    val route: T,
)