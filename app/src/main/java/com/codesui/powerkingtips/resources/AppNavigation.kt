package com.codesui.powerkingtips.resources

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.codesui.powerkingtips.models.Routes
import com.codesui.powerkingtips.screens.AboutScreen
import com.codesui.powerkingtips.screens.DetailScreen
import com.codesui.powerkingtips.screens.TermsScreen
import com.codesui.powerkingtips.screens.TipsScreen

@Composable
fun AppNavigation(runAds :() -> Unit, openAds: () -> Unit, rewardedAds : () -> Unit) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Routes.promptScreen, builder ={
        composable(Routes.promptScreen) {
            NavDrawer(navController, runAds, openAds)
        }


        composable(Routes.aboutScreen) {
            AboutScreen(navController, runAds)
        }

        composable(Routes.tipsScreen) {
            TipsScreen(navController, openAds, rewardedAds)
        }

        composable(Routes.termsScreen) {
            TermsScreen(navController, runAds)
        }

        composable(Routes.detailsScreen + "/{id}") {
            val id = it.arguments?.getString("id")
            DetailScreen(navController, runAds, id?: "323763")
        }
    } )
}