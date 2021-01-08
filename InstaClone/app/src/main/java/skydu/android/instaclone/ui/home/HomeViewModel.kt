package skydu.android.instaclone.ui.home

import androidx.lifecycle.*
import skydu.android.instaclone.data.repository.PostRepository
import skydu.android.instaclone.data.repository.UserRepository
import skydu.android.instaclone.data.repository.model.DataResult
import skydu.android.instaclone.data.repository.model.PostViewData

class HomeViewModel : ViewModel() {
    private val userRepository: UserRepository = UserRepository()

    private val triggerLogOut = MutableLiveData<Unit>()

    private val postRepository: PostRepository = PostRepository()

    private val page: MutableLiveData<Int> = MutableLiveData()

    init {
        page.postValue(1)
    }

    val posts: LiveData<DataResult<List<PostViewData>>> =
        page.switchMap { page ->
            postRepository.fetchHomePostList(page).switchMap {
                liveData {
                    if (it.state == DataResult.State.UNAUTHORIZED) {
                        triggerLogout()
                    } else {
                        emit(
                            DataResult(
                                it.state,
                                it.data,
                                it.errorMessage
                            )
                        )
                    }
                }
            }
        }

    val loggedOutEvent = triggerLogOut.switchMap {
        userRepository.doLogout()
    }


    fun triggerLogout() {
        triggerLogOut.value = Unit
    }
}