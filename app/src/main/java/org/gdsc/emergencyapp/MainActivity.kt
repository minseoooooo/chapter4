package org.gdsc.emergencyapp

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.view.isVisible
import org.gdsc.emergencyapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.goInputActivity.setOnClickListener{
            val intent = Intent(this, EditActivity::class.java)
            startActivity(intent)
        }

        binding.deleteButton.setOnClickListener{
            deleteData()
        }

        binding.emergencyNumberLayer.setOnClickListener{
            val intent = with(Intent(Intent.ACTION_VIEW)){
                val phoneNumber = binding.emergencyNumberValueTextView.text.toString()
                    .replace("-", "")
                data = Uri.parse("tel:$phoneNumber")
                startActivity(this)
            }
        }

    }

    override fun onResume(){
        super.onResume()
        getDataUiUpdate()
    }

    private fun getDataUiUpdate(){
        with(getSharedPreferences(USER_INFORMATION, Context.MODE_PRIVATE)){
            binding.nameValueTextView.text = getString(NAME, "미정")
            binding.birthdateValueTextView.text= getString(BIRTHDATE, "미정")
            binding.bloodtypeValueTextView.text= getString(BLOOD_TYPE, "미정")
            binding.emergencyNumberValueTextView.text= getString(EMERGENCY_NUMBER, "미정")
            val caution  = getString(CAUTION, "")

            binding.cautionTextView.isVisible = caution.isNullOrEmpty().not()
            binding.cautionValueTextView.isVisible = caution.isNullOrEmpty().not()
            if(!caution.isNullOrEmpty()){
                binding.cautionValueTextView.text = caution
            }

        }
    }

    private fun deleteData(){
        with(getSharedPreferences(USER_INFORMATION, MODE_PRIVATE).edit()){
            clear()
            apply()
            getDataUiUpdate()

        }
        Toast.makeText(this, "잘 지워졌습니다.", Toast.LENGTH_SHORT).show()
    }


}