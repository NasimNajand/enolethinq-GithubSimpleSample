package ir.ebcom.githubsimplesample.data.repository

import android.util.Log
import ir.ebcom.githubsimplesample.data.network.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import retrofit2.Response

abstract class BaseRepository {

    suspend fun <T> safeApiCallByFlow(
        apiCall: suspend () -> Response<T>
    ) = flow<Resource<T>> {
        emit(Resource.Loading)
            try {
                val response = apiCall.invoke()
                if (response.isSuccessful) {
                    emit(Resource.Success(response.body()))
                } else {
                    emit(
                        Resource.Failure(
                            false,
                            HttpException(response).code(),
                            HttpException(response).response()?.errorBody()
                        )
                    )
                }
            } catch (e: Exception) {
                emit(Resource.Failure(true, -1, null))
            }
    }.flowOn(Dispatchers.IO)

    suspend fun <T> safeApiCall(
        apiCall: suspend () -> T
    ) = flow {
        try {
            emit(apiCall.invoke())
        } catch (throwable: Throwable) {
            Log.d("nacm", "safeApiCall: " + throwable.message.toString())
            /*when (throwable){
                is HttpException -> {
                    emit(Resource.Failure(false, throwable.code(), throwable.response()?.errorBody()))
                }else -> {
                    emit(Resource.Failure(true, -1, null))
                }
            }*/
        }
    }
}