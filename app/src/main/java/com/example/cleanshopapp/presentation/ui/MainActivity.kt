package com.example.cleanshopapp.presentation.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.example.cleanshopapp.R
import com.example.cleanshopapp.domain.model.main.ShopItem
import com.example.cleanshopapp.presentation.viewmodel.main.MainViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        var isDeleted = true
        viewModel.getShopList().observe(this){
            Log.d("TAG", "onCreate: ${it}")
            if (isDeleted){
                val item = it[1].copy(isEnabled = !it[1].isEnabled)
                viewModel.editShopItem(item)
                isDeleted = false
            }
        }

    }
}