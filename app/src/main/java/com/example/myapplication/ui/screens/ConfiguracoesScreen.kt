package com.example.myapplication.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.myapplication.model.DadosMockados
import com.example.myapplication.ui.components.BottomNavigationBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConfiguracoesScreen(navController: NavHostController) {
    var modoEscuroAtivado by remember { mutableStateOf(false) }
    var notificacoesAtivadas by remember { mutableStateOf(true) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Configurações") },
                actions = {
                    IconButton(onClick = { }) {
                        Icon(Icons.Filled.MoreVert, contentDescription = "Menu")
                    }
                }
            )
        },
        bottomBar = { BottomNavigationBar(navController = navController) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
                .fillMaxSize()
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Modo Escuro", style = MaterialTheme.typography.bodyLarge)
                Switch(
                    checked = modoEscuroAtivado,
                    onCheckedChange = { modoEscuroAtivado = it }
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Notificações", style = MaterialTheme.typography.bodyLarge)
                Switch(
                    checked = notificacoesAtivadas,
                    onCheckedChange = { notificacoesAtivadas = it }
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = { DadosMockados.listaDeFavoritosMock.clear() },
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text("Limpar Favoritos")
            }
        }
    }
}
