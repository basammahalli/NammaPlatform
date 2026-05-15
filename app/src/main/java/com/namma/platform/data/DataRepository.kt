package com.namma.platform.data

import android.content.Context
import com.google.gson.Gson
import java.io.InputStreamReader

object DataRepository {
    private var appData: AppData? = null
    private val dynamicTrains = mutableListOf<Train>()

    fun loadData(context: Context) {
        if (appData != null) return
        try {
            val inputStream = context.assets.open("data.json")
            val reader = InputStreamReader(inputStream)
            appData = Gson().fromJson(reader, AppData::class.java)
            dynamicTrains.addAll(appData?.trains ?: emptyList())
            reader.close()
            inputStream.close()
        } catch (e: Exception) {
            e.printStackTrace()
            appData = AppData(emptyList(), emptyList())
        }
    }

    fun getStations(): List<Station> {
        return appData?.stations ?: emptyList()
    }

    fun getTrainsForStation(stationId: String): List<Train> {
        return dynamicTrains.filter { it.stationId == stationId }
    }

    fun addTrain(train: Train) {
        dynamicTrains.add(train)
    }
}
