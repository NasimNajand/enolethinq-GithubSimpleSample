package ir.ebcom.githubsimplesample.di

import androidx.lifecycle.ViewModelProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ir.ebcom.githubsimplesample.data.repository.SearchRepository
import ir.ebcom.githubsimplesample.data.view.user.UserViewModel
import ir.ebcom.githubsimplesample.utils.ViewModelProviderFactory

@Module
@InstallIn(SingletonComponent::class)
object UserFragmentModule {

    @Provides
    fun provideUserViewModel(searchRepository: SearchRepository): UserViewModel{
        return UserViewModel(searchRepository)
    }

    @Provides
    fun provideViewModelFactory(userViewModel: UserViewModel): ViewModelProvider.Factory =
        ViewModelProviderFactory(userViewModel)
}