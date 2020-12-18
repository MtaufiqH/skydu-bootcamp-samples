package skydu.android.session3.appspecificstorage

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import skydu.android.session3.databinding.ActivityCacheBinding
import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter

/**
 * Like Persist Storage, but different in:
 * readFile: use cacheDir
 * saveFile: use File.createTempFile
 * deleteFile: use cacheDir
 */
class CacheActivity : AppCompatActivity() {
    private val FILENAME = "cacheName"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityCacheBinding.inflate(layoutInflater)

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
        val file = File(this.cacheDir, FILENAME)
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
        val cacheFile = File(this.cacheDir, FILENAME)
        val fw = FileWriter(cacheFile.absoluteFile)
        val bw = BufferedWriter(fw)
        bw.write(text)
        bw.close()
        Toast.makeText(this, "SAVED", Toast.LENGTH_SHORT).show()
    }

    private fun deleteFile() {
        val file = File(this.cacheDir, FILENAME)
        if(file.exists()) {
            file.delete()
            Toast.makeText(this, "file berhasil di hapus", Toast.LENGTH_SHORT).show()
            finish()
        } else {
            Toast.makeText(this, "file tidak ada, gagal hapus", Toast.LENGTH_SHORT).show()
        }
    }
}