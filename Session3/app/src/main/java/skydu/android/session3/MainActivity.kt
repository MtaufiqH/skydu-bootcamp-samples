package skydu.android.session3

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import skydu.android.session3.appspecificstorage.CacheActivity
import skydu.android.session3.appspecificstorage.PersistStorageActivity
import skydu.android.session3.databinding.ActivityMainBinding
import skydu.android.session3.preference.SharedPreferenceActivity
import skydu.android.session3.preference.SharedPreferenceConstant
import skydu.android.session3.sharedstorage.ImportExportActivity

class MainActivity : AppCompatActivity() {
    lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        binding.btnAppSpecificStoragePersist.setOnClickListener {
            gotoActivity(PersistStorageActivity::class.java)
        }

        binding.btnAppSpecificStorageCache.setOnClickListener {
            gotoActivity(CacheActivity::class.java)
        }

        binding.btnSharedcStorage.setOnClickListener {
            gotoActivity(ImportExportActivity::class.java)
        }

        binding.btnSharedPreference.setOnClickListener {
            gotoActivity(SharedPreferenceActivity::class.java)
        }
        setContentView(binding.root)
    }

    override fun onResume() {
        super.onResume()
        val pref = getSharedPreferences(SharedPreferenceConstant.PREF_NAME, Context.MODE_PRIVATE);
        pref.getString(SharedPreferenceConstant.USER_NAME_KEY, "").run {
            if(!this.isNullOrEmpty()) {
                binding.textviewHalo.text = "Halo $this"
            }
        }
    }


    private fun gotoActivity(clz: Class<out Activity>) {
        Intent(this, clz).run {
            startActivity(this)
        }
    }
}