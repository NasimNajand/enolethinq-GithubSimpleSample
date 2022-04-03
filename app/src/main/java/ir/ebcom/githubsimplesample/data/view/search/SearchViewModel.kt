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
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchRepository: SearchRepository
): ViewModel() {
    val _searchResponse: MutableLiveData<Resource<SearchResponse>> = MutableLiveData()
    private val searchResponse: LiveData<Resource<SearchResponse>>
        get() = _searchResponse

    @FlowPreview
    fun search(q: String) = viewModelScope.launch {
        _searchResponse.postValue(Resource.Loading)
        searchRepository.search(q)
            .debounce(300)
            .distinctUntilChanged()
            .collect {
                _searchResponse.value = it
//            handleResponse(it)
        }
    }

    private fun handleResponse(response: Resource<Response<SearchResponse>>) {

    }

    /*private fun handleResponse(response: Response<SearchResponse>) {
        Log.i("nacm", "handleResponse: isSuccessful: " + response.isSuccessful)
        if (response.isSuccessful)
            _searchResponse.value = Resource.Success(response.body())
        else {
            Log.i("nacm", "handleResponse: is not ok cause: " + response.code())
            if (response.code() == 403)
                _searchResponse.value = null
        }
    }*/
}