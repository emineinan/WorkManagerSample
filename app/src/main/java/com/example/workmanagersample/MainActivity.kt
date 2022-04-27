package com.example.workmanagersample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.work.*
import com.example.workmanagersample.databinding.ActivityMainBinding
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var request: WorkRequest

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var quantity = binding.textViewCount.text.toString().toInt()

        binding.buttonDecrease.setOnClickListener {
            if (quantity > 1) {
                quantity--
            }
            binding.textViewCount.text = quantity.toString()
        }

        binding.buttonIncrease.setOnClickListener {
            quantity++
            binding.textViewCount.text = quantity.toString()
        }

        binding.buttonOk.setOnClickListener {
            val workCondition =
                Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()
            request = PeriodicWorkRequestBuilder<WaterWorker>(
                quantity.toLong(),
                TimeUnit.HOURS
            ).setConstraints(workCondition).build()

            WorkManager.getInstance(this).enqueue(request)
        }
    }
}