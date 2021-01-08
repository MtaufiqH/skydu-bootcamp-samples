package skydu.android.instaclone.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import kotlinx.coroutines.Dispatchers
import skydu.android.instaclone.data.remote.NetworkService
import skydu.android.instaclone.data.remote.Networking
import skydu.android.instaclone.data.remote.login.LoginRequest
import skydu.android.instaclone.data.repository.model.DataResult

class UserRepository {
    private val networkService: NetworkService = Networking.service

    fun doUserLogin(username: String, password: String): LiveData<DataResult<Unit>> =
        liveData(Dispatchers.IO) {
            emit(DataResult<Unit>(DataResult.State.LOADING, null, null))

            val result =
                try {
                    networkService.doLoginCall(LoginRequest(username, password)).convertToDataResult()
                }catch (e: Exception) {
                    e.convertExceptionToError()
                }

            if (result.state == DataResult.State.SUCCESS) {
                result.data?.run {
                    saveCurrentUser(this.profile, this.token)
                    emit(
                        DataResult(
                            DataResult.State.SUCCESS, Unit, null
                        )
                    )
                }
            } else {
                emit(DataResult<Unit>(result.state, null, result.errorMessage))
            }
        }
}