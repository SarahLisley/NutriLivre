package com.example.myapplication.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.myapplication.data.ShoppingListRepository
import com.example.myapplication.model.Receita

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShoppingListScreen() {
    val context = LocalContext.current
    val repository = remember { ShoppingListRepository(context) }
    val viewModel: ShoppingListViewModel = androidx.lifecycle.viewmodel.compose.viewModel(
        factory = ShoppingListViewModelFactory(repository, context)
    )
    val listaCompras by viewModel.listaCompras.collectAsState()

    var showDialog by remember { mutableStateOf(false) }
    var novoNome by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Lista de Compras") })
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { showDialog = true }) {
                Icon(Icons.Filled.Add, contentDescription = "Adicionar")
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            LazyColumn(
                modifier = Modifier.weight(1f).fillMaxWidth(),
                contentPadding = PaddingValues(8.dp)
            ) {
                items(listaCompras, key = { it.id }) { receita ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        elevation = CardDefaults.cardElevation(2.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(receita.nome)
                            Row {
                                IconButton(onClick = { viewModel.removerReceita(receita) }) {
                                    Icon(Icons.Filled.Delete, contentDescription = "Remover")
                                }
                            }
                        }
                    }
                }
            }
        }
        if (showDialog) {
            AlertDialog(
                onDismissRequest = { showDialog = false },
                title = { Text("Adicionar item") },
                text = {
                    OutlinedTextField(
                        value = novoNome,
                        onValueChange = { novoNome = it },
                        label = { Text("Nome do item") }
                    )
                },
                confirmButton = {
                    Button(onClick = {
                        if (novoNome.isNotBlank()) {
                            val novoId = (listaCompras.maxOfOrNull { it.id } ?: 0) + 1
                            val novaReceita = Receita(
                                id = novoId,
                                nome = novoNome,
                                descricaoCurta = "",
                                imagemUrl = "",
                                ingredientes = emptyList(),
                                modoPreparo = emptyList(),
                                tempoPreparo = "",
                                porcoes = 1
                            )
                            viewModel.adicionarReceita(novaReceita)
                            novoNome = ""
                            showDialog = false
                        }
                    }) { Text("Adicionar") }
                },
                dismissButton = {
                    Button(onClick = { showDialog = false }) { Text("Cancelar") }
                }
            )
        }
    }
} 