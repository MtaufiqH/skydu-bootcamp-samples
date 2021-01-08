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

class HomeActivity : AppCompatActivity() {
    lateinit var binding: ActivityHomeBinding

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
    }
}