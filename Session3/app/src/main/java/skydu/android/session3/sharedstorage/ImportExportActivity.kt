package skydu.android.session3.sharedstorage

import android.R.attr
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import skydu.android.session3.databinding.ActivityImportExportBinding
import java.io.BufferedReader
import java.io.FileNotFoundException
import java.io.InputStreamReader


class ImportExportActivity : AppCompatActivity() {
    private val PICKFILE_REQUEST_CODE = 1;
    private val CREATE_DOCUMENT_REQUEST_CODE = 2;

    lateinit var binding: ActivityImportExportBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityImportExportBinding.inflate(layoutInflater)

        binding.btnExport.setOnClickListener {
            val intent = Intent(Intent.ACTION_CREATE_DOCUMENT).apply {
                addCategory(Intent.CATEGORY_OPENABLE)
                type = "text/plain"
            }
            startActivityForResult(intent, CREATE_DOCUMENT_REQUEST_CODE)
        }


        binding.btnImport.setOnClickListener {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "text/plain"
            startActivityForResult(intent, PICKFILE_REQUEST_CODE)
        }
        setContentView(binding.root)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CREATE_DOCUMENT_REQUEST_CODE) {
            val uri = data?.data
            if (resultCode == RESULT_OK && uri != null) {
                try {
                    val stream = contentResolver.openOutputStream(uri)
                    if(stream != null) {
                        stream.write(binding.editText.text.toString().toByteArray())
                        stream.close()
                    }
                } catch (e: FileNotFoundException) {
                    e.printStackTrace()
                }
            }
        }
        if (requestCode == PICKFILE_REQUEST_CODE) {
            val uri = data?.data
            if (resultCode == RESULT_OK && uri != null) {
                val stringBuilder = StringBuilder()
                contentResolver.openInputStream(uri)?.use { inputStream ->
                    BufferedReader(InputStreamReader(inputStream)).use { reader ->
                        var line: String? = reader.readLine()
                        while (line != null) {
                            stringBuilder.append(line)
                            line = reader.readLine()
                        }
                    }
                }
                binding.editText.setText(stringBuilder.toString())
            }
        }
    }
}