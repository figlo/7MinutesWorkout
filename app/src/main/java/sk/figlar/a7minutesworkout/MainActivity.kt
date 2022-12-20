package sk.figlar.a7minutesworkout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.FrameLayout
import android.widget.Toast
import sk.figlar.a7minutesworkout.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit private var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.flStart.setOnClickListener {
            Toast.makeText(this@MainActivity, "Here we will start the exercise.", Toast.LENGTH_SHORT).show()
        }
    }
}