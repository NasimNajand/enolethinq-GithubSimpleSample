package ir.ebcom.githubsimplesample.data.response

import com.google.gson.annotations.SerializedName

data class SearchResponse(
    val totalCount: Int,
    @SerializedName("incomplete_results")
    val incompleteResults: Boolean,
    val items: ArrayList<Item>
)
