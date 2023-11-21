package com.thebrownfoxx.petrealm.ui.components

import androidx.compose.ui.unit.Density
import com.ramcosta.composedestinations.animations.defaults.RootNavGraphDefaultAnimations
import com.thebrownfoxx.components.animation.sharedXAxisEnter
import com.thebrownfoxx.components.animation.sharedXAxisExit

fun getDefaultTransitions(density: Density) = with(density) {
    RootNavGraphDefaultAnimations(
        enterTransition = { sharedXAxisEnter() },
        exitTransition = { sharedXAxisExit() },
        popEnterTransition = { sharedXAxisEnter(reversed = true) },
        popExitTransition = { sharedXAxisExit(reversed = true) },
    )
}