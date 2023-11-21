package com.thebrownfoxx.petrealm.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.thebrownfoxx.components.FilledButton
import com.thebrownfoxx.components.FilledTonalButton
import com.thebrownfoxx.components.HorizontalSpacer
import com.thebrownfoxx.components.extension.Elevation
import com.thebrownfoxx.petrealm.models.Pet
import com.thebrownfoxx.petrealm.models.Sample
import com.thebrownfoxx.petrealm.ui.theme.AppTheme

@Composable
fun PetCardContent(
    petName: String,
    petAge: String,
    petType: String,
    ownerName: String,
    modifier: Modifier = Modifier,
) {
    val ownerLabel =
        if (ownerName.isNotEmpty()) "${ownerName}'s ${petType.lowercase().ifEmpty { "pet" }}"
        else "Unowned ${petType.lowercase().ifEmpty { "animal" }}"

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier,
    ) {
        Icon(
            imageVector = petType.icon,
            contentDescription = null,
            modifier = Modifier.size(32.dp)
        )
        HorizontalSpacer(width = 16.dp)
        Column(modifier = Modifier.weight(1f)) {
            Row {
                Text(
                    text = petName.ifEmpty { "Pet name" },
                    style = MaterialTheme.typography.titleMedium,
                )
                Text(
                    text = " $petAge",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Normal,
                )
            }
            Text(
                text = ownerLabel,
                style = MaterialTheme.typography.bodyMedium,
            )
        }
    }
}

@Composable
fun PetCard(
    petName: String,
    petAge: String,
    petType: String,
    ownerName: String,
    modifier: Modifier = Modifier,
) {
    Card(modifier = modifier) {
        PetCardContent(
            petName = petName,
            petAge = petAge,
            petType = petType,
            ownerName = ownerName,
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PetCard(
    pet: Pet,
    expanded: Boolean,
    onClick: () -> Unit,
    onAdopt: () -> Unit,
    onInitiateRemove: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier,
        onClick = onClick,
    ) {
        PetCardContent(
            petName = pet.name,
            petAge = pet.age.toString(),
            petType = pet.type.name,
            ownerName = pet.owner?.name ?: "",
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
        )
        AnimatedVisibility(visible = expanded) {
            Surface(tonalElevation = Elevation.level(1)) {
                Row(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                ) {
                    FilledTonalButton(
                        text = "Remove",
                        onClick = onInitiateRemove,
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.filledTonalButtonColors(
                            containerColor = colorScheme.errorContainer,
                            contentColor = colorScheme.onErrorContainer,
                        )
                    )
                    if (pet.owner == null) {
                        HorizontalSpacer(width = 16.dp)
                        FilledButton(
                            text = "Adopt",
                            onClick = onAdopt,
                            modifier = Modifier.weight(1f),
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun PetCardPreview() {
    AppTheme {
        PetCard(
            pet = Sample.Pet,
            expanded = false,
            onClick = {},
            onAdopt = {},
            onInitiateRemove = {},
            modifier = Modifier.padding(16.dp),
        )
    }
}

@Preview
@Composable
fun PetCardExpandedPreview() {
    AppTheme {
        PetCard(
            pet = Sample.Pet.copy(owner = null),
            expanded = true,
            onClick = {},
            onAdopt = {},
            onInitiateRemove = {},
            modifier = Modifier.padding(16.dp),
        )
    }
}