package sk.figlar.a7minutesworkout

import android.app.Dialog
import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.os.CountDownTimer
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import sk.figlar.a7minutesworkout.databinding.ActivityExerciseBinding
import sk.figlar.a7minutesworkout.databinding.DialogCustomBackConfirmationBinding
import java.util.*

class ExerciseActivity : AppCompatActivity(), TextToSpeech.OnInitListener {
    private lateinit var binding: ActivityExerciseBinding

    private lateinit var restTimer: CountDownTimer
    private var restProgress = 0
    private var restTimerDuration = 1L

    private lateinit var exerciseTimer: CountDownTimer
    private var exerciseProgress = 0
    private var exerciseTimerDuration = 1L

    private val exerciseList = Constants.defaultExerciseList()
    private var currentExercisePosition = -1

    private lateinit var tts: TextToSpeech
    private lateinit var player: MediaPlayer

    private lateinit var adapter: ExerciseStatusAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExerciseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbarExercise)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        tts = TextToSpeech(this, this)

        binding.toolbarExercise.setNavigationOnClickListener {
            customDialogForBackButton()
        }

        setupRestView()
        setupExerciseStatusRecyclerView()
    }

    override fun onBackPressed() {
        customDialogForBackButton()
    }

    private fun customDialogForBackButton() {
        val customDialog = Dialog(this)
        val dialogBinding = DialogCustomBackConfirmationBinding.inflate(layoutInflater)
        customDialog.setContentView(dialogBinding.root)
        customDialog.setCanceledOnTouchOutside(false)

        dialogBinding.btnYes.setOnClickListener {
            this@ExerciseActivity.finish()
            customDialog.dismiss()
        }
        dialogBinding.btnNo.setOnClickListener {
            customDialog.dismiss()
        }

        customDialog.show()
    }

    private fun setupExerciseStatusRecyclerView() {
        binding.rvExerciseStatus.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        adapter = ExerciseStatusAdapter(exerciseList)
        binding.rvExerciseStatus.adapter = adapter
    }

    private fun setupRestView() {
        try {
            val soundURI = Uri.parse(
                "android.resource://sk.figlar.a7minutesworkout/" +
                        R.raw.press_start
            )
            player = MediaPlayer.create(applicationContext, soundURI)
            player.isLooping = false
            player.start()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        binding.flRestProgressBar.visibility = View.VISIBLE
        binding.tvTitle.visibility = View.VISIBLE
        binding.tvExerciseName.visibility = View.INVISIBLE
        binding.flExerciseProgressBar.visibility = View.INVISIBLE
        binding.ivExerciseImage.visibility = View.INVISIBLE
        binding.tvUpcomingLabel.visibility = View.VISIBLE
        binding.tvUpcomingExerciseName.visibility = View.VISIBLE

        if (this::restTimer.isInitialized) {
            restTimer.cancel()
            restProgress = 0
        }

        binding.tvUpcomingExerciseName.text = exerciseList[currentExercisePosition + 1].name

        setRestProgressBar()
    }

    private fun setupExerciseView() {
        binding.flRestProgressBar.visibility = View.INVISIBLE
        binding.tvTitle.visibility = View.INVISIBLE
        binding.tvExerciseName.visibility = View.VISIBLE
        binding.flExerciseProgressBar.visibility = View.VISIBLE
        binding.ivExerciseImage.visibility = View.VISIBLE
        binding.tvUpcomingLabel.visibility = View.INVISIBLE
        binding.tvUpcomingExerciseName.visibility = View.INVISIBLE

        if (this::exerciseTimer.isInitialized) {
            exerciseTimer.cancel()
            exerciseProgress = 0
        }

        speakOut(exerciseList[currentExercisePosition].name)

        binding.ivExerciseImage.setImageResource(exerciseList[currentExercisePosition].image)
        binding.tvExerciseName.text = exerciseList[currentExercisePosition].name

        setExerciseProgressBar()
    }

    private fun setRestProgressBar() {
        binding.restProgressBar.progress = restProgress

        restTimer = object : CountDownTimer(restTimerDuration * 1000, 1000) {
            override fun onTick(p0: Long) {
                restProgress++
                binding.restProgressBar.progress = restTimerDuration.toInt() - restProgress
                binding.tvRestTimer.text = (restTimerDuration.toInt() - restProgress).toString()
            }

            override fun onFinish() {
                currentExercisePosition++

                exerciseList[currentExercisePosition].isSelected = true
                adapter.notifyDataSetChanged()

                setupExerciseView()
            }
        }.start()
    }

    private fun setExerciseProgressBar() {
        binding.exerciseProgressBar.progress = exerciseProgress

        exerciseTimer = object : CountDownTimer(exerciseTimerDuration * 1000, 1000) {
            override fun onTick(p0: Long) {
                exerciseProgress++
                binding.exerciseProgressBar.progress = exerciseTimerDuration.toInt() - exerciseProgress
                binding.tvExerciseTimer.text = (exerciseTimerDuration.toInt() - exerciseProgress).toString()
            }

            override fun onFinish() {
                if (currentExercisePosition < exerciseList.size - 1) {
                    exerciseList[currentExercisePosition].isSelected = false
                    exerciseList[currentExercisePosition].isCompleted = true
                    adapter.notifyDataSetChanged()

                    setupRestView()
                } else {
                    finish()
                    val intent = Intent(this@ExerciseActivity, FinishActivity::class.java)
                    startActivity(intent)
                }
            }
        }.start()
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            val result = tts.setLanguage(Locale.US)
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "The Language specified is not supported")
            }
        } else {
            Log.e("TTS", "Initialization failed!")
        }
    }

    private fun speakOut(text: String) {
        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, "")
    }

    override fun onDestroy() {
        restTimer.cancel()
        restProgress = 0

        tts.stop()
        tts.shutdown()

        player.stop()

        super.onDestroy()
    }
}