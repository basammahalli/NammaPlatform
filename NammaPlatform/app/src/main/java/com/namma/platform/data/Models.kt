package com.namma.platform.data

data class AppData(
    val stations: List<Station>,
    val trains: List<Train>
)

data class Station(
    val id: String,
    val nameKannada: String,
    val nameEnglish: String,
    val zone: String
)

data class Train(
    val id: String,
    val stationId: String,
    val nameKannada: String,
    val nameEnglish: String,
    val departureTime: String,
    val platform: String,
    val coaches: List<Coach>
)

data class Coach(
    val type: String
)
