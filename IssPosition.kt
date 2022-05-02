package com.example.ourfinalfrontier
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.decodeFromString

class IssPosition(message: String, latitude: Double, longitude: Double, timestamp: Int){


    var message: String = message
        get() {
            return field
        }
        set(value) {
            field = value
        }
    var latitude = latitude
        get() {
            return field
        }
        set(value) {
            field = value
        }
    var longitude = longitude
        get() {
            return field
        }
        set(value) {
            field = value
        }
    var timestamp: Int = timestamp
        get() {
            return field
        }
        set(value) {
            field = value
        }


}