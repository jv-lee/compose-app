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
    ) {
        val (text) = createRefs()
        Text(text = "details page.", color = Color.Black, modifier = Modifier
            .clickable {
                navController.popBackStack()
            }
            .constrainAs(text) {
                start.linkTo(parent.start)
                top.linkTo(parent.top)
                end.linkTo(parent.end)
                bottom.linkTo(parent.bottom)
            })

    }
}