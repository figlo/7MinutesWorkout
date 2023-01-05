package sk.figlar.a7minutesworkout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
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

        val dao = (application as WorkOutApp).db.historyDao()
        getAllCompleteDates(dao)
    }

    private fun getAllCompleteDates(dao: HistoryDao) {
        lifecycleScope.launch {
            dao.fetchAllDates().collect() { allCompletedDatesList ->
                if (allCompletedDatesList.isNotEmpty()) {
                    binding.tvHistory.visibility = View.VISIBLE
                    binding.rvHistory.visibility = View.VISIBLE
                    binding.tvNoDataAvailable.visibility = View.GONE

                    binding.rvHistory.layoutManager = LinearLayoutManager(this@HistoryActivity)
                    val dates = allCompletedDatesList.map { it.date }
                    val adapter = HistoryAdapter(dates)
                    binding.rvHistory.adapter = adapter
                } else {
                    binding.tvHistory.visibility = View.GONE
                    binding.rvHistory.visibility = View.GONE
                    binding.tvNoDataAvailable.visibility = View.VISIBLE
                }
            }
        }
    }
}