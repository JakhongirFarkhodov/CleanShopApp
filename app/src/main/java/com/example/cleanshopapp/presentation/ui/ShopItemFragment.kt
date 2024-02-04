package com.example.cleanshopapp.presentation.ui

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.cleanshopapp.R
import com.example.cleanshopapp.domain.model.main.ShopItem
import com.example.cleanshopapp.domain.model.main.ShopItem.Companion.UNDEFINED_ID
import com.example.cleanshopapp.presentation.viewmodel.shopitem.ShopItemViewModel
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class ShopItemFragment : Fragment() {
    private var screen_mode = SCREEN_MODE
    private var shopItemId = UNDEFINED_ID

    private lateinit var tilName: TextInputLayout
    private lateinit var tilCount: TextInputLayout
    private lateinit var tieName: TextInputEditText
    private lateinit var tieCount: TextInputEditText

    private lateinit var viewModel: ShopItemViewModel
    private lateinit var save_button: MaterialButton
    private lateinit var onBackPressClickListener: OnBackPressClickListener


    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnBackPressClickListener)
        {
            onBackPressClickListener = context
        }
        else{
            throw RuntimeException("Activity $context must implement OnBackPressClickListener")
        }
        Log.d("TAG", "onAttach: ShopItemFragment" + " ${requireArguments().hashCode()}")
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            Log.d("TAG", "onCreate: ShopItemFragment")
        }
        checkArguments()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("TAG", "onCreateView: ")

        return inflater.inflate(R.layout.fragment_shop_item, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("TAG", "onViewCreated: ")

        tilName = view.findViewById(R.id.til_name)
        tilCount = view.findViewById(R.id.til_count)
        tieName = view.findViewById(R.id.et_name)
        tieCount = view.findViewById(R.id.et_count)
        save_button = view.findViewById(R.id.save_button)


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

        viewModel.shopItem.observe(viewLifecycleOwner){
            val name = it.name
            val count = it.count.toString()
            tieName.setText(name)
            tieCount.setText(count)
        }

        viewModel.errorInputName.observe(viewLifecycleOwner){
            if (it)
            {
                tilName.error = getString(R.string.error_input_name)
            }
            else{
                tilName.error = null
            }
        }

        viewModel.errorInputCount.observe(viewLifecycleOwner){
            if (it)
            {
                tilCount.error = getString(R.string.error_input_count)
            }
            else{
                tilCount.error = null
            }
        }

        viewModel.shouldCloseActivity.observe(viewLifecycleOwner){
            onBackPressClickListener.onOnBackPressed()
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

    private fun checkArguments()
    {
        arguments?.let {
            if (!it.containsKey(STRING_VALUE))
            {
                throw RuntimeException("Cannot find arguments")
            }

            val mode = it.getString(STRING_VALUE)
            if (mode != MODE_ADD && mode != MODE_EDIT)
            {
                throw RuntimeException("UNDEFINED MODE")
            }

            screen_mode = mode

            if (screen_mode == MODE_EDIT)
            {
                if (!it.containsKey(SHOP_ITEM_ID))
                {
                    throw RuntimeException("Screen mode edit can't find shop item id")
                }
                shopItemId = it.getInt(SHOP_ITEM_ID, -1)
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
        tieName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                viewModel.resetInputNameError()
            }

            override fun afterTextChanged(p0: Editable?) {

            }
        })

        tieCount.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                viewModel.resetInputCountError()
            }

            override fun afterTextChanged(p0: Editable?) {
            }
        })
    }


    override fun onPause() {
        super.onPause()
        Log.d("TAG", "onPause: ShopItemFragment")
    }

    override fun onStop() {
        super.onStop()
        Log.d("TAG", "onStop: ShopItemFragment")
    }

    override fun onResume() {
        super.onResume()
        Log.d("TAG", "onResume: ShopItemFragment")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d("TAG", "onDestroyView: ShopItemFragment")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("TAG", "onDestroy: ShopItemFragment")
    }

    override fun onDetach() {
        super.onDetach()
        Log.d("TAG", "onDetach: ShopItemFragment")
    }

    companion object
    {
        private const val STRING_VALUE = "STRING_VALUE"
        private const val MODE_ADD = "mode_add"
        private const val MODE_EDIT = "mode_edit"
        private const val SHOP_ITEM_ID = "id"
        private const val SCREEN_MODE = ""

        fun newAddFragment():Fragment
        {
            return ShopItemFragment().apply {
                arguments = Bundle().apply {
                    putString(STRING_VALUE, MODE_ADD)
                }

            }
        }

        fun newEditFragment(shopItemId:Int):Fragment
        {
            return ShopItemFragment().apply {
                arguments = Bundle().apply {
                    putString(STRING_VALUE, MODE_EDIT)
                    putInt(SHOP_ITEM_ID, shopItemId)
                }
            }
        }
    }

    interface OnBackPressClickListener{
        fun onOnBackPressed()
    }

}