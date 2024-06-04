package com.juanfra.appubicaciones.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.juanfra.appubicaciones.data.model.Fuente
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.internal.synchronized

@Database(entities = [Fuente::class], version = 1)
abstract class BaseDatos : RoomDatabase() {
    abstract fun fuenteDao(): FuenteDao
    companion object {
        const val DB_NAME = "fuente_db"
        @Volatile
        private var INSTANCE : BaseDatos? = null
        @OptIn(InternalCoroutinesApi::class)
        fun getDB(context: Context) : BaseDatos {
            val tempInstance = INSTANCE
            if (tempInstance != null){
                return tempInstance
            }
            synchronized(BaseDatos::class) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    BaseDatos::class.java,
                    DB_NAME
                ).build()

                INSTANCE = instance
                return instance
            }
        }
    }
}