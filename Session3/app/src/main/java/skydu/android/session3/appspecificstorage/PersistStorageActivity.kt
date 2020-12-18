package skydu.android.session3.appspecificstorage

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import skydu.android.session3.databinding.ActivityAppSpecificStorageBinding
import skydu.android.session3.databinding.ActivityPersistStorageBinding
import java.io.File

class PersistStorageActivity : AppCompatActivity() {

    private val FILENAME = "fileName"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityPersistStorageBinding.inflate(layoutInflater)

       readFile().run {
           if(this.isNotEmpty()) {
               binding.editText.setText(this)
           }
       }

        binding.btnSave.setOnClickListener {
           saveFile(binding.editText.text.toString())
        }

        binding.btnDelete.setOnClickListener {
           deleteFile()
        }
        setContentView(binding.root)
    }

    private fun readFile(): String {
        val file = File(this.filesDir, FILENAME)
        if(file.exists()) {
            Toast.makeText(this, "Sudah ada data", Toast.LENGTH_SHORT).show()
            var data = "";
            file.bufferedReader().useLines { lines ->
                lines.forEachIndexed { index, line ->
                    if (index != 0) {
                        data += "\n"
                    }
                    data += line
                }
            }
            return data;
        } else {
            Toast.makeText(this, "Belum ada data", Toast.LENGTH_SHORT).show()
            return "";
        }
    }

    private fun saveFile(text: String) {
        this.openFileOutput(FILENAME, Context.MODE_PRIVATE).use {
            it.write(text.toByteArray())
            Toast.makeText(this, "SAVED", Toast.LENGTH_SHORT).show()
        }
    }

    private fun deleteFile() {
        val file = File(this.filesDir, FILENAME)
        if(file.exists()) {
            file.delete()
            Toast.makeText(this, "file berhasil di hapus", Toast.LENGTH_SHORT).show()
            finish()
        } else {
            Toast.makeText(this, "file tidak ada, gagal hapus", Toast.LENGTH_SHORT).show()
        }
    }
}