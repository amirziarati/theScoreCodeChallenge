package com.thescore.interview.repositories

import com.thescore.interview.data.Team

interface TeamRepo {
    suspend fun getTeams(): List<Team>
}