package skydu.android.session3.appspecificstorage

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import skydu.android.session3.R
import skydu.android.session3.databinding.ActivityAppSpecificStorageBinding
import skydu.android.session3.databinding.ActivityMainBinding

class AppSpecificStorageActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityAppSpecificStorageBinding.inflate(layoutInflater)
        
        setContentView(binding.root)
    }


    private fun gotoActivity(clz: Class<out Activity>) {
        Intent(this, clz).run {
            startActivity(this)
        }
    }
}