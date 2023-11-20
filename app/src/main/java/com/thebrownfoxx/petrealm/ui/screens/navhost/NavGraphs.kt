package com.thebrownfoxx.petrealm.ui.screens.navhost

import com.ramcosta.composedestinations.annotation.NavGraph

@NavGraph
annotation class PetNavGraph(
    val start: Boolean = false,
)

@NavGraph
annotation class OwnerNavGraph(
    val start: Boolean = false,
)