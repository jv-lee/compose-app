package com.lee.compose.page

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import com.lee.compose.PageRoute
import com.lee.compose.widget.AppBarView

/**
 * @author jv.lee
 * @date 2022/2/16
 * @description
 */
@Composable
fun DetailsPage(navController: NavController) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(Color.Yellow)
            .clickable {}
    ) {
        val (appBar, text, text2) = createRefs()
        AppBarView(
            title = "Details",
            navigationClick = { navController.popBackStack() },
            modifier = Modifier.constrainAs(appBar) {
                start.linkTo(parent.start)
                top.linkTo(parent.top)
                end.linkTo(parent.end)
            })
        Text(text = "details page.", color = Color.Black, modifier = Modifier
            .clickable {
                navController.navigate(PageRoute.DetailsChild.route)
            }
            .constrainAs(text) {
                start.linkTo(parent.start)
                top.linkTo(appBar.bottom)
                end.linkTo(parent.end)
                bottom.linkTo(parent.bottom)
            })
        Text(text = "text", color = Color.Black, modifier = Modifier.constrainAs(text2) {
            start.linkTo(text.start)
            end.linkTo(text.end)
            bottom.linkTo(parent.bottom)
        })

    }
}