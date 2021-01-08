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

    private var isLoading = false

    init {
        page.postValue(1)
    }

    val posts: LiveData<DataResult<PostResult>> =
        page.switchMap { page ->
            postRepository.fetchHomePostList(page).switchMap {
                liveData {
                    isLoading = it.state == DataResult.State.LOADING
                    if (it.state == DataResult.State.UNAUTHORIZED) {
                        triggerLogout()
                    } else {
                        emit(
                            DataResult(
                                it.state,
                                PostResult(page ==1, it.data?: emptyList()),
                                it.errorMessage,
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

    fun loadNextPage() {
        if (!isLoading) {
            page.postValue((page.value ?: 1) + 1)
        }
    }


    class PostResult(val firstPage: Boolean, val list: List<PostViewData>)
}