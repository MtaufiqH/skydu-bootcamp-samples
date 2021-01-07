package skydu.android.instaclone.ui.login

import androidx.lifecycle.*
import skydu.android.instaclone.data.repository.model.DataResult
import skydu.android.instaclone.utils.ResourceUtil
import skydu.android.instaclone.R

class LoginViewModel : ViewModel() {
    private val _emailValidation: MutableLiveData<DataResult<Unit>> = MutableLiveData()
    private val _passwordValidation: MutableLiveData<DataResult<Unit>> = MutableLiveData()


    val emailValidation: LiveData<DataResult<Unit>> = _emailValidation
    val passwordValidation: LiveData<DataResult<Unit>> = _passwordValidation

    fun onLogin(username: String, password: String) {
        var validated = true
        if (username.isBlank()) {
            validated = false
            _emailValidation.postValue(
                DataResult(
                    DataResult.State.ERROR,
                    Unit,
                    ResourceUtil.getString(R.string.username_field_empty)
                )
            )
        } else {
            _emailValidation.postValue(
                DataResult(DataResult.State.SUCCESS, Unit, null)
            )
        }

        if (password.isBlank()) {
            validated = false
            _passwordValidation.postValue(
                DataResult(
                    DataResult.State.ERROR,
                    Unit,
                    ResourceUtil.getString(R.string.password_field_empty)
                )
            )
        } else {
            _passwordValidation.postValue(
                DataResult(DataResult.State.SUCCESS, Unit, null)
            )
        }
        if (validated) {
            //todo login here
        }
    }
}