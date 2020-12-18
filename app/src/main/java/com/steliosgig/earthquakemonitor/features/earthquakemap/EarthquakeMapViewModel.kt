package com.steliosgig.earthquakemonitor.features.earthquakemap

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.steliosgig.earthquakemonitor.internal.model.EarthquakeModel
import com.steliosgig.earthquakemonitor.internal.network.EarthquakeApi
import kotlinx.coroutines.launch
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

const val POSITION_YEAR = 0
const val POSITION_MONTH = 1
const val POSITION_DAY = 2
const val POSITION_HOUR = 3
const val POSITION_MINUTE = 4
const val POSITION_LATITUDE = 6
const val POSITION_LONGITUDE = 7
const val POSITION_DEPTH = 8
const val POSITION_MAGNITUDE = 9

class EarthquakeMapViewModel : ViewModel() {

    private val _response = MutableLiveData<List<EarthquakeModel>>()
    val responseGetEarthquakes: LiveData<List<EarthquakeModel>>
        get() = _response

    init {
        getEarthquakes()
    }

    private fun getEarthquakes() {
        viewModelScope.launch {
            try {
                val phpResponse = EarthquakeApi.retrofitService.getEarthquakes()
                _response.value = transformResponseToList(phpResponse)
            } catch (e: Exception) {
                //_response.value = "Failure: ${e.message}"
            }
        }
    }

    private fun transformResponseToList(response: String): List<EarthquakeModel> {
        val allEarthquakes = mutableListOf<EarthquakeModel>()
        val earthquakeEntries = response.lines()
        earthquakeEntries.forEach {
            if (it.count() == 58) {
                val entry = it.trim().split("\\s+".toRegex())
                val stringDate =
                    "${entry[POSITION_YEAR]}/${entry[POSITION_MONTH]}/${entry[POSITION_DAY]}-${entry[POSITION_HOUR]}:${entry[POSITION_MINUTE]}"
                val format = SimpleDateFormat("yyyy/MMM/dd-HH:mm", Locale.getDefault())
                var date = Date()
                try {
                    date = format.parse(stringDate)
                } catch (e: ParseException) {
                }
                allEarthquakes.add(
                    EarthquakeModel(
                        date,
                        entry[POSITION_LATITUDE].toDouble(),
                        entry[POSITION_LONGITUDE].toDouble(),
                        entry[POSITION_DEPTH].toInt(),
                        entry[POSITION_MAGNITUDE].toDouble()
                    )
                )
            }
        }
        return allEarthquakes
    }
}