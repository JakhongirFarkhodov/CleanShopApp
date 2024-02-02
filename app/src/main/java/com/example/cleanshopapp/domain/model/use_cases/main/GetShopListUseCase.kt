package com.example.cleanshopapp.domain.model.use_cases.main

import androidx.lifecycle.LiveData
import com.example.cleanshopapp.domain.model.ShopItemRepository
import com.example.cleanshopapp.domain.model.main.ShopItem

class GetShopListUseCase(private val repository: ShopItemRepository) {

    fun getShopList():LiveData<List<ShopItem>>
    {
        return repository.getShopList()
    }

}