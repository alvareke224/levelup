package com.example.ejemplomvvm.contador.view

import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector

@Composable

fun BotonContador(icon: ImageVector, content:String, funcion:()->Unit) {
    FilledIconButton(onClick = funcion) {
        Icon(imageVector = icon,
            contentDescription = content)
    }
}