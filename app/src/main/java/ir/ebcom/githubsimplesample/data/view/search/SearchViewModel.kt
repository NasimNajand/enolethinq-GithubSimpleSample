package ir.ebcom.githubsimplesample.data.view.search

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.ebcom.githubsimplesample.data.response.SearchResponse
import ir.ebcom.githubsimplesample.data.network.Resource
import ir.ebcom.githubsimplesample.data.repository.SearchRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchRepository: SearchRepository
): ViewModel() {
    val _searchResponse: MutableLiveData<SearchResponse> = MutableLiveData()
    private val searchResponse: LiveData<SearchResponse>
        get() = _searchResponse

    fun search(q: String) = viewModelScope.launch {
        searchRepository.search(q).collect {
            handleResponse(it)
            Log.d("nacm", "search: " + it.body()!!.items[0].login)
        }
    }

    private fun handleResponse(response: Response<SearchResponse>) {
        Log.i("nacm", "handleResponse: isSuccessful: " + response.isSuccessful)
        if (response.isSuccessful)
            _searchResponse.value = response.body()
        else {
            Log.i("nacm", "handleResponse: is not ok cause: " + response.code())
            if (response.code() == 403)
                _searchResponse.value = null
        }
    }
}