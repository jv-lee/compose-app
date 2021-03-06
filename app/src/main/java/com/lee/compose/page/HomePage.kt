package com.lee.compose.page

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import com.lee.compose.PageRoute

/**
 * @author jv.lee
 * @date 2022/2/16
 * @description
 */
@Composable
fun HomePage(navController: NavController) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(Color.Cyan)
    ) {
        val (text, button) = createRefs()
        Text(text = "home page.", color = Color.Black, modifier = Modifier.constrainAs(text) {
            start.linkTo(parent.start)
            top.linkTo(parent.top)
            end.linkTo(parent.end)
            bottom.linkTo(parent.bottom)
        })

        Button(modifier = Modifier.constrainAs(button) {
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            top.linkTo(text.bottom)
        }, onClick = {
            navController.navigate(PageRoute.Details.route)
        }) {
            Text(text = "start to details")
        }
    }
}