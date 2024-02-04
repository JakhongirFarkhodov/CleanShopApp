package com.example.cleanshopapp.presentation.ui

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView
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
import com.example.cleanshopapp.presentation.ui.ShopItemActivity.Companion.newAddIntent
import com.example.cleanshopapp.presentation.ui.ShopItemActivity.Companion.newEditIntent
import com.example.cleanshopapp.presentation.viewmodel.main.MainViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity(), ShopItemFragment.OnBackPressClickListener {

    private lateinit var viewModel: MainViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var shopItemAdapter: ShopItemAdapter
    private lateinit var floatingActionButton: FloatingActionButton
    private var fragmentContainerView: FragmentContainerView? = null


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        recyclerView = findViewById(R.id.rv_shop_list)
        fragmentContainerView = findViewById(R.id.main_fragment_container_view)
        floatingActionButton = findViewById(R.id.button_add_shop_item)
        shopItemAdapter = ShopItemAdapter()
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]



        viewModel.getShopList().observe(this) {
            shopItemAdapter.submitList(it)
        }

        floatingActionButton.setOnClickListener {
            if (fragmentContainerView != null) {
                val fragment = ShopItemFragment.newAddFragment()
                changeFragment(fragment)
            } else {
                val intent = newAddIntent(this)
                startActivity(intent)
            }

        }

        applyingData()
        itemTouchHelperCallBack(recyclerView)


    }

    private fun itemTouchHelperCallBack(recyclerView: RecyclerView) {

        val callBack = object : SimpleCallback(0, LEFT or RIGHT) {
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
                if (fragmentContainerView != null) {
                    val fragment = ShopItemFragment.newEditFragment(it.id)
                    changeFragment(fragment)
                } else {
                    val intent = newEditIntent(this@MainActivity, shopItemId = it.id)
                    startActivity(intent)
                }

            }
            onShopItemLongClickListener = {
                val item = it.copy(isEnabled = !it.isEnabled)
                viewModel.editShopItem(item)
            }
        }
    }

    private fun changeFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .addToBackStack(null)
            .replace(R.id.main_fragment_container_view, fragment)
            .commit()
    }

    override fun onOnBackPressed() {
        supportFragmentManager.popBackStack()
    }
}