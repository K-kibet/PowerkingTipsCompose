package com.codesui.powerkingtips.models


data class DetailedPrediction (
    val league: String,
    val home: String,
    val away: String,
    val homeImage: String,
    val awayImage: String,
    val homePercent: String,
    val drawPercent: String,
    val awayPercent: String,
    val advice: String,
    val homeForm: String,
    val awayForm: String,
    val homeAtt: String,
    val awayAtt: String,
    val homeDef: String,
    val awayDef: String,
    val homePoss: String,
    val awayPoss: String,
    val homeH2H: String,
    val awayH2H: String,
    val homeTotal: String,
    val awayTotal: String
)