package ir.ebcom.githubsimplesample.data.network

import ir.ebcom.githubsimplesample.data.response.SearchResponse
import ir.ebcom.githubsimplesample.data.response.SingleUser
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface SearchApi {

    @GET("search/users")
    suspend fun searchUserByQuery(@Query("q") q: String) : SearchResponse

    @GET("users/{username}")
    suspend fun getSingleUser(@Path("username") userName: String): SingleUser
}