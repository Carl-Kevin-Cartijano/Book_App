package com.thebrownfoxx.petrealm.ui.screens.navhost

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.ramcosta.composedestinations.DestinationsNavHost
import com.thebrownfoxx.components.extension.minus

@Composable
fun NavHost(modifier: Modifier = Modifier) {
    var navigationBarGraph by rememberSaveable { mutableStateOf(NavigationBarGraph.Pet) }

    Scaffold(
        bottomBar = {
            NavigationBar(
                currentNavGraph = navigationBarGraph,
                onNavGraphChange = { navigationBarGraph = it },
            )
        },
        modifier = modifier.imePadding(),
    ) { contentPadding ->
        AnimatedContent(targetState = navigationBarGraph, label = "") { graph ->
            DestinationsNavHost(
                navGraph = graph.graph,
                modifier = Modifier.padding(
                    contentPadding - WindowInsets.systemBars.asPaddingValues()
                ),
            )
        }
    }
}