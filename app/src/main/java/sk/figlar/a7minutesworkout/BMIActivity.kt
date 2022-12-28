package sk.figlar.a7minutesworkout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import sk.figlar.a7minutesworkout.databinding.ActivityBmiBinding

class BMIActivity : AppCompatActivity() {
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
    }
}