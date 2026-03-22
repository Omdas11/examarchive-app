package com.example.examarchive

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.LibraryBooks
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.examarchive.ui.screens.GenerateScreen
import com.example.examarchive.ui.screens.HistoryScreen
import com.example.examarchive.ui.screens.LibraryScreen
import com.example.examarchive.ui.screens.SettingsScreen
import com.example.examarchive.ui.theme.ExamArchiveTheme
import com.example.examarchive.ui.theme.ThemeOption
import com.example.examarchive.ui.theme.ThemeViewModel
import androidx.lifecycle.viewmodel.compose.viewModel

sealed class Screen(val route: String, val label: String, val icon: ImageVector) {
    object Library : Screen("library", "Library", Icons.Filled.LibraryBooks)
    object Generate : Screen("generate", "Generate", Icons.Filled.AutoAwesome)
    object History : Screen("history", "History", Icons.Filled.History)
    object Settings : Screen("settings", "Settings", Icons.Filled.Settings)
}

val bottomNavItems = listOf(
    Screen.Library,
    Screen.Generate,
    Screen.History,
    Screen.Settings
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val themeViewModel: ThemeViewModel = viewModel(factory = ThemeViewModel.factory(application))
            val themeOption by themeViewModel.themeOption.collectAsState()

            ExamArchiveTheme(themeOption = themeOption) {
                ExamArchiveApp(
                    themeOption = themeOption,
                    onThemeChange = themeViewModel::setTheme
                )
            }
        }
    }
}

@Composable
fun ExamArchiveApp(
    themeOption: ThemeOption,
    onThemeChange: (ThemeOption) -> Unit,
) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            NavigationBar {
                bottomNavItems.forEach { screen ->
                    NavigationBarItem(
                        icon = { Icon(screen.icon, contentDescription = screen.label) },
                        label = { Text(screen.label) },
                        selected = currentRoute == screen.route,
                        onClick = {
                            navController.navigate(screen.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Library.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Library.route) { LibraryScreen() }
            composable(Screen.Generate.route) { GenerateScreen() }
            composable(Screen.History.route) { HistoryScreen() }
            composable(Screen.Settings.route) {
                SettingsScreen(
                    themeOption = themeOption,
                    onThemeChange = onThemeChange
                )
            }
        }
    }
}
