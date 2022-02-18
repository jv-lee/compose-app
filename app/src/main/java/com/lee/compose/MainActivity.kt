package com.lee.compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.*
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.VisibilityThreshold
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.IntOffset
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.lee.compose.page.DetailsChildPage
import com.lee.compose.page.DetailsPage
import com.lee.compose.page.MainPage
import com.lee.compose.ui.theme.ComposeAppTheme

@ExperimentalAnimationApi
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeAppTheme { Navigator() }
        }
    }
}

@ExperimentalAnimationApi
@Composable
fun Navigator() {
    val navController = rememberAnimatedNavController()
    Scaffold(Modifier.fillMaxSize(), content = {
        AnimatedNavHost(
            navController = navController,
            startDestination = PageRoute.Main.route,
            enterTransition = { fadeIn(initialAlpha = 0F) },
            exitTransition = { fadeOut(targetAlpha = 0F) },
            popEnterTransition = { fadeIn(initialAlpha = 0F) },
            popExitTransition = { fadeOut(targetAlpha = 0F) },
        ) {
            sideComposable(PageRoute.Main.route) { MainPage(rootNavController = navController) }
            sideComposable(PageRoute.Details.route) { DetailsPage(navController = navController) }
            sideComposable(PageRoute.DetailsChild.route) { DetailsChildPage(navController = navController) }
        }
    })
}

@ExperimentalAnimationApi
fun NavGraphBuilder.sideComposable(
    route: String,
    content: @Composable AnimatedVisibilityScope.(NavBackStackEntry) -> Unit
) {
    composable(route = route, content = content,
        // 打开页面进入动画
        enterTransition = {
            slideInHorizontally(initialOffsetX = { it * 2 })
        },
        // 打开页面退出动画
        exitTransition = {
            slideOutHorizontally(
                spring(
                    stiffness = 25F,
                    visibilityThreshold = IntOffset.VisibilityThreshold
                ), targetOffsetX = { -it })
        },
        // 关闭页面进入动画
        popEnterTransition = {
            slideInHorizontally(initialOffsetX = { -it })
        },
        // 关闭页面退出动画
        popExitTransition = {
            slideOutHorizontally(spring(
                stiffness = Spring.StiffnessVeryLow,
                visibilityThreshold = IntOffset.VisibilityThreshold
            ), targetOffsetX = { it * 2 })
        }
    )
}

sealed class PageRoute(val route: String) {
    object Main : PageRoute("Main")
    object Details : PageRoute("Details")
    object DetailsChild : PageRoute("DetailsChild")
}
