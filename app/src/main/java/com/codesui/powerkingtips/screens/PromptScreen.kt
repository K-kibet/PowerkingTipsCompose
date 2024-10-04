package com.codesui.powerkingtips.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material3.DrawerState
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.codesui.powerkingtips.R
import com.codesui.powerkingtips.ads.AdmobBanner
import com.codesui.powerkingtips.models.Routes
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun PromptScreen (rememberDrawerState: DrawerState, rememberCoroutineScope: CoroutineScope, navController: NavController, runAds :() -> Unit) {
    Surface (
        color = Color.White,
        modifier = Modifier.fillMaxSize()
    ){
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            //verticalArrangement = Arrangement.Center
        ) {
            TopSection(rememberDrawerState, rememberCoroutineScope)

            FilledTonalButton(
                modifier = Modifier
                    .wrapContentSize()
                    .height(60.dp)
                    .width(280.dp)
                    .background(color = colorResource(id = R.color.dark_green)),
                shape = RectangleShape,
                onClick = {
                    runAds.invoke()
                    navController.navigate(Routes.tipsScreen)
                }) {
                Row (
                    modifier = Modifier
                        .fillMaxSize(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "EXPLORE TIPS",
                        textAlign = TextAlign.Center,

                    )
                    Icon(
                        imageVector = Icons.Filled.KeyboardArrowRight,
                        contentDescription = "Forward Arrow",
                        modifier = Modifier.padding(10.dp),
                    )
                }

            }
            Spacer(modifier = Modifier.weight(1f))
            AdmobBanner()
        }
    }
}


@Composable
private fun TopSection(rememberDrawerState: DrawerState, rememberCoroutineScope: CoroutineScope) {
    Box(
        contentAlignment = Alignment.TopCenter
    ) {
        Image(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(fraction = 0.6f),
            painter = painterResource(id = R.drawable.powerking),
            contentDescription = null,
            contentScale = ContentScale.FillBounds
        )


        IconButton(
            modifier = Modifier
                .width(40.dp)
                .height(40.dp)
                .padding(5.dp)
                .align(alignment = Alignment.TopStart),
            onClick = {
                rememberCoroutineScope.launch {
                    rememberDrawerState.open()
                }
            }) {
            Icon(
                imageVector = Icons.Rounded.Menu,
                contentDescription = "Drawer Icon",
                modifier = Modifier
                    .fillMaxSize(),
                tint = Color.White
            )
        }

        Column (
            modifier = Modifier.padding(top = 60.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Text(
                text = stringResource(id = R.string.heading),
                style = MaterialTheme.typography.headlineMedium,
                color = Color.White,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = stringResource(id = R.string.slogan),
                style = MaterialTheme.typography.titleMedium,
                color = Color.White,
                fontStyle = FontStyle.Italic,
                fontSize = 24.sp
            )
            Spacer(modifier = Modifier.height(20.dp))
            Image(painter = painterResource(
                id = R.mipmap.ic_launcher_foreground
            ),
                contentDescription = "app Icon",
                modifier = Modifier
                    .size(150.dp)
                    .clip(CircleShape)                       // clip to the circle shape
                    .border(1.dp, colorResource(id = R.color.dark_green), CircleShape)
            )
        }

    }
}