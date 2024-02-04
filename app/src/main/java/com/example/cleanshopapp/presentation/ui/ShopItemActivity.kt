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
import com.example.cleanshopapp.presentation.viewmodel.shopitem.ShopItemViewModel
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class ShopItemActivity : AppCompatActivity() {

    private var screen_mode = SCREEN_MODE
    private var shopItemId = UNDEFINED_ID

    private lateinit var tilName:TextInputLayout
    private lateinit var tilCount:TextInputLayout
    private lateinit var tieName:TextInputEditText
    private lateinit var tieCount:TextInputEditText

    private lateinit var viewModel: ShopItemViewModel
    private lateinit var save_button:MaterialButton


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shop_item)

        parseIntent()

        tilName = findViewById(R.id.til_name)
        tilCount = findViewById(R.id.til_count)
        tieName = findViewById(R.id.et_name)
        tieCount = findViewById(R.id.et_count)
        save_button = findViewById(R.id.save_button)

        viewModel = ViewModelProvider(this)[ShopItemViewModel::class.java]

        when(screen_mode)
        {
            MODE_ADD ->{
                launchAddMode()
            }
            MODE_EDIT ->{
                launchEditMode()
            }
        }

        viewModel.shopItem.observe(this){
            val name = it.name
            val count = it.count.toString()
            tieName.setText(name)
            tieCount.setText(count)
        }

        viewModel.errorInputName.observe(this){
            if (it)
            {
                tilName.error = getString(R.string.error_input_name)
            }
            else{
                tilName.error = null
            }
        }

        viewModel.errorInputCount.observe(this){
            if (it)
            {
                tilCount.error = getString(R.string.error_input_count)
            }
            else{
                tilCount.error = null
            }
        }

        viewModel.shouldCloseActivity.observe(this){
            finish()
        }


        save_button.setOnClickListener {
            val name = tieName.text.toString()
            val count = tieCount.text.toString()
            when(screen_mode)
            {
                MODE_ADD ->{
                    viewModel.addShopItem(name, count)
                }
                MODE_EDIT ->{
                    viewModel.editShopItem(name, count)
                }
            }
        }




    }

    private fun launchAddMode() {
        checkTextChange()
    }

    private fun launchEditMode() {
        checkTextChange()
        viewModel.getShopItemById(shopItemId = shopItemId)
    }

    private fun checkTextChange() {
        tieName.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                viewModel.resetInputNameError()
            }

            override fun afterTextChanged(p0: Editable?) {

            }
        })

        tieCount.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                viewModel.resetInputCountError()
            }

            override fun afterTextChanged(p0: Editable?) {
            }
        })
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