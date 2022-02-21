package com.lee.compose.widget

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.google.accompanist.insets.statusBarsHeight

/**
 * @author jv.lee
 * @date 2022/2/21
 * @description
 */
@Composable
fun AppBarView(
    title: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    navigationIcon: @Composable (() -> Unit)? = null,
    actions: @Composable RowScope.() -> Unit = {},
    backgroundColor: Color = Color.Black,
    contentColor: Color = contentColorFor(backgroundColor),
    elevation: Dp = AppBarDefaults.TopAppBarElevation
) {
    Surface(elevation = elevation) {
        Column {
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .statusBarsHeight()
                    .background(backgroundColor)
            )
            TopAppBar(
                title,
                modifier,
                navigationIcon,
                actions,
                backgroundColor,
                contentColor,
                0.dp
            )
        }
    }

}