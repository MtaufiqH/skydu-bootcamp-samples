package skydu.android.instaclone.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import skydu.android.instaclone.data.repository.UserRepository

class HomeViewModel : ViewModel() {
    private val userRepository: UserRepository = UserRepository()

    private val triggerLogOut = MutableLiveData<Unit>()

    val loggedOutEvent = triggerLogOut.switchMap {
        userRepository.doLogout()
    }


    fun triggerLogout() {
        triggerLogOut.value = Unit
    }
}