package com.thescore.interview

import com.thescore.interview.repositories.TeamRepo
import kotlinx.coroutines.CoroutineDispatcher

interface DiContainer {
    val teamRepo: TeamRepo
    val dispatcher: CoroutineDispatcher
}