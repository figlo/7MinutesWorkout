package sk.figlar.a7minutesworkout

import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import sk.figlar.a7minutesworkout.databinding.ActivityFinishBinding
import java.util.*

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

        val dao = (application as WorkOutApp).db.historyDao()
        addDateToDatabase(dao)
    }

    private fun addDateToDatabase(historyDao: HistoryDao) {
        val calendar = java.util.Calendar.getInstance()
        val dateTime = calendar.time
        val formatter = java.text.SimpleDateFormat("dd MMM yyyy HH:mm:ss", Locale.getDefault())
        val date = formatter.format(dateTime)

        lifecycleScope.launch {
            historyDao.insert(HistoryEntity(date))
        }
    }
}