package skydu.android.instaclone.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import skydu.android.instaclone.R
import skydu.android.instaclone.data.repository.model.DataResult
import skydu.android.instaclone.databinding.ActivityHomeBinding
import skydu.android.instaclone.ui.login.LoginActivity
import skydu.android.instaclone.utils.LoadingDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import skydu.android.instaclone.ui.home.post.PostsAdapter

class HomeActivity : AppCompatActivity() {

    private lateinit var postsAdapter: PostsAdapter

    private lateinit var binding: ActivityHomeBinding

    private val viewModel: HomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.appBarLayout.ivToolbarRightIcon.run {
            setImageResource(R.drawable.ic_logout)
            setOnClickListener {
                viewModel.triggerLogout()
            }
            visibility = View.VISIBLE
        }

        Toast.makeText(this, "Home", Toast.LENGTH_SHORT).show()

        postsAdapter = PostsAdapter(
            {
                Toast.makeText(this, "Go to Profile", Toast.LENGTH_SHORT).show()
            },
            {
                Toast.makeText(this, "Like Clicked", Toast.LENGTH_SHORT).show()
            },
            {
                Toast.makeText(this, "Shared Clicked", Toast.LENGTH_SHORT).show()
            },
            {
                Toast.makeText(this, "Go to Post Detail", Toast.LENGTH_SHORT).show()

            }
        )

        binding.rvPosts.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = postsAdapter
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    layoutManager?.run {
                        if (this is LinearLayoutManager
                            && itemCount > 0
                            && itemCount == findLastVisibleItemPosition() + 1
                        ) viewModel.loadNextPage()
                    }
                }
            })
        }
        setupObservers()
    }

    private fun setupObservers() {
        val logoutDialog = LoadingDialog.get(this, getString(R.string.logout))

        viewModel.loggedOutEvent.observe(this) {
            when (it.state) {
                DataResult.State.SUCCESS -> {
                    logoutDialog.dismiss()
                    startActivity(Intent(applicationContext, LoginActivity::class.java))
                    finish()
                }
                DataResult.State.LOADING -> {
                    logoutDialog.show()
                }
                else -> {
                    it.errorMessage?.run {
                        Toast.makeText(this@HomeActivity, this, Toast.LENGTH_SHORT).show()
                    }
                    logoutDialog.dismiss()
                }
            }
        }

        viewModel.posts.observe(this) {
            when (it.state) {
                DataResult.State.SUCCESS -> {
                    it.data?.run {
                        if(this.firstPage) {
                            postsAdapter.updateList(this.list)
                        } else {
                            postsAdapter.appendData(this.list)
                        }
                    }
                    binding.rvPosts.visibility = View.VISIBLE
                    binding.progressBarFull.visibility = View.GONE
                    binding.progressBarBottom.visibility = View.GONE
                }
                DataResult.State.LOADING -> {
                    it.data?.run {
                        if(this.firstPage) {
                            binding.progressBarFull.visibility = View.VISIBLE
                            binding.progressBarBottom.visibility = View.GONE
                            binding.rvPosts.visibility = View.GONE
                        } else {
                            binding.progressBarFull.visibility = View.GONE
                            binding.progressBarBottom.visibility = View.VISIBLE
                            binding.rvPosts.visibility = View.VISIBLE

                        }
                    }
                }
                else -> {
                    it.errorMessage?.run { Toast.makeText(this@HomeActivity, this, Toast.LENGTH_SHORT).show() }
                    binding.progressBarFull.visibility = View.GONE
                    binding.progressBarBottom.visibility = View.GONE
                    binding.rvPosts.visibility = View.VISIBLE
                }
            }
        }
    }
}