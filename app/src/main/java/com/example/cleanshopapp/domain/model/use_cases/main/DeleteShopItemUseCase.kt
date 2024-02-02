package com.example.cleanshopapp.domain.model.use_cases.main

import com.example.cleanshopapp.domain.model.ShopItemRepository
import com.example.cleanshopapp.domain.model.main.ShopItem

class DeleteShopItemUseCase(private val repository: ShopItemRepository) {

    fun deleteShopItem(shopItem: ShopItem)
    {
        repository.deleteShopItem(shopItem = shopItem)
    }

}