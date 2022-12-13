package com.thescore.interview.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.thescore.interview.data.Team
import com.thescore.interview.repositories.TeamRepo
import com.thescore.interview.repositories.TeamService
import com.thescore.interview.repositories.TeamRepoImpl
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainViewModel(teamRepo: TeamRepo, dispatcher: CoroutineDispatcher) : ViewModel() {
    private val viewModelState: MutableStateFlow<ViewModelState> =
        MutableStateFlow(ViewModelState(listOf<Team>(), true))
    val uiState =
        viewModelState.stateIn(viewModelScope, SharingStarted.Eagerly, viewModelState.value)

    init {
        viewModelScope.launch(dispatcher) {
            try {
                viewModelState.update {
                    it.copy(
                        teams = teamRepo.getTeams(),
                        isRefreshing = false,
                        errorState = null
                    )
                }
            } catch (e: TeamRepoImpl.NoInternetException) {
                viewModelState.update {
                    it.copy(errorState = e)
                }
                Log.i("perspolis", "no internet connection")
                e.printStackTrace()
            }
        }
    }

    companion object {
        fun createViewModel(
            teamRepo: TeamRepo,
            dispatcher: CoroutineDispatcher
        ): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return MainViewModel(teamRepo, dispatcher) as T
                }
            }
    }
}

data class ViewModelState(
    val teams: List<Team>,
    val isRefreshing: Boolean,
    val errorState: Exception? = null
)

