package com.example.cleanshopapp.domain.model.use_cases.common

import com.example.cleanshopapp.domain.model.ShopItemRepository
import com.example.cleanshopapp.domain.model.main.ShopItem

class EditShopItemUseCase(private val repository: ShopItemRepository) {

    fun editShopItem(shopItem: ShopItem)
    {
        repository.editShopItem(shopItem = shopItem)
    }

}