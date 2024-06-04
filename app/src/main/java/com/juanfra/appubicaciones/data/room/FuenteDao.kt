package com.juanfra.appubicaciones.data.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.juanfra.appubicaciones.data.model.Fuente

@Dao
interface FuenteDao {

    @Insert
    suspend fun addFuente(fuente: Fuente)

    @Query("SELECT * FROM fuentes")
    fun obtenerTodasFuentes() : LiveData<List<Fuente>>

    @Query("SELECT * FROM fuentes WHERE funciona = true")
    fun obtenerFuentesFuncionales() : LiveData<List<Fuente>>

    @Update
    suspend fun actualizarFuente(fuente: Fuente)

    @Delete
    suspend fun borrarFuente(fuente: Fuente)

    @Query("SELECT * FROM fuentes WHERE id = :id")
    fun obtenerFuente(id: Int) : LiveData<List<Fuente>>

}