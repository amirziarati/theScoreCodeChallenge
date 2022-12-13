package com.thescore.interview.ui

import com.thescore.interview.data.Team
import com.thescore.interview.repositories.TeamRepo
import com.thescore.interview.repositories.TeamRepoImpl
import com.thescore.interview.repositories.TeamService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito


class MainViewModelTest {

    private val repo: TeamRepo = Mockito.mock(TeamRepo::class.java)
    private val viewModel: MainViewModel by lazy { MainViewModel(repo, Dispatchers.IO) }

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun teardown() {
        Dispatchers.resetMain()
    }

    @Test
    fun theSameTeamsComingFromRemoteGoesToUI() = runTest {
        mockStuff()

        val emits = viewModel.uiState.take(2).toList()

        assert(emits[0].teams.isEmpty())
        assert(emits[1].teams.size == 6)
        assert(emits[1].teams[2].id == 3)
    }

    private suspend fun mockStuff() {
        val team1 = createDummyTeam(1)
        val team2 = createDummyTeam(2)
        val team3 = createDummyTeam(3)
        val team4 = createDummyTeam(4)
        val team5 = createDummyTeam(5)
        val team6 = createDummyTeam(5)
        Mockito.`when`(repo.getTeams())
            .thenReturn(listOf(team1, team2, team3, team4, team5, team6))
    }

    @Test
    fun stateChangesToRefreshingWhenRemoteCallHappens() = runTest {
        mockStuff()

        val emits = viewModel.uiState.take(2).toList()

        assert(emits[0].isRefreshing)
        assert(!emits[1].isRefreshing)
    }

    @Test
    fun testTheViewModelStateWhenNoInternetConnection() = runTest {
        val msg = "no internet"
        Mockito.`when`(repo.getTeams())
            .thenThrow(TeamRepoImpl.NoInternetException(msg))

        val emits = viewModel.uiState.take(2).toList()

        assert(emits[0].errorState == null)
        assert(emits[1].errorState is TeamRepoImpl.NoInternetException)
        assert(emits[1].errorState?.message.equals(msg))
    }


    private fun createDummyTeam(id: Int): Team {
        return Team(10000, 0, "perspolis", id)
    }
}