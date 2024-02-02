package com.example.cleanshopapp.domain.model.use_cases.shopItem

import com.example.cleanshopapp.domain.model.ShopItemRepository
import com.example.cleanshopapp.domain.model.main.ShopItem

class AddShopItemUseCase(private val repository: ShopItemRepository) {

    fun addShopItem(shopItem: ShopItem)
    {
        repository.addShopItem(shopItem = shopItem)
    }

}