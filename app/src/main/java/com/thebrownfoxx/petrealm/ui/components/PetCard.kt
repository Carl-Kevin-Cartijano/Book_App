package com.thebrownfoxx.petrealm.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.Pets
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.thebrownfoxx.components.HorizontalSpacer
import com.thebrownfoxx.petrealm.R
import com.thebrownfoxx.petrealm.models.Pet
import com.thebrownfoxx.petrealm.models.Sample
import com.thebrownfoxx.petrealm.ui.theme.AppTheme

@Composable
fun PetCard(
    petName: String,
    petAge: String,
    petType: String,
    ownerName: String,
    modifier: Modifier = Modifier,
) {
    val ownerLabel =
        if (ownerName.isNotEmpty()) "${ownerName}'s ${petType.lowercase().ifEmpty { "pet" }}"
        else "Unowned ${petType.lowercase().ifEmpty { "animal" }}"

    Card(modifier = modifier) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
        ) {
            Icon(
                imageVector = when (petType) {
                    "Cat" -> ImageVector.vectorResource(R.drawable.cat)
                    "Dog" -> ImageVector.vectorResource(R.drawable.dog)
                    else -> Icons.TwoTone.Pets
                },
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
}

@Composable
fun PetCard(
    pet: Pet,
    modifier: Modifier = Modifier,
) {
    PetCard(
        petName = pet.name,
        petAge = pet.age.toString(),
        petType = pet.type.name,
        ownerName = pet.owner?.name ?: "",
        modifier = modifier,
    )
}

@Preview
@Composable
fun PetCardPreview() {
    AppTheme {
        PetCard(pet = Sample.Pet, modifier = Modifier.padding(16.dp))
    }
}