package ir.ebcom.githubsimplesample.data.repository

import ir.ebcom.githubsimplesample.data.network.SearchApi
import javax.inject.Inject

class SearchRepository @Inject constructor(
    private val api: SearchApi
): BaseRepository() {

    suspend fun search(
        query: String
    ) = safeApiCall {
        api.searchUserByQuery(query)
    }
    suspend fun fetchSingleUser(
        name: String
    ) = safeApiCall {
        api.getSingleUser(name)
    }
}