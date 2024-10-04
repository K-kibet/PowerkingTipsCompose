package com.codesui.powerkingtips.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.KeyboardArrowRight
import androidx.compose.material.icons.rounded.Send
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.codesui.powerkingtips.R
import com.codesui.powerkingtips.ads.AdmobBanner
import com.codesui.powerkingtips.resources.UrlManager

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PremiumScreen() {
    val ctx = LocalContext.current
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ColumnItem(modifier = Modifier, name = "Chat Admin Now", icon = Icons.Rounded.Send, runFunction = {
            UrlManager(ctx.getString(R.string.telegram_admin), ctx)
        })
        ColumnItem(modifier = Modifier, name = "Join Our Channel", icon = Icons.Rounded.KeyboardArrowRight, runFunction = {
            UrlManager(ctx.getString(R.string.telegram_channel), ctx)
        })
        Spacer(modifier = Modifier.weight(1f))
        AdmobBanner()
    }
}