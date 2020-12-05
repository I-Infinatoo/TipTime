package com.example.tiptime

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.tiptime.databinding.ActivityMainBinding
import java.text.NumberFormat
import kotlin.math.ceil

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding  // This is used to promise that code will initialize the variable before using it. If don't, app will crash.

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater) //This line initializes the binding object which will be used to access Views in the activity_main.xml layout.
        setContentView(binding.root)        //It will convert the id's fo views to CamelCase in resources.

        binding.calculateButton.setOnClickListener { calculateTip() }
    }


    private fun calculateTip() {

        val stringInTextField = binding.costOfServiceEditText.text.toString()  //It will take input as a complete text in string form.
        val cost = stringInTextField.toDoubleOrNull()

        if (cost == null) {
            binding.tipResult.text = " "
            return
        }
        /**
         * binding.tipOptions.checkedRadioButtonId -> will return the id of selected radio button
         * when() {}  it will return the corresponding percentage
         **/
        val tipPercent = when (binding.tipOptions.checkedRadioButtonId) {
            R.id.option_twenty_percent -> 0.20
            R.id.option_eighteen_percent -> 0.18
            else -> 0.15
        }

        var tip = tipPercent * cost
        if( binding.roundUpSwitch.isChecked) {  // check if the round up button is checked
            tip = ceil(tip)
        }

        //This will give a number formatter which can be used to format numbers as currency.
        val formattedTip = NumberFormat.getCurrencyInstance().format(tip)

        //binding.tipResult.setText(formattedTip.toString())
        binding.tipResult.text = getString(R.string.tip_amount, formattedTip)
    }
}