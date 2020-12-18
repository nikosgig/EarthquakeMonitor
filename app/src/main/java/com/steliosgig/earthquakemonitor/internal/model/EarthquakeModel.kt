package com.steliosgig.earthquakemonitor.internal.model

import java.util.*

data class EarthquakeModel (
    val date: Date,
    val latitude: Double,
    val longitude: Double,
    val depth: Int,
    val magnitude: Double
)