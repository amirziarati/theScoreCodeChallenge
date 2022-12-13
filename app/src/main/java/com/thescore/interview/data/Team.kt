package com.thescore.interview.data

import com.google.gson.annotations.SerializedName;


data class Team(
    @SerializedName("wins")
    val wins: Int?,
    @SerializedName("losses")
    val losses: Int?,
    @SerializedName("full_name")
    val fullName: String?,
    @SerializedName("id")
    val id: Int?
)