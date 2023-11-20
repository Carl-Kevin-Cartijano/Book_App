package com.thebrownfoxx.petrealm.ui.screens.pets.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.thebrownfoxx.petrealm.models.PetType

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun PetTypeDropdownMenu(
    selectedPetType: PetType?,
    petTypes: List<PetType>,
    expanded: Boolean,
    onExpandedChange: (Boolean) -> Unit,
    onPetTypeChange: (PetType) -> Unit,
    hasWarning: Boolean,
    modifier: Modifier = Modifier,
) {
    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = onExpandedChange,
        modifier = modifier,
    ) {
        Column {
            TextField(
                readOnly = true,
                value = selectedPetType?.name ?: "",
                onValueChange = {},
                label = { Text("Type") },
                isError = hasWarning,
                singleLine = true,
                colors = ExposedDropdownMenuDefaults.textFieldColors(),
                modifier = Modifier.menuAnchor(),
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                },
            )
            AnimatedVisibility(visible = hasWarning) {
                Text(
                    text = "Required",
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.labelSmall,
                    modifier = Modifier.padding(start = 16.dp, top = 4.dp),
                )
            }
        }
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { onExpandedChange(false) }
        ) {
            for (petType in petTypes) {
                DropdownMenuItem(
                    text = { Text(text = petType.name) },
                    onClick = { onPetTypeChange(petType) },
                )
            }
        }
    }
}