package com.example.cleanshopapp.presentation.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DiffUtil.ItemCallback
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ItemTouchHelper.*
import androidx.recyclerview.widget.RecyclerView
import com.example.cleanshopapp.R
import com.example.cleanshopapp.domain.model.main.ShopItem
import com.example.cleanshopapp.presentation.adapter.ShopItemAdapter
import com.example.cleanshopapp.presentation.adapter.ShopItemAdapter.Companion.VIEW_TYPE_DISABLED
import com.example.cleanshopapp.presentation.adapter.ShopItemAdapter.Companion.VIEW_TYPE_ENABLED
import com.example.cleanshopapp.presentation.viewmodel.main.MainViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var shopItemAdapter: ShopItemAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        recyclerView = findViewById(R.id.rv_shop_list)
        shopItemAdapter = ShopItemAdapter()
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]



        viewModel.getShopList().observe(this){
            shopItemAdapter.submitList(it)
        }



        applyingData()
        itemTouchHelperCallBack(recyclerView)


    }

    private fun itemTouchHelperCallBack(recyclerView: RecyclerView) {

        val callBack = object : SimpleCallback(0, LEFT or RIGHT)
        {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val item = shopItemAdapter.currentList[viewHolder.adapterPosition]
                viewModel.deleteShopItem(item)
            }

        }

        val itemTouchHelper = ItemTouchHelper(callBack)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

    private fun applyingData() {
        recyclerView.apply {
            adapter = shopItemAdapter
            recycledViewPool.setMaxRecycledViews(VIEW_TYPE_ENABLED, 30)
            recycledViewPool.setMaxRecycledViews(VIEW_TYPE_DISABLED, 30)
        }

        shopItemAdapter.apply {
            onShopItemClickListener = {
                Toast.makeText(this@MainActivity, "${it.name}", Toast.LENGTH_SHORT).show()
            }
            onShopItemLongClickListener = {
                val item = it.copy(isEnabled = !it.isEnabled)
                viewModel.editShopItem(item)
            }
        }
    }
}