package ir.ebcom.githubsimplesample.data.view.user

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import ir.ebcom.githubsimplesample.data.network.Resource
import ir.ebcom.githubsimplesample.data.response.SingleUser
import ir.ebcom.githubsimplesample.data.view.base.BaseFragment
import ir.ebcom.githubsimplesample.data.view.search.SearchViewModel
import ir.ebcom.githubsimplesample.databinding.FragmentUserBinding
import javax.inject.Inject

@AndroidEntryPoint
class UserFragment: BaseFragment<UserViewModel, FragmentUserBinding>() {
    private val TAG = "UserFragment"
    @Inject
    lateinit var userViewModel: UserViewModel
    private val username: UserFragmentArgs by navArgs()

    override fun getViewModel(): UserViewModel {
        return userViewModel
    }

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    )= FragmentUserBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        userViewModel.fetchUser(username.usernameArg)
        userViewModel.userResponse.observe(viewLifecycleOwner, {
            when (it){
                is Resource.Loading -> {
                    Log.d(TAG, "onViewCreated: loading ")
                    binding.progressbar.visibility = VISIBLE
                }
                is Resource.Success -> {
                    Log.d(TAG, "onViewCreated: success ")
                    with(binding){
                        progressbar.visibility = GONE
                        userNameTv.text = "name: " + it.value?.name
                        bioTv.text = "bio: " + it.value?.bio
                        emailTv.text = "email: " + it.value?.email
                        followersTv.text = "followers: " + it.value?.followers
                        followingTv.text = "following: " + it.value?.following
                        typeTv.text = "type: " + it.value?.type
                    }
                }
                is Resource.Failure -> {
                    binding.progressbar.visibility = GONE
                    binding.userCv.visibility = GONE
                    Toast.makeText(requireContext(), "error: " + it.errorCode, Toast.LENGTH_LONG).show()
                }
            }
        })
    }
}