package ir.ebcom.githubsimplesample.data.view.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import ir.ebcom.githubsimplesample.data.network.Resource
import ir.ebcom.githubsimplesample.data.view.base.BaseFragment
import ir.ebcom.githubsimplesample.data.view.search.SearchViewModel
import ir.ebcom.githubsimplesample.databinding.FragmentUserBinding
import javax.inject.Inject

@AndroidEntryPoint
class UserFragment: BaseFragment<UserViewModel, FragmentUserBinding>() {

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
        userViewModel._userResponse.observe(viewLifecycleOwner){
            when (it){
                is Resource.Loading -> {
                    binding.progressbar.visibility = VISIBLE
                }
                is Resource.Success -> {
                    binding.progressbar.visibility = GONE
                    binding.userNameTv.text = "name: " + it.value.name
                    binding.bioTv.text = "bio: " + it.value.bio
                    binding.emailTv.text = "email: " + it.value.email
                    binding.followersTv.text = "followers: " + it.value.followers
                    binding.followingTv.text = "following: " + it.value.following
                    binding.typeTv.text = "type: " + it.value.type
                }
                is Resource.Failure -> {
                    binding.progressbar.visibility = GONE
                    binding.userNameTv.text = "name: -"
                    binding.bioTv.text = "bio: -"
                    binding.emailTv.text = "email: -"
                    binding.followersTv.text = "followers: -"
                    binding.followingTv.text = "following: -"
                    binding.typeTv.text = "type: -"
                    Toast.makeText(requireContext(), "error: " + it.errorCode, Toast.LENGTH_LONG).show()
                }
            }

        }
    }
}