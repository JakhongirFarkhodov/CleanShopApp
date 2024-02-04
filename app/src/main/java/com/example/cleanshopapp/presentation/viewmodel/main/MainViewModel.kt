package com.example.cleanshopapp.presentation.viewmodel.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.cleanshopapp.data.ShopItemRepositoryImpl
import com.example.cleanshopapp.domain.model.main.ShopItem
import com.example.cleanshopapp.domain.model.use_cases.common.EditShopItemUseCase
import com.example.cleanshopapp.domain.model.use_cases.main.DeleteShopItemUseCase
import com.example.cleanshopapp.domain.model.use_cases.main.GetShopListUseCase

class MainViewModel : ViewModel() {

    private val repository:ShopItemRepositoryImpl = ShopItemRepositoryImpl

    private val getShopListUseCase:GetShopListUseCase = GetShopListUseCase(repository)
    private val deleteShopItemUseCase:DeleteShopItemUseCase = DeleteShopItemUseCase(repository)
    private val editShopItemUseCase:EditShopItemUseCase = EditShopItemUseCase(repository)

    fun getShopList():LiveData<List<ShopItem>>{
        return getShopListUseCase.getShopList()
    }

    fun deleteShopItem(shopItem: ShopItem)
    {
        deleteShopItemUseCase.deleteShopItem(shopItem = shopItem)
    }

    fun editShopItem(shopItem: ShopItem)
    {
        editShopItemUseCase.editShopItem(shopItem = shopItem)
    }


}