package com.example.ejemplomvvm.contador.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.ejemplomvvm.contador.viewmodel.ContadorViewModel

@Composable
fun ControlCantidad(contadorViewModel: ContadorViewModel) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
    )
    {

        BotonContador(
            Icons.AutoMirrored.Filled.KeyboardArrowLeft,
            "Descontar",
            funcion = {contadorViewModel.descontar() }

        )

        ValorContador(contadorViewModel)

        BotonContador(
            Icons.AutoMirrored.Filled.KeyboardArrowRight,
            "Agregar",
            funcion = {contadorViewModel.agregar() }

        )

//Icons.AutoMirrored.Filled.KeyboardArrowLeft




    }
}

