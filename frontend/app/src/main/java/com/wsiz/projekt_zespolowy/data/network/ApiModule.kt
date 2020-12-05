package com.wsiz.projekt_zespolowy.data.network

import com.wsiz.projekt_zespolowy.data.network.service.PostService
import com.wsiz.projekt_zespolowy.data.shared_preferences.SharedPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@Module
@InstallIn(ApplicationComponent::class)
class ApiModule {

    companion object {
        private const val TIME_OUT_SECONDS = 60L
        private const val CONNECT_TIME_OUT_SECONDS = 5L
    }

    @Provides
    fun providesPostService(retrofit: Retrofit): PostService {
        return retrofit.create(PostService::class.java)
    }

    @Provides
    fun providesRetrofitClient(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Endpoints.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpClient)
            .build()
    }

    @Provides
    fun providesOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor,
        tokenInterceptor: TokenAuthenticationInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .callTimeout(TIME_OUT_SECONDS, TimeUnit.SECONDS)
            .readTimeout(TIME_OUT_SECONDS, TimeUnit.SECONDS)
            .writeTimeout(TIME_OUT_SECONDS, TimeUnit.SECONDS)
            .connectTimeout(CONNECT_TIME_OUT_SECONDS, TimeUnit.SECONDS)
            .addInterceptor(loggingInterceptor)
            .addInterceptor(tokenInterceptor)
            .build()
    }

    @Provides
    fun providesHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BASIC


        return interceptor
    }

    class TokenAuthenticationInterceptor @Inject constructor(private val sharedPreferences: SharedPreferences) :
        Interceptor {

        override fun intercept(chain: Interceptor.Chain): Response {
            val request = chain.request().newBuilder()
                .header("Authorization", "Bearer ${sharedPreferences.getToken()}")
                .build()

            return chain.proceed(request)
        }
    }
}