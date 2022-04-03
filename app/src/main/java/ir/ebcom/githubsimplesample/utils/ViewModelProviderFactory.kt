package ir.ebcom.githubsimplesample.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

class ViewModelProviderFactory<V: Any>(private var viewModel: V): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(viewModel.javaClass))
            return viewModel as T
        throw IllegalArgumentException("Unknown Class Name.")
    }
}