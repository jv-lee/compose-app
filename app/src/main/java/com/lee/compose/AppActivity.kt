package com.lee.compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

/**
 * @author jv.lee
 * @date 2022/2/23
 * @description
 */
class AppActivity : ComponentActivity() {

    private val tabItems = listOf(MainTab.Home, MainTab.Square, MainTab.Me)

    sealed class MainTab(val route: String, val icon: Int, val selectIcon: Int) {
        object Home : MainTab("Home", R.drawable.vector_home, R.drawable.vector_home_fill)
        object Square : MainTab("Square", R.drawable.vector_square,
            R.drawable.vector_square_fill)
        object Me : MainTab("Me", R.drawable.vector_me, R.drawable.vector_me_fill)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainNavigation()
        }
    }

    @Composable
    fun MainNavigation() {
        // navigation控制器 通过rememberNavController 记录控制器状态
        val navController = rememberNavController()
        // bottomNavigation选中下标位置
        val selectIndex = remember { mutableStateOf(0) }

        // 通过页面脚手架 设置页面content 及 底部导航栏
        Scaffold(Modifier.fillMaxSize(), backgroundColor = Color.Cyan, bottomBar = {

            // 创建底部导航栏
            BottomNavigation(backgroundColor = Color.White, elevation = 3.dp) {
                // 获取当前回退栈状态 根据回退栈内页面信息导航
                val navBackStackEntry by navController.currentBackStackEntryAsState()

                // 当前页面路由名称
                val route = navBackStackEntry?.destination?.route

                // 构建item
                tabItems.forEachIndexed { index, item ->
                    if (route == item.route) selectIndex.value = index

                    BottomNavigationItem(selected = selectIndex.value == index, onClick = {
                        selectIndex.value = index
                        // 路由导航
                        navController.navigate(item.route) {
                            popUpTo(navController.graph.startDestinationId)
                            launchSingleTop = true
                        }
                    }, icon = {
                        val icon = if (selectIndex.value == index) item.selectIcon else item.icon
                        Image(painter = painterResource(id = icon), contentDescription = null)
                    }, label = {
                        val color = if (selectIndex.value == index) Color.Black else Color.Gray
                        Text(text = item.route, textAlign = TextAlign.Center, color = color)
                    })
                }
            }
        }, content = {
            // 使用 Navigation Compose库的 导航路由组件注册所有页面
            NavHost(navController = navController, startDestination = MainTab.Home.route)
            {
                composable(MainTab.Home.route) { HomePage(navController) }
                composable(MainTab.Square.route) { SquarePage(navController) }
                composable(MainTab.Me.route) { MePage(navController) }
            }
        })
    }

    @Composable
    fun HomePage(navController: NavController) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .wrapContentSize(align = Alignment.Center)
        ) {
            Text(text = "home page")
        }
    }

    @Composable
    fun SquarePage(navController: NavController) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .wrapContentSize(align = Alignment.Center)
        ) {
            Text(text = "square page")
        }
    }

    @Composable
    fun MePage(navController: NavController) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .wrapContentSize(align = Alignment.Center)
        ) {
            Text(text = "me page")
        }
    }

}

