package com.example.ejemplomvvm.contador.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.ejemplomvvm.contador.model.ContadorModel

class ContadorViewModel(context: Context): ViewModel() {

    private val _contadorModel= ContadorModel(
        context
    )
    //Y OTRO MENOS ACAAAAAAAAAAAAAA
    //private val _cuenta= MutableLiveData(-_contadorModel.obtenerValor())

    private val _cuenta= MutableLiveData(_contadorModel.obtenerValor())
    val cuenta: LiveData<Int> =_cuenta


    fun agregar(){
        //Y ACA TAMBIEN!!!!!!!!!
        //_cuenta.value = -_contadorModel.actualizarValor(aumento = 1)
        _cuenta.value = _contadorModel.actualizarValor(aumento = 1)
    }

    fun descontar(){
        _cuenta.value = _contadorModel.actualizarValor(aumento = -1)
    }

}