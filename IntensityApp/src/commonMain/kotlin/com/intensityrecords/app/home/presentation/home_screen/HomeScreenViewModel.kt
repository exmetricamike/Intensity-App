package com.intensityrecords.app.home.presentation.home_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.intensityrecords.app.core.data.SessionProvider
import com.intensityrecords.app.core.domain.onError
import com.intensityrecords.app.core.domain.onSuccess
import com.intensityrecords.app.home.domain.HomeRepository
import com.intensityrecords.app.home.domain.UiConfig
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeScreenViewModel(
    private val homeRepository: HomeRepository,
    private val sessionProvider: SessionProvider
) : ViewModel() {

    private val _state = MutableStateFlow(HomeState(isLoading = true))
    val state: StateFlow<HomeState> = _state.asStateFlow()

    init {
        observeAuth()
    }

    private fun observeAuth() {

        viewModelScope.launch {

            sessionProvider.authId.collect { id ->

                if (id != null) {
                    loadHomeItems(id)
                }

            }

        }

    }

    private fun loadHomeItems(id: String) {

        _state.update { it.copy(isLoading = true) }

        viewModelScope.launch {

            val result = homeRepository.getHome(id)

            println("AUTH ID :::: ${sessionProvider.getAuthId()}")

            result.onSuccess { data ->
                _state.update {
                    it.copy(
                        isLoading = false,
                        items = data
                    )
                }
            }

            result.onError { error ->
                _state.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = "Failed to load data \n $error"
                    )
                }
            }

        }

    }

}
