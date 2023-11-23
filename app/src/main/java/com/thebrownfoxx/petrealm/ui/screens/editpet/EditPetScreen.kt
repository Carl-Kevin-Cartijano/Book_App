package com.thebrownfoxx.petrealm.ui.screens.editpet

import androidx.compose.animation.core.AnimationState
import androidx.compose.animation.core.animateTo
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusProperties
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.thebrownfoxx.components.ExpandedTopAppBar
import com.thebrownfoxx.components.FilledButton
import com.thebrownfoxx.components.HorizontalSpacer
import com.thebrownfoxx.components.IconButton
import com.thebrownfoxx.components.VerticalSpacer
import com.thebrownfoxx.components.extension.bottom
import com.thebrownfoxx.components.extension.end
import com.thebrownfoxx.components.extension.plus
import com.thebrownfoxx.components.extension.start
import com.thebrownfoxx.components.extension.top
import com.thebrownfoxx.petrealm.models.PetType
import com.thebrownfoxx.petrealm.ui.components.PetCard
import com.thebrownfoxx.petrealm.ui.components.TextField
import com.thebrownfoxx.petrealm.ui.components.bringIntoViewOnFocus
import com.thebrownfoxx.petrealm.ui.components.indicationlessClickable
import com.thebrownfoxx.petrealm.ui.screens.pets.components.PetTypeDropdownMenu
import com.thebrownfoxx.petrealm.ui.theme.AppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditPetScreen(
    petTypes: List<PetType>,
    state: EditPetState,
    stateChangeListener: EditPetStateChangeListener,
    onNavigateUp: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val focusRequesters = remember { Array(5) { FocusRequester() } }
    val focusManger = LocalFocusManager.current
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

    fun Modifier.fieldModifier(focusIndex: Int) = bringIntoViewOnFocus {
        val topAppBarState = scrollBehavior.state
        AnimationState(initialValue = topAppBarState.heightOffset).animateTo(
            targetValue = topAppBarState.heightOffsetLimit,
            animationSpec = scrollBehavior.snapAnimationSpec!!,
        ) { topAppBarState.heightOffset = value }
    }
        .focusRequester(focusRequesters[focusIndex])
        .focusProperties {
            next = focusRequesters.getOrElse(focusIndex + 1) { focusRequesters.first() }
        }

    Scaffold(
        modifier = Modifier
            .nestedScroll(scrollBehavior.nestedScrollConnection)
            .indicationlessClickable { focusManger.clearFocus() },
        topBar = {
            ExpandedTopAppBar(
                collapsedContent = { Text(text = "Edit pet") },
                navigationIcon = {
                    IconButton(
                        imageVector = Icons.TwoTone.ArrowBack,
                        contentDescription = null,
                        onClick = onNavigateUp,
                    )
                },
                scrollBehavior = scrollBehavior,
            ) {
                PetCard(
                    petName = state.petName,
                    petAge = state.petAge?.toString() ?: "",
                    petType = state.petType?.name ?: "",
                    ownerName = state.ownerName,
                    modifier = Modifier.padding(horizontal = 16.dp),
                )
            }
        },
    ) { contentPadding ->
        val padding = contentPadding + PaddingValues(16.dp)

        Column(
            modifier = modifier
                .padding(start = padding.start, end = padding.end)
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
        ) {
            VerticalSpacer(height = padding.top)
            TextField(
                label = "Name",
                value = state.petName,
                onValueChange = stateChangeListener.onPetNameChange,
                required = true,
                error = if (state.hasPetNameWarning) "Required" else null,
                modifier = Modifier.fieldModifier(focusIndex = 0),
            )
            VerticalSpacer(height = 16.dp)
            Row {
                TextField(
                    label = "Age",
                    value = state.petAge?.toString() ?: "",
                    onValueChange = stateChangeListener.onPetAgeChange,
                    required = true,
                    error = if (state.hasPetAgeWarning) "Required" else null,
                    numeric = true,
                    modifier = Modifier
                        .weight(1f)
                        .fieldModifier(focusIndex = 1),
                )
                HorizontalSpacer(width = 16.dp)
                PetTypeDropdownMenu(
                    selectedPetType = state.petType,
                    petTypes = petTypes,
                    expanded = state.petTypeDropdownExpanded,
                    onExpandedChange = stateChangeListener.onPetTypeDropdownExpandedChange,
                    onPetTypeChange = stateChangeListener.onPetTypeChange,
                    hasWarning = state.hasPetTypeWarning,
                    modifier = Modifier
                        .weight(2f)
                        .fieldModifier(focusIndex = 2),
                )
            }
            VerticalSpacer(height = 16.dp)
            TextField(
                label = "Owner name",
                value = state.ownerName,
                onValueChange = stateChangeListener.onOwnerNameChange,
                modifier = Modifier.fieldModifier(focusIndex = 3),
            )
            VerticalSpacer(height = 16.dp)
            FilledButton(
                text = "Save",
                onClick = stateChangeListener.onEditPet,
                modifier = Modifier
                    .fillMaxWidth()
                    .fieldModifier(focusIndex = 4),
            )
            VerticalSpacer(height = padding.bottom)
        }
    }
}

@Preview
@Composable
fun EditPetScreenPreview() {
    AppTheme {
//        EditPetScreen(
//            petTypes = listOf(),
//            state = EditPetState(),
//            stateChangeListener = EditPetStateChangeListener(
//                onPetNameChange = {},
//                onPetAgeChange = {},
//                onPetTypeDropdownExpandedChange = {},
//                onPetTypeChange = {},
//                onOwnerNameChange = {},
//                onEditPet = {},
//            ),
//            onNavigateUp = {},
//        )
    }
}