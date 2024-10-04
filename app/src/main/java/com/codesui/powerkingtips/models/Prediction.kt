package com.codesui.powerkingtips.models

data class Prediction(
    val id: String,
    val homeTeam: String,
    val awayTeam:String,
    val homeTeamIcon: String,
    val awayTeamIcon: String,
    val date: String,
    val time: String
)
