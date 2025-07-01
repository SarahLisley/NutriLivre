package com.example.myapplication.ui.screens

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.ShoppingListRepository
import com.example.myapplication.model.Receita
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ShoppingListViewModel(
    private val repository: ShoppingListRepository,
    private val context: Context
) : ViewModel() {
    private val _listaCompras = MutableStateFlow<List<Receita>>(emptyList())
    val listaCompras: StateFlow<List<Receita>> = _listaCompras.asStateFlow()

    init {
        viewModelScope.launch {
            repository.getShoppingList().collect { lista ->
                _listaCompras.value = lista
            }
        }
    }

    fun adicionarReceita(receita: Receita) {
        viewModelScope.launch {
            val novaLista = listOf(receita) + _listaCompras.value
            repository.saveShoppingList(novaLista)
        }
    }

    fun removerReceita(receita: Receita) {
        viewModelScope.launch {
            val novaLista = _listaCompras.value.toMutableList().apply { remove(receita) }
            repository.saveShoppingList(novaLista)
        }
    }

    fun editarReceita(receitaEditada: Receita) {
        viewModelScope.launch {
            val novaLista = _listaCompras.value.map {
                if (it.id == receitaEditada.id) receitaEditada else it
            }
            repository.saveShoppingList(novaLista)
        }
    }

    fun toggleFavorito(receitaId: Int) {
        viewModelScope.launch {
            val novaLista = _listaCompras.value.map {
                if (it.id == receitaId) it.copy(isFavorita = !it.isFavorita) else it
            }
            repository.saveShoppingList(novaLista)
        }
    }
}

class ShoppingListViewModelFactory(
    private val repository: ShoppingListRepository,
    private val context: Context
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ShoppingListViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ShoppingListViewModel(repository, context) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
} 