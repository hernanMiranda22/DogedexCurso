package com.example.dogedex.doglist

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dogedex.R
import com.example.dogedex.databinding.ActivityDocListBinding

class DogListActivity : AppCompatActivity() {

    private val dogListViewModel : DogListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityDocListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.dog_recycler)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        val recycler = binding.dogRecycler

        val adapter = DogAdapter()
        recycler.layoutManager = LinearLayoutManager(this)
        recycler.adapter = adapter

        dogListViewModel.dogList.observe(this){ dogList ->
            adapter.submitList(dogList)
        }
    }
}