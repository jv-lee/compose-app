package com.lee.compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.lee.compose.page.HomePage
import com.lee.compose.page.MePage
import com.lee.compose.page.SquarePage
import com.lee.compose.ui.theme.ComposeAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeAppTheme { Navigator() }
        }
    }
}

@Composable
fun Navigator() {
    val selectIndex = remember { mutableStateOf(0) }
    val navController = rememberNavController()

    Scaffold(Modifier.fillMaxSize(), bottomBar = {
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
    }, content = {
        NavHost(navController = navController, startDestination = MainTab.Home.route) {
            composable(MainTab.Home.route) { HomePage(navController = navController) }
            composable(MainTab.Square.route) { SquarePage(navController = navController) }
            composable(MainTab.Me.route) { MePage(navController = navController) }
        }
    })

}

sealed class MainTab(val route: String, val icon: Int, val selectIcon: Int) {
    object Home : MainTab("Home", R.drawable.vector_home, R.drawable.vector_home_fill)
    object Square : MainTab("Square", R.drawable.vector_square, R.drawable.vector_square_fill)
    object Me : MainTab("Me", R.drawable.vector_me, R.drawable.vector_me_fill)
}

val tabItems = listOf(MainTab.Home, MainTab.Square, MainTab.Me)
