package ir.ebcom.githubsimplesample.data.repository

import ir.ebcom.githubsimplesample.data.network.SearchApi
import javax.inject.Inject

class SearchRepository @Inject constructor(
    private val api: SearchApi
): BaseRepository() {

    suspend fun search(
        query: String
    ) = safeApiCallByFlow {
        api.searchUserByQuery(query)
    }
    suspend fun fetchSingleUser(
        name: String
    ) = safeApiCallByFlow {
        api.getSingleUser(name)
    }
}