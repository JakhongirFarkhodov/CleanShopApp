package com.example.cleanshopapp.presentation.ui

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.cleanshopapp.R

class ShopItemActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shop_item)

        val extra = intent.getStringExtra(EXTRA_STRING_VALUE)
        Log.d("TAG", "onCreate: ${extra}")

    }


    companion object{
        private const val EXTRA_STRING_VALUE = "EXTRA_VALUE"
        private const val MODE_ADD = "mode_add"
        private const val MODE_EDIT = "mode_edit"
        private const val EXTRA_SHOP_ITEM_ID = "id"

        fun newAddIntent(context: Context):Intent{
            return Intent(context, ShopItemActivity::class.java).apply {
                putExtra(EXTRA_STRING_VALUE, MODE_ADD)
            }
        }

        fun newEditIntent(context: Context, shopItemId:Int):Intent
        {
            return Intent(context, ShopItemActivity::class.java).apply {
                putExtra(EXTRA_STRING_VALUE, MODE_EDIT)
                putExtra(EXTRA_SHOP_ITEM_ID, shopItemId)
            }
        }
    }
}