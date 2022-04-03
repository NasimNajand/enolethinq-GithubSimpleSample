package ir.ebcom.githubsimplesample.data.repository

import android.util.Log
import ir.ebcom.githubsimplesample.data.network.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException

abstract class BaseRepository {

    suspend fun <T> safeApiCallByFlow(
        apiCall: suspend () -> T
    ) : Flow<Resource<T>>{
        return flow {
            try {
                emit(Resource.Loading(Resource.Status.LOADING))
                emit(Resource.Success(apiCall.invoke()))
            }catch (throwable: Throwable){
                when(throwable){
                    is HttpException -> {
                        emit(Resource.Failure(false, throwable.code(), throwable.response()?.errorBody()))
                    } else -> {
                    emit(Resource.Failure(true, -1, null))
                }
                }
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun <T> safeApiCall(
        apiCall: suspend () -> T
    ) = flow {
        try {
            emit(apiCall.invoke())
        } catch (throwable: Throwable){
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