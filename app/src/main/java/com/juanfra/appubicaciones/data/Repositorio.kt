package com.juanfra.appubicaciones.data

import android.content.Context
import com.juanfra.appubicaciones.data.model.Fuente
import com.juanfra.appubicaciones.data.room.BaseDatos

class Repositorio(val context: Context) {

    val database = BaseDatos.getDB(context)
    val fuenteDao = database.fuenteDao()

    suspend fun addFuente(fuente: Fuente) = fuenteDao.addFuente(fuente)
    fun obtenerTodasFuentes() = fuenteDao.obtenerTodasFuentes()
    fun obtenerFuentesFuncionales() = fuenteDao.obtenerFuentesFuncionales()
    suspend fun actualizarFuente(fuente: Fuente) = fuenteDao.actualizarFuente(fuente)
    suspend fun borrarFuente(fuente: Fuente) = fuenteDao.borrarFuente(fuente)
    fun obtenerFuente(id: Int) = fuenteDao.obtenerFuente(id)

}