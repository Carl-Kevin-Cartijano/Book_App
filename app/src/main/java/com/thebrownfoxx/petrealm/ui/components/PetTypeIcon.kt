package com.thebrownfoxx.petrealm.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.Pets
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import com.thebrownfoxx.petrealm.R
import com.thebrownfoxx.petrealm.models.PetType

typealias PetTypeName = String

val PetTypeName.icon @Composable get() = when (this) {
    "Cat" -> ImageVector.vectorResource(R.drawable.cat)
    "Dog" -> ImageVector.vectorResource(R.drawable.dog)
    else -> Icons.TwoTone.Pets
}

val PetType.icon @Composable get() = name.icon