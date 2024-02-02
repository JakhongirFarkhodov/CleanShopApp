package com.example.cleanshopapp.domain.model.main

data class ShopItem(
    private val name:String,
    private val count:Int,
    private val isEnabled:Boolean,
    private var id:Int = UNDEFINED_ID
)
{
    companion object{
        const val UNDEFINED_ID = -1
    }
}
