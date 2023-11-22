package com.thebrownfoxx.petrealm.ui.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.focus.focusTarget
import androidx.compose.ui.focus.onFocusChanged
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
fun Modifier.bringIntoViewOnFocus(onFocus: suspend () -> Unit = {}) = composed {
    val bringIntoViewRequester = remember { BringIntoViewRequester() }
    val coroutineScope = rememberCoroutineScope()
    bringIntoViewRequester(bringIntoViewRequester)
        .onFocusChanged {
            if (it.isFocused) {
                coroutineScope.launch {
                    // This sends a request to all parents that asks them to scroll so
                    // that this item is brought into view.
                    onFocus()
                    bringIntoViewRequester.bringIntoView()
                }
            }
        }
        .focusTarget()
}