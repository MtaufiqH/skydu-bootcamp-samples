package skydu.android.instaclone.data.remote

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import skydu.android.instaclone.data.remote.common.ApiResponse
import skydu.android.instaclone.data.remote.login.LoginRequest
import skydu.android.instaclone.data.remote.login.LoginResponse
import skydu.android.instaclone.data.remote.post.PostsResponse

interface NetworkService {
    @POST("api/token")
    suspend fun doLoginCall(
        @Body request: LoginRequest
    ): Response<ApiResponse<LoginResponse>>

    @GET("api/token/revoke")
    suspend fun doLogout(): Response<ApiResponse<String>>

    @GET("api/posts")
    suspend fun getHomeFeed(@Query("page") page: Int): Response<ApiResponse<List<PostsResponse>>>
}