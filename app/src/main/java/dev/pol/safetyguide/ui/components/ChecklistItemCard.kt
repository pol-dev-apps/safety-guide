package dev.pol.safetyguide.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.pol.safetyguide.R
import dev.pol.safetyguide.data.model.ChecklistItem
import dev.pol.safetyguide.ui.theme.PrepperTheme

@Composable
fun ChecklistItemCard(
    item: ChecklistItem,
    onCheckedChange: (Boolean) -> Unit,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = if (item.isCompleted) {
                MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f)
            } else {
                MaterialTheme.colorScheme.surface
            }
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.Top
        ) {
            Checkbox(
                checked = item.isCompleted,
                onCheckedChange = onCheckedChange
            )

            Spacer(modifier = Modifier.width(8.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = item.title,
                    style = MaterialTheme.typography.bodyLarge,
                    color = if (item.isCompleted) {
                        MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                    } else {
                        MaterialTheme.colorScheme.onSurface
                    }
                )

                if (!item.description.isNullOrBlank()) {
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = item.description,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            IconButton(
                onClick = onDelete,
                modifier = Modifier.size(40.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = stringResource(R.string.item_delete),
                    tint = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ChecklistItemCardIncompletePreview() {
    PrepperTheme {
        ChecklistItemCard(
            item = ChecklistItem(
                id = "1",
                categoryId = "fire",
                title = "Gaśnica i koc gaśniczy",
                description = "Upewnij się, że masz gaśnicę ABC",
                isCompleted = false
            ),
            onCheckedChange = {},
            onDelete = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ChecklistItemCardCompletePreview() {
    PrepperTheme {
        ChecklistItemCard(
            item = ChecklistItem(
                id = "2",
                categoryId = "fire",
                title = "Czujki dymu, czadu i gazu",
                description = null,
                isCompleted = true
            ),
            onCheckedChange = {},
            onDelete = {}
        )
    }
}
