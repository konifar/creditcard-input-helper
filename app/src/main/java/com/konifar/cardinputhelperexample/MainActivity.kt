package com.konifar.cardinputhelperexample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.konifar.cardinputhelper.R
import com.konifar.cardinputhelper.databinding.ActivityMainBinding
import io.konifar.cardinputhelper.formatter.CardNumberFormatTextWatcher

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(
            this,
            R.layout.activity_main
        )
        binding.panEdit.addTextChangedListener(CardNumberFormatTextWatcher())
    }
}
