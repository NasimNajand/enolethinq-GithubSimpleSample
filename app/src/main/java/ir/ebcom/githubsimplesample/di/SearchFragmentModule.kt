package ir.ebcom.githubsimplesample.di

import androidx.lifecycle.ViewModelProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ir.ebcom.githubsimplesample.data.repository.SearchRepository
import ir.ebcom.githubsimplesample.data.view.search.SearchViewModel
import ir.ebcom.githubsimplesample.utils.ViewModelProviderFactory

@Module
@InstallIn(SingletonComponent::class)
object SearchFragmentModule {

    @Provides
    fun provideSearchViewModel(searchRepository: SearchRepository): SearchViewModel{
        return SearchViewModel(searchRepository)
    }

    @Provides
    fun provideViewModelFactory(searchViewModel: SearchViewModel): ViewModelProvider.Factory =
        ViewModelProviderFactory(searchViewModel)
}