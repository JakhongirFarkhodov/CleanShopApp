package com.example.cleanshopapp.presentation.ui

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.example.cleanshopapp.R
import com.example.cleanshopapp.domain.model.main.ShopItem
import com.example.cleanshopapp.domain.model.main.ShopItem.Companion.UNDEFINED_ID
import com.example.cleanshopapp.presentation.ui.ShopItemFragment.Companion.newAddFragment
import com.example.cleanshopapp.presentation.ui.ShopItemFragment.Companion.newEditFragment
import com.example.cleanshopapp.presentation.viewmodel.shopitem.ShopItemViewModel
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class ShopItemActivity : AppCompatActivity() {

    private var screen_mode = SCREEN_MODE
    private var shopItemId = UNDEFINED_ID


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shop_item)

        parseIntent()

        if (savedInstanceState == null) {
            launchFragment()
        }


    }

    override fun onStart() {
        super.onStart()
        Log.d("TAG", "ShopItemActivity onStart: ")
    }

    override fun onPause() {
        super.onPause()
        Log.d("TAG", "ShopItemActivity onPause: ")
    }

    override fun onStop() {
        super.onStop()
        Log.d("TAG", "ShopItemActivity onStop: ")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d("TAG", "ShopItemActivity onRestart: ")
    }

    override fun onResume() {
        super.onResume()
        Log.d("TAG", "ShopItemActivity onResume: ")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("TAG", "ShopItemActivity onDestroy: ")
    }

    private fun launchFragment() {
        val fragment = when(screen_mode)
        {
            MODE_ADD ->{
                newAddFragment()
            }
            MODE_EDIT ->{
                newEditFragment(shopItemId = shopItemId)
            }
            else ->{
                throw RuntimeException("Cannot find screen mode")
            }
        }

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container_view, fragment).commit()
    }

    private fun parseIntent()
    {
        if (!intent.hasExtra(EXTRA_STRING_VALUE))
        {
            throw RuntimeException("Intent key does not found${EXTRA_STRING_VALUE}")
        }

        val mode = intent.getStringExtra(EXTRA_STRING_VALUE)
        if (mode != MODE_ADD && mode != MODE_EDIT)
        {
            throw RuntimeException("Undefined mode ${mode}")
        }

        screen_mode = mode
        if (screen_mode == MODE_EDIT)
        {
            if (!intent.hasExtra(EXTRA_SHOP_ITEM_ID))
            {
                throw RuntimeException("Screen mode edit does not find shopItemId")
            }
            shopItemId = intent.getIntExtra(EXTRA_SHOP_ITEM_ID, -1)
        }

    }

    companion object{
        private const val EXTRA_STRING_VALUE = "EXTRA_VALUE"
        private const val MODE_ADD = "mode_add"
        private const val MODE_EDIT = "mode_edit"
        private const val EXTRA_SHOP_ITEM_ID = "id"
        private const val SCREEN_MODE = ""

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