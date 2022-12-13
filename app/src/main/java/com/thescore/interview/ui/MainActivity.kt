package com.thescore.interview.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.thescore.interview.TeamsApplication
import com.thescore.interview.databinding.ActivityMainBinding
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private var binding: ActivityMainBinding? = null

//    private val viewModel by viewModels<MainViewModel>(factoryProducer = {
//        object : ViewModelProvider.Factory {
//            override fun <T : ViewModel> create(modelClass: Class<T>): T {
//                return when (modelClass) {
//                    MainViewModel::javaClass -> MainViewModel() as T
//                    else -> throw IllegalArgumentException("Unsupported ViewModel type")
//                }
//            }
//        }
//    })

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val repo = (application as TeamsApplication).teamRepo
        val dispatcher = (application as TeamsApplication).dispatcher
        val mainViewModel by viewModels<MainViewModel>(factoryProducer = {
            MainViewModel.createViewModel(
                repo,
                dispatcher
            )
        })

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        binding?.recyclerview?.layoutManager =
            LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)


        lifecycleScope.launch {
            mainViewModel.uiState.collect { viewState ->
                if (viewState.teams.isNotEmpty()) {
                    if (binding?.recyclerview?.adapter == null) {
                        val teamsAdapter = TeamsAdapter(viewState.teams)
                        binding?.recyclerview?.adapter = teamsAdapter
                    } else {
                        (binding?.recyclerview?.adapter as TeamsAdapter).updateItems(viewState.teams)
                    }
                }
            }
        }


        //TODO: Setup recyclerview with adapter
//        binding?.recyclerview?.adapter = TeamsAdapter()
    }
}