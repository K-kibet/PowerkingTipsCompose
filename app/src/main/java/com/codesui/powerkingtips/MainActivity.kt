package com.codesui.powerkingtips

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.codesui.powerkingtips.ads.AppOpenAdManager
import com.codesui.powerkingtips.ads.RewardedInterstitialManager
import com.codesui.powerkingtips.resources.AppNavigation
import com.codesui.powerkingtips.ui.theme.PowerkingTipsTheme
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import kotlinx.coroutines.runBlocking

class MainActivity : ComponentActivity() {
    private lateinit var appOpenAdManager: AppOpenAdManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        runBlocking {
            installSplashScreen()
        }

        appOpenAdManager = AppOpenAdManager()
        appOpenAdManager.loadAd(this@MainActivity)
        setContent {
            PowerkingTipsTheme {
                // A surface container using the 'background' color from the theme
                val rewardedInterstitialManager = RewardedInterstitialManager(context = LocalContext.current, activity =  this@MainActivity)
                rewardedInterstitialManager.loadAd()
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                   AppNavigation({ showInterstialAd() }, {
                       appOpenAdManager.showAdIfAvailable(this@MainActivity)
                   }, {
                       rewardedInterstitialManager.loadAd()
                       rewardedInterstitialManager.showAdNow()
                   })
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        appOpenAdManager.showAdIfAvailable(this@MainActivity)
    }

    override fun onRestart() {
        super.onRestart()
        appOpenAdManager.showAdIfAvailable(this@MainActivity)
    }

    private fun showInterstialAd() {
        InterstitialAd.load(
            this,
            getString(R.string.Interstitial_Ad_Unit), //Change this with your own AdUnitID!
            AdRequest.Builder().build(),
            object : InterstitialAdLoadCallback() {
                override fun onAdFailedToLoad(adError: LoadAdError) {
                }

                override fun onAdLoaded(interstitialAd: InterstitialAd) {
                    interstitialAd.show(this@MainActivity)
                }
            }
        )
    }

}



