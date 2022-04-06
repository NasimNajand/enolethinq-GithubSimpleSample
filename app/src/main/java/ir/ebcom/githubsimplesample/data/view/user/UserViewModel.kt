package ir.ebcom.githubsimplesample.data.view.user

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.ebcom.githubsimplesample.data.network.Resource
import ir.ebcom.githubsimplesample.data.repository.SearchRepository
import ir.ebcom.githubsimplesample.data.response.SingleUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val repository: SearchRepository
): ViewModel() {
    private val _userResponse: MutableLiveData<Resource<SingleUser>> = MutableLiveData()
    val userResponse: LiveData<Resource<SingleUser>> = _userResponse

    fun fetchUser(name: String) = viewModelScope.launch {
        repository.fetchSingleUser(name).collect {
            _userResponse.postValue(it)
        }
    }
}