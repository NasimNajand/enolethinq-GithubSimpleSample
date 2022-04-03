package ir.ebcom.githubsimplesample.data.view.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
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
            binding.userNameTv.text = "name: " + it.name
            binding.bioTv.text = "bio: " + it.bio
            binding.emailTv.text = "email: " + it.email
            binding.followersTv.text = "followers: " + it.followers
            binding.followingTv.text = "following: " + it.following
            binding.typeTv.text = "type: " + it.type
        }
    }
}