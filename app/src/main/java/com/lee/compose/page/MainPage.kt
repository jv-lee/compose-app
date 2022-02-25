package com.lee.compose.page

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.lee.compose.R

/**
 * @author jv.lee
 * @date 2022/2/18
 * @description
 */
@ExperimentalAnimationApi
@Composable
fun MainPage(navController: NavHostController) {
    val selectIndex = remember { mutableStateOf(0) }

    Scaffold(Modifier.fillMaxSize(), backgroundColor = Color.Cyan, bottomBar = {
        BottomNavigation(backgroundColor = Color.White, elevation = 3.dp) {
            tabItems.forEachIndexed { index, item ->
                BottomNavigationItem(selected = index == selectIndex.value, onClick = {
                    selectIndex.value = index
                }, icon = {
                    val icon = if (selectIndex.value == index) item.selectIcon else item.icon
                    Image(painter = painterResource(id = icon), contentDescription = null)
                }, label = {
                    val color = if (index == selectIndex.value) Color.Black else Color.Gray
                    Text(text = item.name, textAlign = TextAlign.Center, color = color)
                })
            }
        }
    }, content = {
        when (selectIndex.value) {
            0 -> HomePage(navController = navController)
            1 -> SquarePage(navController = navController)
            2 -> MePage(navController = navController)
        }
    })
}

val tabItems = listOf(MainTab.Home, MainTab.Square, MainTab.Me)

sealed class MainTab(val name: String, val icon: Int, val selectIcon: Int) {
    object Home : MainTab("Home", R.drawable.vector_home, R.drawable.vector_home_fill)
    object Square : MainTab("Square", R.drawable.vector_square, R.drawable.vector_square_fill)
    object Me : MainTab("Me", R.drawable.vector_me, R.drawable.vector_me_fill)
}

