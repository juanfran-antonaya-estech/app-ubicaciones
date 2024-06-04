package com.juanfra.appubicaciones.data

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import com.juanfra.appubicaciones.data.model.Fuente
import kotlinx.coroutines.launch

class MapViewModel(val context: Context) : ViewModel() {

    val repo = Repositorio(context)

    val actualLatLng = MutableLiveData<LatLng>()
    val actualFuente = MutableLiveData<Fuente?>()


    fun addFuente(fuente: Fuente) {
        viewModelScope.launch {
            repo.addFuente(fuente)
        }
    }

    fun obtenerTodasFuentes() = repo.obtenerTodasFuentes()
    fun obtenerFuentesFuncionales() = repo.obtenerFuentesFuncionales()
    fun actualizarFuente(fuente : Fuente) {
        viewModelScope.launch {
            repo.actualizarFuente(fuente)
        }
    }
    fun borrarFuente(fuente : Fuente) {
        viewModelScope.launch {
            repo.borrarFuente(fuente)
        }
    }
    fun obtenerFuentePorId(id : Int){
        viewModelScope.launch {
            val fuentes = repo.obtenerFuente(id)
            actualFuente.postValue(fuentes.value?.first())
        }
    }


    class MyViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return modelClass.getConstructor(Context::class.java).newInstance(context)
        }
    }
}