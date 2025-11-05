package com.example.ejemplomvvm.contador.model

import android.content.Context
import android.content.SharedPreferences
import androidx.datastore.preferences.core.Preferences

class ContadorModel (val context: Context){

    private val _sharedPreferences: SharedPreferences=(
            context.getSharedPreferences("contadorKey", Context.MODE_PRIVATE))


    fun actualizarValor(aumento:Int):Int{
        //HABIA UN - ACAAAAA!!!!!
        //val valorActual=-_sharedPreferences.getInt("contadorKey",0 )
        val valorActual=_sharedPreferences.getInt("contadorKey",0 )

        println("ACTUAL: ${valorActual}")
        println("AUMENTO ${aumento}")

        val valorActualizado=valorActual+aumento

        println("ACTUALIZADO ${valorActualizado}")

        val editor=_sharedPreferences.edit()
        editor.putInt("contadorKey",valorActualizado)
        editor.apply()
        return valorActualizado
    }

    fun obtenerValor():Int{
        return _sharedPreferences.getInt("contadorKey",0)
    }

}