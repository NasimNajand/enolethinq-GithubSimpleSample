package ir.ebcom.githubsimplesample.data.network

import okhttp3.ResponseBody

sealed class Resource<out T> {
    enum class Status {
        SUCCESS,
        ERROR,
        LOADING
    }
    data class Success<out T>(val value: T) : Resource<T>()
    data class Failure(
        val isNetworkError: Boolean,
        val errorCode: Int,
        val errorBody: ResponseBody?
    ) : Resource<Nothing>()
    data class Loading(val status: Status): Resource<Nothing>()
}