package sk.figlar.a7minutesworkout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import sk.figlar.a7minutesworkout.databinding.ActivityHistoryBinding

class HistoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHistoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.tbHistory)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "History"

        binding.tbHistory.setNavigationOnClickListener {
            onBackPressed()
        }
    }
}