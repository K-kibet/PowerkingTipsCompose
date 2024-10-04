package com.codesui.powerkingtips.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.codesui.footballfixtures.Requests.RetrofitClient
import com.codesui.powerkingtips.R
import com.codesui.powerkingtips.ads.AdmobBanner
import com.codesui.powerkingtips.resources.IndeterminateCircularIndicator
import com.codesui.powerkingtips.resources.NetworkError
import com.codesui.powerkingtips.resources.TopBar
import com.codesui.powerkingtips.resources.isInternetAvailable
import com.google.gson.JsonObject
import java.text.SimpleDateFormat
import java.util.Date

@Composable
fun DetailScreen (navController: NavController, runAds :() -> Unit, id: String){
    val prediction = remember { mutableStateOf<JsonObject?>(null) }
    val isLoading = remember { mutableStateOf(true) }
    val error = remember { mutableStateOf<String?>(null) }
    val league = remember { mutableStateOf("Failed To Load!")}
    val params = mapOf("action" to "get_predictions", "from" to getCurrentDate(), "to" to getCurrentDate(), "match_id" to id, "APIkey" to stringResource(id = R.string.api_key))
    var isButtonClicked by remember { mutableStateOf(true) }

    LaunchedEffect(isButtonClicked) {
        if (isButtonClicked) {
            try {
                val response = RetrofitClient.apiService.getPrediction(params)
                val responseObject = response.first().asJsonObject
                prediction.value = responseObject
            } catch (e: Exception) {
                error.value = e.message
            } finally {
                isLoading.value = false
            }
            isButtonClicked = false // Reset state after task
        }
    }


    Column (
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        TopBar(navController, league.value, runAds)
        when {
            isLoading.value -> {
                IndeterminateCircularIndicator()
            }

            error.value != null -> {
                when{
                    !isInternetAvailable(LocalContext.current) -> {
                        NetworkError {
                            isButtonClicked = true
                            isLoading.value = true
                            error.value = null
                        }
                    }

                    isInternetAvailable(LocalContext.current) -> {
                        Text(
                            modifier = Modifier
                                .padding(8.dp)
                                .fillMaxWidth(),
                            text = "Prediction For This Match is Not Available!",
                            fontWeight = FontWeight.Medium,
                            fontSize = 20.sp,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
            prediction.value != null -> {
                league.value = prediction.value!!.get("league_name").asString  + " - " + prediction.value!!.get("country_name").asString
                val home = prediction.value!!.get("match_hometeam_name").asString
                val away = prediction.value!!.get("match_awayteam_name").asString
                val homePercent = prediction.value!!.get("prob_HW").asString.split(".")[0]
                val drawPercent = prediction.value!!.get("prob_D").asString.split(".")[0]
                val awayPercent = prediction.value!!.get("prob_AW").asString.split(".")[0]
                val dc1x = prediction.value!!.get("prob_HW_D").asString.split(".")[0]
                val dc2x = prediction.value!!.get("prob_AW_D").asString.split(".")[0]
                val dc12 = prediction.value!!.get("prob_HW_AW").asString.split(".")[0]
                val ov2 = prediction.value!!.get("prob_O").asString.split(".")[0]
                val un2 = prediction.value!!.get("prob_U").asString.split(".")[0]
                val ov15 = prediction.value!!.get("prob_O_1").asString.split(".")[0]
                val un15 = prediction.value!!.get("prob_U_1").asString.split(".")[0]
                val ov35 = prediction.value!!.get("prob_U_3").asString.split(".")[0]
                val un35 = prediction.value!!.get("prob_U_3").asString.split(".")[0]
                val bts = prediction.value!!.get("prob_bts").asString.split(".")[0]
                val ots = prediction.value!!.get("prob_ots").asString.split(".")[0]
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = home,
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f),
                            style = MaterialTheme.typography.bodyMedium,
                            textAlign = TextAlign.End,
                            maxLines = 2,
                            overflow = TextOverflow.Clip,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 20.sp,
                        )
                        Text(
                            modifier = Modifier
                                .padding(16.dp)
                                .weight(.4f),
                            text = "VS",
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.Medium,
                            fontSize = 20.sp,
                            fontStyle = FontStyle.Italic
                        )
                        Text(
                            text = away,
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f),
                            style = MaterialTheme.typography.bodyMedium,
                            textAlign = TextAlign.Start,
                            maxLines = 2,
                            overflow = TextOverflow.Clip,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 20.sp,
                        )
                    }

                    Header(text = "1x2")
                    TeamStats2("Home",homePercent,"Draw",drawPercent,"Away",awayPercent)
                    Header(text = "Both Teams To Score (Gg/ng)")
                    TeamStats("GG", bts, "NG", ots)
                    Header(text = "Double Chance")
                    TeamStats2("1/X",dc1x,"2/X",dc2x,"1/2",dc12)
                    Header(text = "Total (OV/UN)")
                    TeamStats("OV 2.5", ov2, "UN 2.5", un2)
                    TeamStats("OV 1.5", ov15, "UN 1.5", un15)
                    TeamStats("OV 3.5", ov35, "UN 3.5", un35)
                    Spacer(modifier = Modifier.weight(1f))
                    AdmobBanner()
                }
            }
            
            
        }
    }
}

private fun getCurrentDate(): String {
    val dateFormat = SimpleDateFormat("yyyy-MM-dd")
    val currentDate = Date()
    return dateFormat.format(currentDate)
}

@Composable
fun TeamStats(firstText:String, firstPrediction: String, secondText: String, secondPrediction: String) {
    val isDarkTheme = isSystemInDarkTheme()

    val textColor = if (isDarkTheme) {
        Color.White
    } else {
        Color.Black
    }

    val bgColor = if (isDarkTheme) {
        Color.Gray
    } else {
        Color.LightGray
    }
    Row (
        modifier = Modifier
            .fillMaxWidth()
            .height(45.dp)
            .padding(5.dp),
        verticalAlignment = Alignment.CenterVertically
    ){

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .weight(1f)
                .background(
                    color = bgColor,
                    shape = RoundedCornerShape(16.dp) // 16.dp corner radius
                ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = firstText,
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center,
                color = textColor,
                modifier = Modifier
                    .wrapContentWidth()
                    .padding(start = 8.dp)
            )
            Text(
                text = "$firstPrediction%",
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center,
                color = textColor,
                modifier = Modifier
                    .wrapContentWidth()
                    .padding(end = 8.dp)
            )
        }
        Spacer(modifier = Modifier.width(5.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .weight(1f)
                .background(
                    color = bgColor,
                    shape = RoundedCornerShape(16.dp) // 16.dp corner radius
                ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = secondText,
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center,
                color = textColor,
                modifier = Modifier
                    .wrapContentWidth()
                    .padding(start = 8.dp)
            )
            Text(
                text = "$secondPrediction%",
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center,
                color = textColor,
                modifier = Modifier
                    .wrapContentWidth()
                    .padding(end = 8.dp)
            )
        }
    }
}

@Composable
fun TeamStats2(firstText:String, firstPrediction: String, secondText: String, secondPrediction: String, thirdText: String, thirdPrediction: String) {
    val isDarkTheme = isSystemInDarkTheme()

    val textColor = if (isDarkTheme) {
        Color.White
    } else {
        Color.Black
    }

    val bgColor = if (isDarkTheme) {
        Color.Gray
    } else {
        Color.LightGray
    }
    Row (
        modifier = Modifier
            .fillMaxWidth()
            .height(45.dp)
            .padding(5.dp),
        verticalAlignment = Alignment.CenterVertically
    ){

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .weight(1f)
                .background(
                    color = bgColor,
                    shape = RoundedCornerShape(16.dp) // 16.dp corner radius
                ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = firstText,
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center,
                color = textColor,
                modifier = Modifier
                    .wrapContentWidth()
                    .padding(start = 8.dp)
            )
            Text(
                text = "$firstPrediction%",
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center,
                color = textColor,
                modifier = Modifier
                    .wrapContentWidth()
                    .padding(end = 8.dp)
            )
        }
        Spacer(modifier = Modifier.width(5.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .weight(1f)
                .background(
                    color = bgColor,
                    shape = RoundedCornerShape(16.dp) // 16.dp corner radius
                ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = secondText,
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center,
                color = textColor,
                modifier = Modifier
                    .wrapContentWidth()
                    .padding(start = 8.dp)
            )
            Text(
                text = "$secondPrediction%",
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center,
                color = textColor,
                modifier = Modifier
                    .wrapContentWidth()
                    .padding(end = 8.dp)
            )
        }

        Spacer(modifier = Modifier.width(5.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .weight(1f)
                .background(
                    color = bgColor,
                    shape = RoundedCornerShape(16.dp) // 16.dp corner radius
                ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = thirdText,
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center,
                color = textColor,
                modifier = Modifier
                    .wrapContentWidth()
                    .padding(start = 8.dp)
            )
            Text(
                text = "$thirdPrediction%",
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center,
                color = textColor,
                modifier = Modifier
                    .wrapContentWidth()
                    .padding(end = 8.dp)
            )
        }
    }
}
@Composable
fun Header(text:String){
    Text(
        modifier = Modifier
            .padding(8.dp)
            .padding(vertical = 8.dp, horizontal = 12.dp)
            .fillMaxWidth(),
        text = "⭐ $text",
        fontWeight = FontWeight.Medium,
        fontSize = 20.sp,
        textAlign = TextAlign.Start
    )
}