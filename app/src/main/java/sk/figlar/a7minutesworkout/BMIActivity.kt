package sk.figlar.a7minutesworkout

import android.icu.math.BigDecimal
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import sk.figlar.a7minutesworkout.databinding.ActivityBmiBinding
import java.math.RoundingMode

class BMIActivity : AppCompatActivity() {

    companion object {
        private const val METRIC_UNITS_VIEW = "METRIC_UNITS_VIEW"
        private const val US_UNITS_VIEW = "US_UNITS_VIEW"
    }

    private var currentVisibleView: String = METRIC_UNITS_VIEW

    private lateinit var binding: ActivityBmiBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityBmiBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.tbBMI)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Calculate BMI"

        binding.tbBMI.setNavigationOnClickListener {
            onBackPressed()
        }

        makeVisibleMetricUnitsView()

        binding.rgUnits.setOnCheckedChangeListener { _, checkedId: Int ->
            if (checkedId == R.id.rbMetricUnits) {
                makeVisibleMetricUnitsView()
            } else {
                makeVisibleUSUnitsView()
            }
        }

        binding.btnCalculate.setOnClickListener {
            calculateUnits()
        }
    }

    private fun makeVisibleMetricUnitsView() {
        currentVisibleView = METRIC_UNITS_VIEW
        binding.tilWeight.visibility = View.VISIBLE
        binding.tilHeight.visibility = View.VISIBLE
        binding.tilWeightUS.visibility = View.INVISIBLE
        binding.tilHeightUSFeet.visibility = View.INVISIBLE
        binding.tilHeightUSInch.visibility = View.INVISIBLE

        binding.etWeight.text?.clear()
        binding.etHeight.text?.clear()

        binding.llBMIResult.visibility = View.INVISIBLE
    }

    private fun makeVisibleUSUnitsView() {
        currentVisibleView = US_UNITS_VIEW
        binding.tilWeight.visibility = View.INVISIBLE
        binding.tilHeight.visibility = View.INVISIBLE
        binding.tilWeightUS.visibility = View.VISIBLE
        binding.tilHeightUSFeet.visibility = View.VISIBLE
        binding.tilHeightUSInch.visibility = View.VISIBLE

        binding.etWeightUS.text?.clear()
        binding.etHeightUSFeet.text?.clear()
        binding.etHeightUSInch.text?.clear()

        binding.llBMIResult.visibility = View.INVISIBLE
    }

    private fun displayBMIResult(bmi: Float) {
        val bmiLabel: String
        val bmiDescription: String

        when {
            bmi < 15f   -> {
                bmiLabel = "Very severely underweight"
                bmiDescription = "Oops! You really need to take better care of yourself! Eat more!"
            }
            bmi < 16f   -> {
                bmiLabel = "Severely underweight"
                bmiDescription = "Oops! You really need to take better care of yourself! Eat more!"
            }
            bmi < 18.5f -> {
                bmiLabel = "Underweight"
                bmiDescription = "Oops! You really need to take better care of yourself! Eat more!"
            }
            bmi < 25f   -> {
                bmiLabel = "Normal"
                bmiDescription = "Congratulations! You are in a good shape!"
            }
            bmi < 30f   -> {
                bmiLabel = "Overweight"
                bmiDescription = "Oops! You really need to take better care of yourself! Workout more!"
            }
            bmi < 35f   -> {
                bmiLabel = "Obese class | Moderately obese"
                bmiDescription = "Oops! You really need to take better care of yourself! Workout more!"
            }
            bmi < 40f   -> {
                bmiLabel = "Obese class | Severely obese"
                bmiDescription = "OMG! You are in a very dangerous condition! Act now!"
            }
            else        -> {
                bmiLabel = "Obese class | Very severely obese"
                bmiDescription = "OMG! You are in a very dangerous condition! Act now!"
            }
        }

        binding.llBMIResult.visibility = View.VISIBLE

        val bmiValue = java.math.BigDecimal(bmi.toDouble()).setScale(2, RoundingMode.HALF_EVEN).toString()

        binding.tvBMIValue.text = bmiValue
        binding.tvBMIType.text = bmiLabel
        binding.tvBMIDescription.text = bmiDescription
    }

    private fun validateUnits(): Boolean {
        var isValid = true

        if (binding.etWeight.text.toString().isEmpty()) {
            isValid = false
        } else if (binding.etHeight.text.toString().isEmpty()) {
            isValid = false
        }

        return isValid
    }

    private fun calculateUnits() {
        if (currentVisibleView == METRIC_UNITS_VIEW) {
            if (validateUnits()) {
                val weight = binding.etWeight.text.toString().toFloat()
                val height = binding.etHeight.text.toString().toFloat() / 100

                val bmi = weight / (height * height)

                displayBMIResult(bmi)
            } else {
                Toast.makeText(this, "Please enter valid values.", Toast.LENGTH_SHORT).show()
            }
        } else {
            if (validateUSUnits()) {
                val weightUS = binding.etWeightUS.text.toString().toFloat()
                val heightUSFeet = binding.etHeightUSFeet.text.toString().toFloat()
                val heightUSInch = binding.etHeightUSInch.text.toString().toFloat()

                val heightUSInchValue = heightUSInch + heightUSFeet * 12
                val bmi = 703 * weightUS / (heightUSInchValue * heightUSInchValue)

                displayBMIResult(bmi)
            } else {
                Toast.makeText(this, "Please enter valid values.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun validateUSUnits(): Boolean {
        var isValid = true

        if (binding.etWeightUS.text.toString().isEmpty()) {
            isValid = false
        } else if (binding.etHeightUSFeet.text.toString().isEmpty()) {
            isValid = false
        } else if (binding.etHeightUSInch.text.toString().isEmpty()) {
            isValid = false
        }

        return isValid
    }
}