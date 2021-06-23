package ru.androidschool.intensiv.data

import com.google.gson.annotations.SerializedName

data class TvShowsResponse(
    @SerializedName("page")
    var page: Int = 1,
    @SerializedName("results")
    var contentList: List<TvShow> = listOf<TvShow>(),
    @SerializedName("total_pages")
    var totalPages: Int = 0,
    @SerializedName("total_results")
    var totalResults: Int = 0
)
