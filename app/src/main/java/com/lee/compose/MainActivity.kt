package com.lee.compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.*
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.currentBackStackEntryAsState
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.lee.compose.page.DetailsPage
import com.lee.compose.page.HomePage
import com.lee.compose.page.MePage
import com.lee.compose.page.SquarePage
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
    val selectIndex = remember { mutableStateOf(0) }
    val navController = rememberAnimatedNavController()
    val navigationVisible = remember { mutableStateOf(true) }

    // 监听当前页面变化，非首页隐藏navigationBar
    navController.addOnDestinationChangedListener { _, destination, _ ->
        when (destination.route) {
            MainTab.Home.route, MainTab.Square.route, MainTab.Me.route -> {
                navigationVisible.value = true
            }
            else -> {
                navigationVisible.value = false
            }
        }
    }

    Scaffold(Modifier.fillMaxSize(), bottomBar = {
        // 根据页面状态显示隐藏navigationBar
        AnimatedVisibility(
            visible = navigationVisible.value,
            enter = slideInVertically(
                initialOffsetY = { fullHeight -> fullHeight },
                animationSpec = tween(durationMillis = 150, easing = FastOutLinearInEasing)
            ),
            exit = slideOutVertically(
                targetOffsetY = { fullHeight -> fullHeight * 2 },
                animationSpec = tween(durationMillis = 250, easing = FastOutLinearInEasing)
            )
        ) {
            BottomNavigation(backgroundColor = Color.White, elevation = 3.dp) {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route

                tabItems.forEachIndexed { index, item ->
                    if (currentRoute == item.route) selectIndex.value = index

                    BottomNavigationItem(
                        selected = currentRoute == item.route, onClick = {
                            navController.navigate(item.route) {
                                popUpTo(navController.graph.startDestinationId)
                                launchSingleTop = true
                            }
                        }, icon = {
                            val icon = if (selectIndex.value == index) {
                                tabItems[index].selectIcon
                            } else {
                                tabItems[index].icon
                            }
                            Image(painter = painterResource(id = icon), contentDescription = null)
                        }, label = {
                            Text(
                                text = tabItems[index].route,
                                textAlign = TextAlign.Center,
                                color = if (index == selectIndex.value) Color.Black else Color.Gray
                            )
                        })
                }
            }
        }
    }, content = {
        AnimatedNavHost(navController = navController, startDestination = MainTab.Home.route) {
            tabComposable(MainTab.Home.route) { HomePage(navController = navController) }
            tabComposable(MainTab.Square.route) { SquarePage(navController = navController) }
            tabComposable(MainTab.Me.route) { MePage(navController = navController) }
            sideComposable(PageRoute.Details.route) { DetailsPage(navController = navController) }
        }
    })

}

@ExperimentalAnimationApi
fun NavGraphBuilder.tabComposable(
    route: String,
    content: @Composable AnimatedVisibilityScope.(NavBackStackEntry) -> Unit
) {
    composable(route = route, content = content,
        enterTransition = { fadeIn(initialAlpha = 1F) },
        exitTransition = { fadeOut(targetAlpha = 1F) },
        popEnterTransition = { fadeIn(initialAlpha = 1F) },
        popExitTransition = { fadeOut(targetAlpha = 1F) })
}

@ExperimentalAnimationApi
fun NavGraphBuilder.sideComposable(
    route: String,
    content: @Composable AnimatedVisibilityScope.(NavBackStackEntry) -> Unit
) {
    composable(route = route, content = content,
        enterTransition = {
            slideInHorizontally(initialOffsetX = {
                it * 2
            })
        },
        exitTransition = {
            slideOutHorizontally()
        },
        popEnterTransition = {
            slideInHorizontally()
        },
        popExitTransition = {
            slideOutHorizontally()
        })
}


val tabItems = listOf(MainTab.Home, MainTab.Square, MainTab.Me)

sealed class MainTab(val route: String, val icon: Int, val selectIcon: Int) {
    object Home : MainTab("Home", R.drawable.vector_home, R.drawable.vector_home_fill)
    object Square : MainTab("Square", R.drawable.vector_square, R.drawable.vector_square_fill)
    object Me : MainTab("Me", R.drawable.vector_me, R.drawable.vector_me_fill)
}

sealed class PageRoute(val route: String) {
    object Details : PageRoute("Details")
}
