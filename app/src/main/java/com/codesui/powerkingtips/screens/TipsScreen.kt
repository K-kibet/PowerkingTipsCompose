package com.codesui.powerkingtips.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.codesui.powerkingtips.R
import com.codesui.powerkingtips.models.NavItem
import com.codesui.powerkingtips.resources.TopBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TipsScreen (navController: NavController, runAds :() -> Unit, rewardedAds : () -> Unit) {
    val navItemList = listOf(
        NavItem("Free", Icons.Default.List),
        NavItem("Premium", Icons.Default.Star)
    )

    var selectedIndex by remember {
        mutableIntStateOf(0)
    }

    Scaffold (
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            NavigationBar {
                navItemList.forEachIndexed { index, navItem ->
                    NavigationBarItem(
                        selected = selectedIndex == index,
                        onClick = {
                            selectedIndex = index
                        },
                        icon = {
                               Icon(imageVector = navItem.icon, contentDescription = "Icon")
                        },
                        label = {
                            Text(text = navItem.label)
                        }
                    )
                }
            }
        }
    ){innerPadding ->
        ContentScreen(Modifier.padding(innerPadding), selectedIndex, navController, runAds, rewardedAds)
    }
}


@Composable
fun ContentScreen(modifier: Modifier, selectedIndex : Int, navController: NavController, runAds: () -> Unit, rewardedAds: () -> Unit) {
    Column (
        modifier = modifier
    ) {
        TopBar(navController, stringResource(id = R.string.app_name), runAds)
        when(selectedIndex) {
            0 -> FreeScreen(navController = navController, runAds = runAds)
            1 -> {
                PremiumScreen()
                rewardedAds.invoke()
            }
        }
    }
}