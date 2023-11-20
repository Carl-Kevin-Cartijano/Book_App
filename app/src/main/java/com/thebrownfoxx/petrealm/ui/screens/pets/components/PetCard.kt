package com.thebrownfoxx.petrealm.ui.screens.pets.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.DismissValue.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SwipeToDismiss
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDismissState
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PetCard(
    pet: Pet,
    onRemove: () -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(),
) {
    val dismissState = rememberDismissState(
        confirmValueChange = { dismissValue ->
            val dismissed = dismissValue == DismissedToStart || dismissValue == DismissedToEnd
            if (dismissed) onRemove()
            true
        }
    )

    val ownerLabel =
        if (pet.owner != null) "${pet.owner.name}'s ${pet.type.name.lowercase()}"
        else "Stray ${pet.type.name.lowercase()}"

    SwipeToDismiss(
        modifier = modifier,
        state = dismissState,
        background = {},
        dismissContent = {
            Card(modifier = Modifier.padding(contentPadding)) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                ) {
                    Icon(
                        imageVector = when (pet.type.name) {
                            "Cat" -> ImageVector.vectorResource(R.drawable.cat)
                            else -> ImageVector.vectorResource(R.drawable.dog)
                        },
                        contentDescription = null,
                        modifier = Modifier.size(32.dp)
                    )
                    HorizontalSpacer(width = 16.dp)
                    Column(modifier = Modifier.weight(1f)) {
                        Row {
                            Text(
                                text = pet.name,
                                style = MaterialTheme.typography.titleMedium,
                            )
                            Text(
                                text = " ${pet.age}",
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
        },
    )
}

@Preview
@Composable
fun PetCardPreview() {
    AppTheme {
        PetCard(
            pet = Sample.Pet,
            onRemove = {},
            contentPadding = PaddingValues(16.dp),
        )
    }
}