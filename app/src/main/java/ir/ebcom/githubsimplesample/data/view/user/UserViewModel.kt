package ir.ebcom.githubsimplesample.data.view.user

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.ebcom.githubsimplesample.data.repository.SearchRepository
import ir.ebcom.githubsimplesample.data.response.SingleUser
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val repository: SearchRepository
): ViewModel() {
    val _userResponse: MutableLiveData<SingleUser> = MutableLiveData()
    private val userResponse: LiveData<SingleUser>
        get() = _userResponse

    fun fetchUser(name: String) = viewModelScope.launch {
        repository.fetchSingleUser(name).collect {
            handleResponse(it)
        }
    }
    private fun handleResponse(response: Response<SingleUser>){
        if (response.isSuccessful)
            _userResponse.value = response.body()
        else{
            _userResponse.value = null
        }
    }
}