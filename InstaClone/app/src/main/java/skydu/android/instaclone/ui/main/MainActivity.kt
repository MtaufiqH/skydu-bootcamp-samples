package skydu.android.instaclone.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import skydu.android.instaclone.R
import skydu.android.instaclone.ui.login.LoginActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Intent(this, LoginActivity::class.java).run {
            startActivity(this)
        }
    }
}