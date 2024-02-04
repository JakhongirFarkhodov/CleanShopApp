package com.example.cleanshopapp.presentation.viewmodel.shopitem

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.cleanshopapp.data.ShopItemRepositoryImpl
import com.example.cleanshopapp.domain.model.main.ShopItem
import com.example.cleanshopapp.domain.model.use_cases.common.EditShopItemUseCase
import com.example.cleanshopapp.domain.model.use_cases.shopItem.AddShopItemUseCase
import com.example.cleanshopapp.domain.model.use_cases.shopItem.GetShopItemUseCase

class ShopItemViewModel : ViewModel() {

    private val repository:ShopItemRepositoryImpl = ShopItemRepositoryImpl
    private val getShopItemUseCase:GetShopItemUseCase = GetShopItemUseCase(repository)
    private val addShopItemUseCase:AddShopItemUseCase = AddShopItemUseCase(repository)
    private val editShopItemUseCase:EditShopItemUseCase = EditShopItemUseCase(repository)


    private val _shopItem = MutableLiveData<ShopItem>()
    val shopItem:LiveData<ShopItem>
        get() = _shopItem

    private val _errorInputName = MutableLiveData<Boolean>()
    val errorInputName:LiveData<Boolean>
        get() = _errorInputName

    private val _errorInputCount = MutableLiveData<Boolean>()
    val errorInputCount:LiveData<Boolean>
        get() = _errorInputCount

    private val _shouldCloseActivity = MutableLiveData<Unit>()
    val shouldCloseActivity:LiveData<Unit>
        get() = _shouldCloseActivity

    fun getShopItemById(shopItemId:Int)
    {
        _shopItem.value = getShopItemUseCase.getShopItem(shopItemId = shopItemId)
    }

    fun addShopItem(input_name:String?, input_count:String?)
    {
        val name = checkInputName(input_name)
        val count = checkInputCount(input_count)
        val checkValues = validateValue(name, count)

        if (checkValues)
        {
            val item = ShopItem(name = name, count = count, isEnabled = true)
            addShopItemUseCase.addShopItem(item)
            closeActivity()
        }

    }

    fun editShopItem(input_name: String?, input_count: String?)
    {
        val name = checkInputName(input_name)
        val count = checkInputCount(input_count)
        val checkValues = validateValue(name, count)
        if (checkValues)
        {
            _shopItem.value?.let {
                val item = it.copy(name, count)
                editShopItemUseCase.editShopItem(shopItem = item)
                closeActivity()
            }
        }
    }

    private fun checkInputName(input_name: String?):String
    {
        return input_name?.trim() ?: ""
    }

    private fun checkInputCount(input_count: String?):Int
    {
       return try {
            return input_count?.trim()?.toInt() ?: 0
        } catch (e: Exception) {
            0
        }
    }

    private fun validateValue(name:String, count:Int):Boolean
    {
        var result = true

        if (name.isBlank())
        {
            //TODO Show error of inputName
            _errorInputName.value = true
            result = false
        }
        if (count <= 0)
        {
            //TODO Show error of inputCount
            _errorInputCount.value = true
            result = false
        }

        return result
    }

    private fun closeActivity()
    {
        _shouldCloseActivity.value = Unit
    }

}