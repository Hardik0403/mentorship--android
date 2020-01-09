package org.systers.mentorship.remote

import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.systers.mentorship.MentorshipApplication
import org.systers.mentorship.remote.services.AuthService
import org.systers.mentorship.remote.services.RelationService
import org.systers.mentorship.remote.services.TaskService
import org.systers.mentorship.remote.services.UserService
import org.systers.mentorship.utils.Constants.HTTP_CACHE_SIZE
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File

/**
 * A class that represents API Manager. It encapsulates three services: authentication, relation and user.
 */
class ApiManager {

    val authService: AuthService
    val relationService: RelationService
    val userService: UserService
    val taskService: TaskService

    companion object {
        private var apiManager: ApiManager? = null

        val instance: ApiManager
            get() {
                if (apiManager == null) {
                    apiManager = ApiManager()
                }
                return apiManager as ApiManager
            }
    }

    init {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        /**
         * Creating a cache for HTTP server response.
         * */
        val httpCacheDir = File(MentorshipApplication.getContext().cacheDir, "http-cache")
        val cache = Cache(httpCacheDir, HTTP_CACHE_SIZE)

        val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .addInterceptor(CustomInterceptor())
                .addNetworkInterceptor(CacheInterceptor())
                .cache(cache)
                .build()

        val retrofit = Retrofit.Builder()
                .baseUrl(BaseUrl.apiBaseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .build()

        authService = retrofit.create(AuthService::class.java)
        relationService = retrofit.create(RelationService::class.java)
        userService = retrofit.create(UserService::class.java)
        taskService = retrofit.create(TaskService::class.java)
    }
}