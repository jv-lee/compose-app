package com.lee.compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.lee.compose.page.DetailsChildPage
import com.lee.compose.page.DetailsPage
import com.lee.compose.page.MainPage
import com.lee.compose.tools.SimpleAnimatedNavHost
import com.lee.compose.tools.sideComposable
import com.lee.compose.ui.theme.ComposeAppTheme

@ExperimentalAnimationApi
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)
        setContent {
            ProvideWindowInsets() {
                ComposeAppTheme {
                    Navigator()
                }
            }
        }
    }
}

@ExperimentalAnimationApi
@Composable
fun Navigator() {
    val navController = rememberAnimatedNavController()
    Scaffold(
        Modifier.fillMaxSize(),
        content = {
            SimpleAnimatedNavHost(
                navController = navController,
                startDestination = PageRoute.Main.route,
            ) {
                sideComposable(PageRoute.Main.route) { MainPage(rootNavController = navController) }
                sideComposable(PageRoute.Details.route) { DetailsPage(navController = navController) }
                sideComposable(PageRoute.DetailsChild.route) { DetailsChildPage(navController = navController) }
            }
        })
}

sealed class PageRoute(val route: String) {
    object Main : PageRoute("Main")
    object Details : PageRoute("Details")
    object DetailsChild : PageRoute("DetailsChild")
}
