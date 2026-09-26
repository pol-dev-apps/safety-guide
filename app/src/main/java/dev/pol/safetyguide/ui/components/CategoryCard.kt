package dev.pol.safetyguide.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.pol.safetyguide.R
import dev.pol.safetyguide.data.model.Category
import dev.pol.safetyguide.ui.theme.CompletedGreen
import dev.pol.safetyguide.ui.theme.IncompleteGray
import dev.pol.safetyguide.ui.theme.PrepperTheme

@Composable
fun CategoryCard(
    category: Category,
    totalItems: Int,
    completedItems: Int,
    onClick: () -> Unit
) {
    val progress = if (totalItems > 0) completedItems.toFloat() / totalItems else 0f

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.weight(1f)
            ) {
                // Category icon
                Text(
                    text = category.icon,
                    style = MaterialTheme.typography.displayLarge,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Category name
                Text(
                    text = category.name,
                    style = MaterialTheme.typography.titleMedium,
                    textAlign = TextAlign.Center,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }

            // Progress section - always at bottom
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                SquareProgressIndicator(
                    progress = progress,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(8.dp),
                    progressColor = CompletedGreen,
                    trackColor = IncompleteGray,
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = stringResource(R.string.progress_format, completedItems, totalItems),
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 170, heightDp = 200)
@Composable
private fun CategoryCardPreview() {
    PrepperTheme {
        CategoryCard(
            category = Category(
                id = "fire",
                name = "Pożar",
                icon = "🔥",
                sortOrder = 1
            ),
            totalItems = 10,
            completedItems = 6,
            onClick = {}
        )
    }
}

@Preview(showBackground = true, widthDp = 170, heightDp = 200)
@Composable
private fun CategoryCardEmptyPreview() {
    PrepperTheme {
        CategoryCard(
            category = Category(
                id = "flood",
                name = "Powódź",
                icon = "🌊",
                sortOrder = 2
            ),
            totalItems = 0,
            completedItems = 0,
            onClick = {}
        )
    }
}
