package com.example.dogedex.core.doglist

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.dogedex.core.dogdetail.DogDetailComposeActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DogListActivity : ComponentActivity() {

    //private val dogListViewModel: DogListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //enableEdgeToEdge()
        setContent {
            DogListScreen(
                onDogClicked = ::openDogDetailActivity,
                onNavigationIconClick = ::onNavigationIconClick,
            )
        }
    }

    private fun openDogDetailActivity(dog: com.example.dogedex.core.model.Dog) {
        val intent = Intent(this, DogDetailComposeActivity::class.java)
        intent.putExtra(DogDetailComposeActivity.DOG_DETAIL, dog)
        startActivity(intent)
    }

    private fun onNavigationIconClick(){
        finish()
    }
//        val binding = ActivityDocListBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.dog_frame)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }
//
//
//        val loadingWheel = binding.loadingWheel
//
//        val recycler = binding.dogRecycler
//
//        val adapter = DogAdapter()
//        recycler.layoutManager = GridLayoutManager(this, GRID_SPAN_COUNT)
//        recycler.adapter = adapter
//
//        adapter.setItemClickListener {
//            val intent = Intent(this, DogDetailComposeActivity::class.java)
//            intent.putExtra(DogDetailComposeActivity.DOG_DETAIL, it)
//            startActivity(intent)
//        }
//
//        dogListViewModel.dogList.observe(this){ dogList ->
//            adapter.submitList(dogList)
//        }
//
//        dogListViewModel.uiState.observe(this){ uiState ->
//
//            when(uiState){
//                is ApiResponseStatus.Error -> {
//                    Toast.makeText(this, uiState.messageId , Toast.LENGTH_SHORT).show()
//                    loadingWheel.visibility = View.GONE
//                }
//                is ApiResponseStatus.Loading -> {
//                    loadingWheel.visibility = View.VISIBLE
//                }
//                is ApiResponseStatus.Success -> {
//                    loadingWheel.visibility = View.GONE
//                }
//            }
//        }
//    }
}