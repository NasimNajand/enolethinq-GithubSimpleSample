package ir.ebcom.githubsimplesample.data.view.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding

abstract class BaseFragment<VM: ViewModel, VB: ViewBinding>
//    (
//    private val bindingInflater: (inflater: LayoutInflater) -> VB
//)
    : Fragment() {
    protected lateinit var binding: VB
    private lateinit var viewModel: VM


//    private var _binding: VB? = null
//    val binding: VB
//        get() = _binding as VB

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        _binding = bindingInflater.invoke(inflater)
        binding = getFragmentBinding(inflater, container)
//        viewModel = ViewModelProvider(this, factory)[getViewModel()]
        viewModel = getViewModel()
        return binding.root
    }

    abstract fun getViewModel(): VM

    abstract fun getFragmentBinding(inflater: LayoutInflater, container: ViewGroup?): VB

}