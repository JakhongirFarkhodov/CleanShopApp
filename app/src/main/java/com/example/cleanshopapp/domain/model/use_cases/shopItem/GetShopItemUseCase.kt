package com.example.cleanshopapp.domain.model.use_cases.shopItem

import com.example.cleanshopapp.domain.model.ShopItemRepository
import com.example.cleanshopapp.domain.model.main.ShopItem

class GetShopItemUseCase(private val repository: ShopItemRepository) {

    fun getShopItem(shopItemId:Int):ShopItem{
        return repository.getShopItem(shopItemId = shopItemId)
    }

}