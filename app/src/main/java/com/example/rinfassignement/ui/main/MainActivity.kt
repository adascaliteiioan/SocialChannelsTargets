package com.example.rinfassignement.ui.main

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.rinfassignement.databinding.ActivityMainBinding
import com.example.rinfassignement.util.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        lifecycleScope.launchWhenStarted {
            viewModel.channels.collect { resource ->
                when (resource) {
                    is Resource.Loading -> {
                    }
                    is Resource.Error -> {
                        Toast.makeText(
                            this@MainActivity,
                            resource.error?.localizedMessage,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    is Resource.Success -> {

                    }
                }
            }
        }
    }
}