package com.thebrownfoxx.petrealm.models

data class Pet (
    val id: String = "",
    val name: String,
    val age: Int,
    val type: PetType,
    val owner: Owner? = null,
)