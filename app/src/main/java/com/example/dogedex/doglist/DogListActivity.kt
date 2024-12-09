package com.example.dogedex.doglist

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dogedex.R
import com.example.dogedex.api.ApiResponseStatus
import com.example.dogedex.databinding.ActivityDocListBinding
import com.example.dogedex.dogdetail.DogDetailActivity
import com.example.dogedex.dogdetail.DogDetailActivity.Companion.DOG_DETAIL

private const val GRID_SPAN_COUNT = 3

class DogListActivity : AppCompatActivity() {

    private val dogListViewModel : DogListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityDocListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.dog_frame)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        val loadingWheel = binding.loadingWheel

        val recycler = binding.dogRecycler

        val adapter = DogAdapter()
        recycler.layoutManager = GridLayoutManager(this, GRID_SPAN_COUNT)
        recycler.adapter = adapter

        adapter.setItemClickListener {
            val intent = Intent(this, DogDetailActivity::class.java)
            intent.putExtra(DOG_DETAIL, it)
            startActivity(intent)
        }

        dogListViewModel.dogList.observe(this){ dogList ->
            adapter.submitList(dogList)
        }

        dogListViewModel.uiState.observe(this){ uiState ->

            when(uiState){
                is ApiResponseStatus.Error -> {
                    Toast.makeText(this, uiState.messageId , Toast.LENGTH_SHORT).show()
                    loadingWheel.visibility = View.GONE
                }
                is ApiResponseStatus.Loading -> {
                    loadingWheel.visibility = View.VISIBLE
                }
                is ApiResponseStatus.Success -> {
                    loadingWheel.visibility = View.GONE
                }
            }
        }
    }
}