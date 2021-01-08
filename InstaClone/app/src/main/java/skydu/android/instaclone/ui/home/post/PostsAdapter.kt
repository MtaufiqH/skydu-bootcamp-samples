package skydu.android.instaclone.ui.home.post

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import skydu.android.instaclone.data.repository.model.PostViewData

class PostsAdapter(
    private val onProfileClicked: ((PostViewData) -> Unit),
    private val onLikeClickListener: ((PostViewData) -> Unit),
    private val onShareClickListener: ((PostViewData) -> Unit),
    private val onAddCommentClickListener: ((PostViewData) -> Unit)
) : RecyclerView.Adapter<PostItemViewHolder>() {

    private val dataList: ArrayList<PostViewData> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = PostItemViewHolder(
        parent,
        onProfileClicked,
        onLikeClickListener,
        onShareClickListener,
        onAddCommentClickListener
    )

    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder: PostItemViewHolder, position: Int) {
        holder.bind(dataList[position], position)
    }

    fun appendData(dataList: List<PostViewData>) {
        this.dataList.addAll(dataList)
        notifyDataSetChanged()
    }

    fun updateList(list: List<PostViewData>) {
        dataList.clear()
        dataList.addAll(list)
        notifyDataSetChanged()
    }
}