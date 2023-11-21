package com.thebrownfoxx.petrealm.ui.components

sealed class RemoveDialogState<T> {
    class Hidden<T>() : RemoveDialogState<T>()
    data class Pending<T>(val value: T) : RemoveDialogState<T>()
    class Canceled<T>() : RemoveDialogState<T>()
    class Confirmed<T>() : RemoveDialogState<T>()
}

class RemoveDialogStateChangeListener<T>(
    val onInitiateRemove: suspend (T) -> Boolean,
    val onCancelRemove: () -> Unit,
    val onRemove: () -> Unit,
)