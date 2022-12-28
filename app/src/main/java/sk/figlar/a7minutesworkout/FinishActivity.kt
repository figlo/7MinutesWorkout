package sk.figlar.a7minutesworkout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import sk.figlar.a7minutesworkout.databinding.ActivityFinishBinding

class FinishActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFinishBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityFinishBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.tbFinishActivity)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.tbFinishActivity.setNavigationOnClickListener {
            onBackPressed()
        }

        binding.btnFinish.setOnClickListener {
            finish()
        }
    }
}