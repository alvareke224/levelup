package com.example.ejemplomvvm.contador.view

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.example.ejemplomvvm.contador.viewmodel.ContadorViewModel

@Composable
fun ValorContador(contadorViewModel: ContadorViewModel) {
    var valorLocal by rememberSaveable { mutableStateOf(0) }

    contadorViewModel.cuenta.observe(LocalLifecycleOwner.current,{
        it->
        valorLocal=it
    })



    Text(text = valorLocal.toString(), fontSize = 48.sp,  fontWeight = FontWeight.SemiBold,
        color = MaterialTheme.colorScheme.primary)
}