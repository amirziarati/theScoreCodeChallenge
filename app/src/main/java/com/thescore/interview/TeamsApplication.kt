package com.thescore.interview

import android.app.Application
import com.thescore.interview.repositories.TeamRepo
import com.thescore.interview.repositories.TeamRepoImpl
import com.thescore.interview.repositories.TeamService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class TeamsApplication : Application(), DiContainer {

    private val remoteService: TeamService by lazy {
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://raw.githubusercontent.com/scoremedia/nba-team-viewer/master/")
            .build()
            .create<TeamService>()
    }

    override val teamRepo: TeamRepo
        get() = TeamRepoImpl(remoteService)

    override val dispatcher: CoroutineDispatcher
        get() = Dispatchers.IO

}