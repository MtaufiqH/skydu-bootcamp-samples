package skydu.android.session3

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import skydu.android.session3.appspecificstorage.AppSpecificStorageActivity
import skydu.android.session3.databinding.ActivityMainBinding
import skydu.android.session3.sharedstorage.ImportExportActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)

        binding.btnAppSpecificStorage.setOnClickListener {
            gotoActivity(AppSpecificStorageActivity::class.java)
        }

        binding.btnSharedcStorage.setOnClickListener {
            gotoActivity(ImportExportActivity::class.java)
        }
        setContentView(binding.root)
    }


    private fun gotoActivity(clz: Class<out Activity>) {
        Intent(this, clz).run {
            startActivity(this)
        }
    }
}