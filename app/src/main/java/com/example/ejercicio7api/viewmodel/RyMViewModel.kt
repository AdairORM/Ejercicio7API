package com.example.ejercicio7api.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ejercicio7api.model.Character
import com.example.ejercicio7api.network.RyMApi
import kotlinx.coroutines.launch

sealed class RyMUiState {
    object Loading : RyMUiState()
    data class Success(val characters: List<Character>) : RyMUiState()
    object Error : RyMUiState()
}

class RyMViewModel: ViewModel(){
    var ryMUiState: RyMUiState by mutableStateOf(RyMUiState.Loading)
        private set

    init {
        getCharacters()
    }

    private fun getCharacters() {
        viewModelScope.launch {
            try {
                val characters = RyMApi.retrofitService.getCharacters()
                ryMUiState = RyMUiState.Success(characters)
            } catch (e: Exception) {
                ryMUiState = RyMUiState.Error
            }
        }
    }
}
