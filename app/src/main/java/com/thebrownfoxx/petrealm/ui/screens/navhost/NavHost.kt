package com.thebrownfoxx.petrealm.ui.screens.navhost

import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.ramcosta.composedestinations.DestinationsNavHost
import com.thebrownfoxx.petrealm.ui.screens.NavGraphs

@Composable
fun NavHost(modifier: Modifier = Modifier) {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = { NavigationBar(navController = navController) },
        modifier = modifier.imePadding(),
    ) { contentPadding ->
        DestinationsNavHost(
            navGraph = NavGraphs.root,
            navController = navController,
            modifier = Modifier.padding(contentPadding),
        )
    }
}