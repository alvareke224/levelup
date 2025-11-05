package com.example.ejemplomvvm

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.ejemplomvvm.ui.AppNavigation
import com.example.ejemplomvvm.ui.theme.EjemploMvvmTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EjemploMvvmTheme {
                AppNavigation()
            }
        }
    }
}
