package com.thebrownfoxx.petrealm.models

object Sample {
    val Pet = Pet(
        name = "Tan",
        age = 7,
        type = PetType(name = "Cat"),
        owner = Owner(name = "Justine")
    )

    val Pets = listOf(
        Pet,
        Pet(
            name = "Jericho",
            age = 69,
            type = PetType(name = "Dog"),
        )
    )
}