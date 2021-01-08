package skydu.android.instaclone.data.remote

import retrofit2.Response
import retrofit2.http.*
import skydu.android.instaclone.data.remote.common.ApiResponse
import skydu.android.instaclone.data.remote.login.LoginRequest
import skydu.android.instaclone.data.remote.login.LoginResponse
import skydu.android.instaclone.data.remote.post.PostsResponse
import skydu.android.instaclone.data.remote.profile.ProfileResponse

interface NetworkService {
    @POST("api/token")
    suspend fun doLoginCall(
        @Body request: LoginRequest
    ): Response<ApiResponse<LoginResponse>>

    @GET("api/token/revoke")
    suspend fun doLogout(): Response<ApiResponse<String>>

    @GET("api/posts")
    suspend fun getHomeFeed(@Query("page") page: Int): Response<ApiResponse<List<PostsResponse>>>

    @POST("api/posts/{postId}/like")
    suspend fun doToggleLike(@Path("postId") postId: Int): Response<ApiResponse<String>>

    @GET("api/profile/{username}")
    suspend fun getProfile(@Path("username") username: String): Response<ApiResponse<ProfileResponse>>
}