package com.juanfra.appubicaciones.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "fuentes")
data class Fuente(
    val lat: Double,
    val lng: Double,
    val visitada: Boolean,


){
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
}
