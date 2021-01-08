package skydu.android.instaclone.ui.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import skydu.android.instaclone.databinding.ActivityHomeBinding
import skydu.android.instaclone.databinding.ActivityLoginBinding

class HomeActivity : AppCompatActivity() {
    lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Toast.makeText(this, "Home", Toast.LENGTH_SHORT).show()
    }
}