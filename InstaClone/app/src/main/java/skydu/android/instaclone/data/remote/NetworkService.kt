package skydu.android.instaclone.data.remote

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import skydu.android.instaclone.data.remote.common.ApiResponse
import skydu.android.instaclone.data.remote.login.LoginRequest
import skydu.android.instaclone.data.remote.login.LoginResponse

interface NetworkService {
    @POST("api/token")
    suspend fun doLoginCall(
        @Body request: LoginRequest
    ): Response<ApiResponse<LoginResponse>>

    @GET("api/token/revoke")
    suspend fun doLogout(): Response<ApiResponse<String>>
}