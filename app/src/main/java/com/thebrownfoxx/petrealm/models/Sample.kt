package com.thebrownfoxx.petrealm.models

object Sample {
    val Pet = Pet(
        name = "Tan",
        age = 7,
        type = null,
        owner = Owner(name = "Justine")
    )

    val Pets = listOf(
        Pet,
        Pet(
            name = "Jericho",
            age = 69,
            type = null,
        )
    )
}