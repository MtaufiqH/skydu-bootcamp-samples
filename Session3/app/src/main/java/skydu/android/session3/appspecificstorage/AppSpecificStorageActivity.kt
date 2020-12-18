package skydu.android.session3.appspecificstorage

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import skydu.android.session3.databinding.ActivityAppSpecificStorageBinding

class AppSpecificStorageActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityAppSpecificStorageBinding.inflate(layoutInflater)
        binding.btnPersist.setOnClickListener {
            gotoActivity(PersistStorageActivity::class.java)
        }

        binding.btnCache.setOnClickListener {
            gotoActivity(CacheActivity::class.java)
        }
        setContentView(binding.root)
    }


    private fun gotoActivity(clz: Class<out Activity>) {
        Intent(this, clz).run {
            startActivity(this)
        }
    }
}