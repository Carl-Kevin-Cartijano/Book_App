package com.thebrownfoxx.petrealm.ui.screens.owners.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.thebrownfoxx.components.HorizontalSpacer
import com.thebrownfoxx.components.VerticalSpacer
import com.thebrownfoxx.petrealm.models.Owner
import com.thebrownfoxx.petrealm.models.Sample
import com.thebrownfoxx.petrealm.ui.components.icon
import com.thebrownfoxx.petrealm.ui.theme.AppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OwnerCard(
    owner: Owner,
    expanded: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier,
        onClick = onClick,
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
        ) {
            Text(
                text = owner.name,
                style = typography.titleMedium,
            )
            AnimatedVisibility(visible = !expanded) {
                Text(
                    text = "${owner.pets.size} pets",
                    style = typography.bodyMedium,
                )
            }
            AnimatedVisibility(visible = expanded && owner.pets.isNotEmpty()) {
                Column {
                    VerticalSpacer(height = 8.dp)
                    Text(
                        text = "Pets",
                        style = typography.titleSmall,
                    )
                    for (pet in owner.pets) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = pet.type.icon,
                                contentDescription = null,
                                modifier = Modifier.size(16.dp),
                            )
                            HorizontalSpacer(width = 4.dp)
                            Text(
                                text = pet.name,
                                style = typography.bodySmall,
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun OwnerCardPreview() {
    AppTheme {
        OwnerCard(
            owner = Sample.Owner,
            expanded = false,
            onClick = {},
        )
    }
}

@Preview
@Composable
fun OwnerCardExpandedPreview() {
    AppTheme {
        OwnerCard(
            owner = Sample.Owner,
            expanded = true,
            onClick = {},
        )
    }
}