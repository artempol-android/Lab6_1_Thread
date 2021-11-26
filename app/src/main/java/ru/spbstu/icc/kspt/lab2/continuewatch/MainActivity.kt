package ru.spbstu.icc.kspt.lab2.continuewatch

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    var secondsElapsed: Int = 0
    lateinit var textSecondsElapsed: TextView
    private lateinit var backgroundThread: Thread

    @SuppressLint("SetTextI18n")
    private fun createNewThread() {
        backgroundThread = Thread {
            while (!Thread.currentThread().isInterrupted) {
                try {
                    Log.d("Thread: ", Thread.currentThread().name)
                    Thread.sleep(1000)
                    textSecondsElapsed.post {
                        textSecondsElapsed.text = "Seconds elapsed: " + secondsElapsed++
                    }
                } catch (e: InterruptedException) {
                    Thread.currentThread().interrupt()
                    Log.d("Thread was interrupted: ", Thread.currentThread().name)
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        textSecondsElapsed = findViewById(R.id.textSecondsElapsed)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        secondsElapsed = savedInstanceState.getInt("seconds")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt("seconds", secondsElapsed)
        super.onSaveInstanceState(outState)
    }

    override fun onStop() {
        super.onStop()
        backgroundThread.interrupt()
    }

    override fun onStart() {
        super.onStart()
        createNewThread()
        backgroundThread.start()
    }
}


