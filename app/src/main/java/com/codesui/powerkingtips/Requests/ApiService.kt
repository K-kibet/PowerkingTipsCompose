package com.codesui.footballfixtures.Requests

import com.google.gson.JsonArray
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface ApiService {
    @GET("fixtures")
    suspend fun getFixtures(
        @QueryMap params: Map<String, String>
    ): JsonArray

    @GET("predictions")
    suspend fun getPrediction(
        @QueryMap params: Map<String, String>
    ): JsonArray
}