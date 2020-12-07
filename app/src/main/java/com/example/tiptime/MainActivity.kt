package com.example.tiptime

import android.content.Context
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import com.example.tiptime.databinding.ActivityMainBinding
import java.text.NumberFormat
import kotlin.math.ceil

class MainActivity : AppCompatActivity() {


    // This is used to promise that code will initialize the variable before using it. If don't, app will crash.
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        //This line initializes the binding object which will be used to access Views in the activity_main.xml layout.
        binding = ActivityMainBinding.inflate(layoutInflater)

        //It will convert the id's fo views to CamelCase in resources.
        setContentView(binding.root)

        // Setup a click listener on the calculate button to calculate the tip
        binding.calculateButton.setOnClickListener { calculateTip() }

        // Set up a key listener on the EditText field to listen for "enter" button presses
        binding.costOfServiceEditText.setOnKeyListener { view, keyCode, _ -> handleKeyEvent(view, keyCode) }
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


    //to hide the keyboard
    fun handleKeyEvent(view: View, keyCode: Int): Boolean {
        if (keyCode == KeyEvent.KEYCODE_ENTER) {
            // Hide the keyboard
            val inputMethodManager =
                    getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
            return true
        }
        return false
    }

} //main activity





    