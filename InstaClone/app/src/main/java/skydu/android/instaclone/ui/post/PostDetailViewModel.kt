package skydu.android.instaclone.ui.post

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.switchMap
import skydu.android.instaclone.data.repository.PostRepository
import skydu.android.instaclone.data.repository.model.CommentViewData
import skydu.android.instaclone.data.repository.model.DataResult
import skydu.android.instaclone.ui.base.BaseViewModel

class PostDetailViewModel(postId: Int) : BaseViewModel() {

    private val postRepository = PostRepository()

    val postDetails: LiveData<DataResult<List<CommentViewData>>> =
        postRepository.fetchPostDetail(postId).switchMap {
            liveData {
                if (it.state == DataResult.State.UNAUTHORIZED) {
                    triggerLogout()
                } else {
                    emit(it)
                }
            }
        }
}