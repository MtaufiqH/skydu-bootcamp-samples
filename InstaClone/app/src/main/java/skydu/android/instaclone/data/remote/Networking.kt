package skydu.android.instaclone.data.remote

import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import skydu.android.instaclone.BuildConfig


object Networking {

    val service: NetworkService by lazy {
        Retrofit.Builder()
            .baseUrl("https://skydu-insta-api.herokuapp.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NetworkService::class.java)
    }

}