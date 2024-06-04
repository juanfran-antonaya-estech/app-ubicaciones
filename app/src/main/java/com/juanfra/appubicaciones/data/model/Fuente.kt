package com.juanfra.appubicaciones.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "fuentes")
data class Fuente(
    val lat: Double,
    val lng: Double,
    var visitada: Boolean,
    var nombre: String,
    var temperatura: String,
    var funciona: Boolean,
    var boquillas: Int,
    var puntuacion: Double

){
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}
