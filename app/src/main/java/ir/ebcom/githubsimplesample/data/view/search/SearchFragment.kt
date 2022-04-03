package ir.ebcom.githubsimplesample.data.view.search

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import ir.ebcom.githubsimplesample.data.network.Resource
import ir.ebcom.githubsimplesample.data.view.adapter.ResultAdapter
import ir.ebcom.githubsimplesample.data.view.base.BaseFragment
import ir.ebcom.githubsimplesample.databinding.FragmentSearchBinding
import javax.inject.Inject

@AndroidEntryPoint
class SearchFragment: BaseFragment<SearchViewModel, FragmentSearchBinding>()
//    (FragmentSearchBinding::inflate)
{
    @Inject
    lateinit var searchViewModel: SearchViewModel
    override fun getViewModel(): SearchViewModel {
        return searchViewModel
    }

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentSearchBinding.inflate(inflater, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.searchEt.doOnTextChanged { text, _, _, _ ->
            if(text!!.length > 2)
                searchViewModel.search(text.toString())
            else
                binding.searchRv.visibility = GONE
        }
        searchViewModel._searchResponse.observe(viewLifecycleOwner){
            if (it != null){
                when(it){
                    is Resource.Loading -> {
                        binding.progressbar.visibility = VISIBLE
                        binding.searchRv.visibility = GONE
                    }
                    is Resource.Success -> {
                        Log.i("nacm", "onViewCreated: item size: " + it.value.items.size)
                        binding.progressbar.visibility = GONE
                        val  adapter = ResultAdapter(it.value)
                        binding.searchRv.apply {
                            this.visibility = VISIBLE
                            this.adapter = adapter
                            this.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                        }
                        val listener = object: ResultAdapter.OnSelectedListener{
                            override fun setOnSelectedListener(name: String) {
                                Log.i("nacm", "setOnSelectedListener: $name")
                                requireView().findNavController().navigate(SearchFragmentDirections.actionNavigationSearchToNavigationUser(name))
                            }
                        }
                        adapter.setOnSelectedListener(listener)
                    }
                    is Resource.Failure -> {
                        Toast.makeText(requireContext(), "API rate limit exceeded!", Toast.LENGTH_SHORT).show()
                        binding.searchRv.visibility = GONE
                    }
                }


            }else{
                Toast.makeText(requireContext(), "API rate limit exceeded!", Toast.LENGTH_SHORT).show()
                binding.searchRv.visibility = GONE
            }
        }
    }

}