package ir.ebcom.githubsimplesample.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ir.ebcom.githubsimplesample.data.network.NetworkConnectionInterceptor
import ir.ebcom.githubsimplesample.data.network.SearchApi
import ir.ebcom.githubsimplesample.utils.AppConstants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    internal fun provideNetworkConnectionInterceptor(mContext: Context): NetworkConnectionInterceptor {
        return NetworkConnectionInterceptor(mContext)
    }

    @Provides
    internal fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(AppConstants.BASE_URL)
            .build()
    }

    @Provides
    internal fun provideSearchApi(retrofit: Retrofit): SearchApi{
        return retrofit.create(SearchApi::class.java)
    }
}