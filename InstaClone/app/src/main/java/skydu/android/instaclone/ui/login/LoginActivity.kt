package skydu.android.instaclone.ui.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import skydu.android.instaclone.data.repository.model.DataResult
import skydu.android.instaclone.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private val vm: LoginViewModel by viewModels()

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btLogin.setOnClickListener { vm.onLogin(binding.etUsername.text.toString(), binding.etPassword.text.toString()) }
        setupObservers()
    }

    private fun setupObservers() {
        vm.emailValidation.observe(this) {
            when (it.state) {
                DataResult.State.ERROR -> {
                    binding.layoutUsername.error = it.errorMessage
                }
                else -> {
                    binding.layoutUsername.isErrorEnabled = false
                }
            }
        }

        vm.passwordValidation.observe(this) {
            when (it.state) {
                DataResult.State.ERROR -> {
                    binding.layoutPassword.error = it.errorMessage
                }
                else -> {
                    binding.layoutPassword.isErrorEnabled = false
                }
            }
        }
    }
}