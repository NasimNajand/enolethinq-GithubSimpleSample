package ir.ebcom.githubsimplesample.data.view.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ir.ebcom.githubsimplesample.data.repository.BaseRepository
import ir.ebcom.githubsimplesample.data.repository.SearchRepository
import ir.ebcom.githubsimplesample.data.view.search.SearchViewModel
import java.lang.IllegalArgumentException

@Suppress("UNCHECKED_CAST")
class ViewModelFactory(
    private val repository: BaseRepository
): ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(SearchViewModel::class.java) -> SearchViewModel(repository as SearchRepository) as T
            else -> throw IllegalArgumentException("ViewModel Class Not Found.")
        }
    }
}