package com.thescore.interview.repositories

import com.thescore.interview.data.Team

class TeamRepoImpl(val teamService: TeamService): TeamRepo {

    override suspend fun getTeams(): List<Team> {
        if(true) {
            return teamService.getTeams()
        } else {
            throw NoInternetException("No internet")
        }
    }

    class NoInternetException(msg: String): java.lang.RuntimeException(msg)
}