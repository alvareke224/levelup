package com.example.ejemplomvvm.contador.view

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ejemplomvvm.contador.viewmodel.ContadorViewModel

@SuppressLint("ViewModelConstructorInComposable")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContadorScreen () {

    val context= LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text="Prueba MVVM") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary
                )
            )
        }
    )
    {paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            ControlCantidad(contadorViewModel = ContadorViewModel(context))

            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.padding(20.dp)
                    .fillMaxWidth()
            ){
                Text(text = "Controles con Ciclo", fontSize = 30.sp, color = MaterialTheme.colorScheme.primary )
            }

//          repeat(5){
//              ControlCantidad(contadorViewModel = ContadorViewModel(context))
//          }

        }

    }

}