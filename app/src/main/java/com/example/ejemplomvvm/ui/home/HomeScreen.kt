
package com.example.ejemplomvvm.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.ejemplomvvm.R
import com.example.ejemplomvvm.data.User

@Composable
fun HomeScreen(
    currentUser: User?,
    onExploreCatalog: () -> Unit,
    onLogin: () -> Unit,
    onNavigateToProfile: () -> Unit,
    onNavigateToPromotions: () -> Unit,
    onNavigateToLocation: () -> Unit
) {
    Scaffold {
        Column(
            modifier = Modifier.fillMaxSize().padding(it),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_launcher_foreground),
                contentDescription = stringResource(id = R.string.app_name),
                modifier = Modifier.size(128.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = stringResource(id = R.string.app_name),
                style = MaterialTheme.typography.headlineLarge,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Tu tienda de videojuegos retro",
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(32.dp))
            if (currentUser == null) {
                Button(onClick = onLogin) {
                    Text("Login")
                }
            } else {
                Button(onClick = onNavigateToProfile) {
                    Text("Mi Perfil")
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = onExploreCatalog) {
                Text("Explorar catálogo")
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = onNavigateToPromotions) {
                Text("Promociones")
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = onNavigateToLocation) {
                Text("Visítanos")
            }
        }
    }
}
