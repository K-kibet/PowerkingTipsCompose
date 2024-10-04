package com.codesui.powerkingtips.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.codesui.footballfixtures.Requests.RetrofitClient
import com.codesui.powerkingtips.R
import com.codesui.powerkingtips.ads.AdmobBanner
import com.codesui.powerkingtips.models.Tip
import com.codesui.powerkingtips.resources.IndeterminateCircularIndicator
import com.codesui.powerkingtips.resources.NetworkError
import com.google.gson.JsonObject
import java.text.SimpleDateFormat
import java.util.Date


@Composable
fun FreeScreen (navController: NavController, runAds :() -> Unit) {
    val predictionList = remember { mutableStateOf<List<JsonObject>?>(null) }
    val isLoading = remember { mutableStateOf(true) }
    val error = remember { mutableStateOf<String?>(null) }

    val params = mapOf("action" to "get_events", "from" to getCurrentDate(), "to" to getCurrentDate(),"timezone" to "Africa/Nairobi", "APIkey" to stringResource(id = R.string.api_key))

    var isButtonClicked by remember { mutableStateOf(true) }

    LaunchedEffect(isButtonClicked) {
        if (isButtonClicked){
            try {
                val response = RetrofitClient.apiService.getFixtures(params)
                predictionList.value = response.map { it.asJsonObject }
            } catch (e: Exception) {
                error.value = e.message
            } finally {
                isLoading.value = false
            }
            isButtonClicked = false // Reset state after task
        }
    }

    when {
        isLoading.value -> {
            IndeterminateCircularIndicator()
        }
        error.value != null -> {
            NetworkError {
                isButtonClicked = true
                isLoading.value = true
                error.value = null
            }
        }

        predictionList.value != null -> {
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                items(items = predictionList.value!!) { prediction ->
                    when{
                       prediction.get("match_live").asString.equals("0") && !prediction.get("match_status").asString.equals("Finished") -> {
                            Tip(prediction, navController, runAds)
                       }
                    }
                }
            }
            AdmobBanner()
        }
    }
}
private fun getCurrentDate(): String {
    val dateFormat = SimpleDateFormat("yyyy-MM-dd")
    val currentDate = Date()
    return dateFormat.format(currentDate)
}