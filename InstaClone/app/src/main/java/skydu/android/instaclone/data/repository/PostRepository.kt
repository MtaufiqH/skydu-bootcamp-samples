package skydu.android.instaclone.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import skydu.android.instaclone.data.remote.NetworkService
import skydu.android.instaclone.data.remote.Networking
import skydu.android.instaclone.data.repository.model.DataResult
import skydu.android.instaclone.data.repository.model.PostViewData
import skydu.android.instaclone.utils.TimeUtils

class PostRepository {
    private val networkService: NetworkService = Networking.service

    fun fetchHomePostList(page: Int): LiveData<DataResult<List<PostViewData>>> =
        liveData {
            emit(DataResult<List<PostViewData>>(DataResult.State.LOADING, null, null))

            val result =
                try {
                    networkService.getHomeFeed(page).convertToDataResult()
                } catch (e: Exception) {
                    e.convertExceptionToError()
                }

            if (result.state == DataResult.State.SUCCESS) {
                result.data?.map { item ->
                    PostViewData(
                        item.id,
                        item.username,
                        item.user_image_url,
                        item.image_url,
                        item.caption.let {
                            if (it.length > 100) {
                                it.substring(0, 100) + "..."
                            } else {
                                it
                            }
                        },
                        item.likes_info.count,
                        item.likes_info.is_liked,
                        TimeUtils.convertTimeFormat(item.created_at)
                    )
                }.run {
                    emit(
                        DataResult(
                            DataResult.State.SUCCESS,
                            this,
                            null
                        )
                    )
                }
            } else {
                emit(DataResult<List<PostViewData>>(result.state, null, result.errorMessage))
            }
        }
}