package com.sonicnest.app.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.sonicnest.app.ui.components.IconKind
import com.sonicnest.app.ui.components.LocalNotice
import com.sonicnest.app.ui.components.SonicIcon
import com.sonicnest.app.ui.screens.HomeScreen
import com.sonicnest.app.ui.screens.LibraryScreen
import com.sonicnest.app.ui.screens.NowPlayingScreen
import com.sonicnest.app.ui.screens.PlaylistsScreen
import com.sonicnest.app.ui.screens.SettingsScreen
import com.sonicnest.app.ui.theme.SonicColors
import kotlinx.coroutines.launch

object Routes {
    const val HOME = "home"
    const val LIBRARY = "library"
    const val PLAYLISTS = "playlists"
    const val SETTINGS = "settings"
    const val NOW_PLAYING = "now_playing"
}

private data class TopLevelDestination(val route: String, val label: String, val icon: IconKind)

private val topLevelDestinations = listOf(
    TopLevelDestination(Routes.HOME, "Home", IconKind.Home),
    TopLevelDestination(Routes.LIBRARY, "Library", IconKind.Library),
    TopLevelDestination(Routes.PLAYLISTS, "Playlists", IconKind.Playlists),
    TopLevelDestination(Routes.SETTINGS, "Settings", IconKind.Settings)
)

@Composable
fun SonicNestApp() {
    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val notice = remember<(String) -> Unit>(scope, snackbarHostState) {
        { message ->
            scope.launch {
                snackbarHostState.currentSnackbarData?.dismiss()
                snackbarHostState.showSnackbar(message)
            }
        }
    }

    fun goTo(route: String) {
        navController.navigate(route) {
            popUpTo(navController.graph.findStartDestination().id) { saveState = true }
            launchSingleTop = true
            restoreState = true
        }
    }

    CompositionLocalProvider(LocalNotice provides notice) {
        Scaffold(
            containerColor = SonicColors.Background,
            snackbarHost = { SnackbarHost(snackbarHostState) },
            bottomBar = {
                if (currentRoute != Routes.NOW_PLAYING) {
                    NavigationBar(containerColor = SonicColors.Panel) {
                        topLevelDestinations.forEach { item ->
                            val selected = currentRoute == item.route
                            NavigationBarItem(
                                selected = selected,
                                onClick = { goTo(item.route) },
                                icon = {
                                    SonicIcon(
                                        item.icon,
                                        if (selected) SonicColors.Accent else SonicColors.TextSecondary,
                                        Modifier.size(24.dp)
                                    )
                                },
                                label = { Text(item.label) },
                                colors = NavigationBarItemDefaults.colors(
                                    selectedTextColor = SonicColors.Accent,
                                    unselectedTextColor = SonicColors.TextSecondary,
                                    indicatorColor = SonicColors.PanelHigh
                                )
                            )
                        }
                    }
                }
            }
        ) { padding ->
            NavHost(
                navController = navController,
                startDestination = Routes.HOME,
                modifier = Modifier.padding(padding)
            ) {
                composable(Routes.HOME) {
                    HomeScreen(
                        onOpenNowPlaying = {
                            navController.navigate(Routes.NOW_PLAYING) { launchSingleTop = true }
                        }
                    )
                }
                composable(Routes.LIBRARY) { LibraryScreen() }
                composable(Routes.PLAYLISTS) { PlaylistsScreen() }
                composable(Routes.SETTINGS) { SettingsScreen() }
                composable(Routes.NOW_PLAYING) {
                    NowPlayingScreen(
                        onBack = { navController.popBackStack() },
                        onOpenSettings = { goTo(Routes.SETTINGS) }
                    )
                }
            }
        }
    }
}
