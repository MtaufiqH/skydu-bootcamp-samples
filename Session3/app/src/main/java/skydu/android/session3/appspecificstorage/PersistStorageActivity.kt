package skydu.android.session3.appspecificstorage

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import skydu.android.session3.databinding.ActivityAppSpecificStorageBinding
import skydu.android.session3.databinding.ActivityPersistStorageBinding

class PersistStorageActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityPersistStorageBinding.inflate(layoutInflater)

        setContentView(binding.root)
    }
}