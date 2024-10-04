package com.codesui.powerkingtips.screens

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.codesui.powerkingtips.R
import com.codesui.powerkingtips.ads.AdmobBanner
import com.codesui.powerkingtips.resources.TopBar
import com.farimarwat.composenativeadmob.nativead.BannerAdAdmobMedium
import com.farimarwat.composenativeadmob.nativead.BannerAdAdmobSmall
import com.farimarwat.composenativeadmob.nativead.rememberNativeAdState

@Composable
fun TermsScreen (navController: NavController, runAds :() -> Unit){

    //get a context to use in the library
    val context = LocalContext.current

    Column (
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ){
        val adState = rememberNativeAdState(context = context, adUnitId = context.getString(R.string.Native_Ad_Id))
        TopBar(navController, stringResource(id = R.string.terms_of_service), runAds)
        Spacer(modifier = Modifier.height(10.dp))
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .weight(1f, false)
                .fillMaxHeight(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CardItem(
                modifier = Modifier,
                title = "Introduction",
                description = "Welcome to Powerking Tips. By downloading, installing, or using our mobile application, you agree to comply with and be bound by these Terms of Service. If you do not agree with these Terms, please do not use the App."
            )

            CardItem(
                modifier = Modifier,
                title = "Use of the App",
                description = "You must be at least 18 years old to use the App. By using the App, you represent and warrant that you are at least 18 years old."
            )

            CardItem(
                modifier = Modifier,
                title = "Predictions and Betting Tips",
                description = "1. Nature of Predictions\n" +
                        "The App provides football predictions and betting tips for informational purposes only. These predictions and tips are based on our analysis and are not guaranteed to be accurate or reliable.\n" +
                        "\n" +
                        "2. No Liability\n" +
                        "We are not responsible for any financial loss or damages that may result from your use of the App’s predictions and betting tips. Use them at your own risk."
            )
            BannerAdAdmobSmall(loadedAd = adState)
            CardItem(
                modifier = Modifier,
                title = "Third-Party Services",
                description = "1. Advertisements\n" +
                        "The App may display advertisements from third-party services. We are not responsible for the content of these advertisements or the products or services they promote.\n" +
                        "\n" +
                        "2. Links to Third-Party Websites\n" +
                        "The App may contain links to third-party websites. We do not control these websites and are not responsible for their content or policies. Your use of third-party websites is at your own risk."
            )

            CardItem(
                modifier = Modifier,
                title = "Privacy",
                description = "1. Data Collection\n" +
                        "We collect and use your personal information in accordance with our Privacy Policy. By using the App, you consent to such collection and use.\n" +
                        "\n" +
                        "2. Third-Party Services\n" +
                        "Our App may use third-party services that collect information used to identify you. Please refer to the privacy policies of these third-party service providers for more details."
            )


            CardItem(
                modifier = Modifier,
                title = "Limitation of Liability",
                description = "To the fullest extent permitted by law, we shall not be liable for any indirect, incidental, special, consequential, or punitive damages, or any loss of profits or revenues, whether incurred directly or indirectly, or any loss of data, use, goodwill, or other intangible losses, resulting from:\n" +
                        "\n" +
                        "Your use or inability to use the App.\n" +
                        "Any unauthorized access to or use of our servers and/or any personal information stored therein.\n" +
                        "Any interruption or cessation of transmission to or from the App."
            )
            Spacer(modifier = Modifier.fillMaxWidth())
            BannerAdAdmobMedium(loadedAd = adState)
            CardItem(
                modifier = Modifier,
                title = "Changes to the Terms",
                description = "We reserve the right to modify these Terms at any time. We will notify you of any changes by posting the new Terms on this page. Your continued use of the App after such changes constitutes your acceptance of the new Terms."
            )

            CardItem(
                modifier = Modifier,
                title = "The service is provided without any warranties or guarantees",
                description = "The service is provided without warranty of any kind. If you are dissatisfied with any portion of the service, or with these terms, your sole and exclusive remedy is to discontinue using the service."
            )

            Spacer(modifier = Modifier.height(5.dp))
            Text(
                text = "By using the App, you acknowledge that you have read, understood, and agree to be bound by these Terms of Service.",
                fontSize = 18.sp,
                fontWeight =  FontWeight.Normal,
                modifier = Modifier
                    .padding(5.dp)
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(5.dp),
            )
        }
        AdmobBanner()
    }
}

@Composable
fun CardItem(modifier: Modifier, title : String, description: String){
    val isDarkTheme = isSystemInDarkTheme()

    val textColor = if (isDarkTheme) Color.White else Color.Black
    val bgColor = if (isDarkTheme) Color.DarkGray else Color.White
    Card(
        modifier
            .padding(5.dp)
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(5.dp),
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(
            containerColor = bgColor
        ),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Box(
            modifier
                .padding(5.dp)
                .fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(5.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = title,
                    fontSize = 22.sp,
                    fontWeight =  FontWeight.Bold,
                    color = textColor
                )
                Spacer(modifier = modifier.height(5.dp))
                Text(
                    text = description,
                    fontSize = 18.sp,
                    fontWeight =  FontWeight.Normal,
                    color = textColor
                )
            }
        }
    }
}