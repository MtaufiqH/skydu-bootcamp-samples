package skydu.android.session3.preference

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import skydu.android.session3.databinding.ActivityImportExportBinding
import skydu.android.session3.databinding.ActivitySharedPreferenceBinding

class SharedPreferenceActivity : AppCompatActivity() {
    lateinit var pref: SharedPreferences
    lateinit var binding: ActivitySharedPreferenceBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySharedPreferenceBinding.inflate(layoutInflater)
        pref = getSharedPreferences(SharedPreferenceConstant.PREF_NAME, Context.MODE_PRIVATE);
        val visitCount = pref.getInt(SharedPreferenceConstant.VISIT_PAGE_COUNT_KEY, 0) + 1
        saveVisitCount(visitCount)
        binding.textViewVisit.text =
            "Anda telah mengunjungi halaman ini sebanyak " + visitCount + " kali"

        var buttonClickCount = pref.getInt(SharedPreferenceConstant.BUTTON_CLICK_COUNT_KEY, 0)
        setButtonClickCount(buttonClickCount)
        binding.btnClick.setOnClickListener {
            buttonClickCount += 1
            setButtonClickCount(buttonClickCount)
            saveButtonClickCount(buttonClickCount)
        }



        setContentView(binding.root)
    }


    private fun saveVisitCount(visitCount: Int) {
        pref.edit().putInt(SharedPreferenceConstant.VISIT_PAGE_COUNT_KEY, visitCount).apply()
    }

    private fun setButtonClickCount(clickCount: Int) {
        binding.textViewButton.text =
            "Anda telah click button ini sebanyak " + clickCount + " kali";
    }

    private fun saveButtonClickCount(clickCount: Int) {
        pref.edit().putInt(SharedPreferenceConstant.BUTTON_CLICK_COUNT_KEY, clickCount).apply()
    }
}