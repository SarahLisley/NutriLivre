package com.example.myapplication.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.UserPreferencesRepository
import com.example.myapplication.model.AppSettings
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import androidx.compose.runtime.mutableStateListOf
import com.example.myapplication.model.DadosMockados
import com.example.myapplication.model.Receita

class SettingsViewModel(
    private val repository: UserPreferencesRepository
) : ViewModel() {

    // Fluxos individuais para cada preferência
    val isDarkModeEnabled: StateFlow<Boolean> =
        repository.isDarkModeEnabled
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), false)

    val areNotificationsEnabled: StateFlow<Boolean> =
        repository.areNotificationsEnabled
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), true)

    val areAnimationsEnabled: StateFlow<Boolean> =
        repository.areAnimationsEnabled
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), true)

    val primaryColor: StateFlow<String?> =
        repository.primaryColor.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), null)

    val secondaryColor: StateFlow<String?> =
        repository.secondaryColor.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), null)

    // Fluxo combinado para UI
    val uiState: StateFlow<AppSettings> = combine(
        isDarkModeEnabled,
        areNotificationsEnabled,
        areAnimationsEnabled,
        primaryColor,
        secondaryColor
    ) { dark, notif, anim, primary, secondary ->
        AppSettings(
            darkModeEnabled      = dark,
            notificationsEnabled = notif,
            animationsEnabled    = anim,
            primaryColor         = primary,
            secondaryColor       = secondary
        )
    }
        .stateIn(viewModelScope, SharingStarted.Eagerly, AppSettings())

    // Métodos de escrita
    fun toggleDarkMode(enabled: Boolean) {
        viewModelScope.launch {
            repository.setDarkModeEnabled(enabled)
        }
    }

    fun toggleNotificationsEnabled(enabled: Boolean) {
        viewModelScope.launch {
            repository.setNotificationsEnabled(enabled)
        }
    }

    fun toggleAnimationsEnabled(enabled: Boolean) {
        viewModelScope.launch {
            repository.setAnimationsEnabled(enabled)
        }
    }

    fun setPrimaryColor(color: String) {
        viewModelScope.launch {
            repository.setPrimaryColor(color)
        }
    }

    fun setSecondaryColor(color: String) {
        viewModelScope.launch {
            repository.setSecondaryColor(color)
        }
    }
}

// Factory para injeção manual (sem Hilt)
class SettingsViewModelFactory(
    private val repository: UserPreferencesRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SettingsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SettingsViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

class ReceitasViewModel : ViewModel() {
    // Lista mutável de receitas
    val receitas = mutableStateListOf<Receita>().apply {
        addAll(DadosMockados.listaDeReceitas)
    }

    fun toggleFavorito(receitaId: Int) {
        val idx = receitas.indexOfFirst { it.id == receitaId }
        if (idx >= 0) {
            val atual = receitas[idx]
            receitas[idx] = atual.copy(isFavorita = !atual.isFavorita)
        }
    }

    fun editarNome(receitaId: Int, novoNome: String) {
        val idx = receitas.indexOfFirst { it.id == receitaId }
        if (idx >= 0) {
            val atual = receitas[idx]
            receitas[idx] = atual.copy(nome = novoNome)
        }
    }

    fun adicionarReceita(nome: String) {
        val novoId = (receitas.maxOfOrNull { it.id } ?: 0) + 1
        receitas.add(0, DadosMockados.listaDeReceitas.first().copy(
            id = novoId,
            nome = nome,
            descricaoCurta = "Novo item adicionado",
            imagemUrl = ""
        ))
    }

    fun getFavoritos(): List<Receita> = receitas.filter { it.isFavorita }
}
