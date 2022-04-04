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
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val repository: SearchRepository
): ViewModel() {
    val _userResponse: MutableLiveData<Resource<SingleUser>> = MutableLiveData()
    private val userResponse: LiveData<Resource<SingleUser>>
        get() = _userResponse

    fun fetchUser(name: String) = viewModelScope.launch {
        _userResponse.postValue(Resource.Loading)
        repository.fetchSingleUser(name).collect {
            _userResponse.value = it
        }
    }

    override fun onCleared() {
        super.onCleared()
        Log.i("UserVM", "onCleared: called")
    }
}