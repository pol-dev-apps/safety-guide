package dev.pol.safetyguide.ui.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import dev.pol.safetyguide.R
import dev.pol.safetyguide.data.model.EmergencyNumber
import dev.pol.safetyguide.data.model.EmergencyNumbers

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmergencyScreen() {
    val context = LocalContext.current
    var showCallDialog by remember { mutableStateOf(false) }
    var selectedNumber by remember { mutableStateOf<EmergencyNumber?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(R.string.emergency_title))
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.errorContainer
                )
            )
        }
    ) { paddingValues ->
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            items(
                items = EmergencyNumbers.numbers,
                key = { it.number }
            ) { emergency ->
                EmergencyCard(
                    emergency = emergency,
                    onClick = {
                        selectedNumber = emergency
                        showCallDialog = true
                    }
                )
            }
        }
    }

    // Call confirmation dialog
    if (showCallDialog && selectedNumber != null) {
        AlertDialog(
            onDismissRequest = { showCallDialog = false },
            title = { Text(stringResource(R.string.emergency_call)) },
            text = {
                Text(
                    stringResource(
                        R.string.emergency_confirm,
                        selectedNumber!!.number,
                        selectedNumber!!.name
                    )
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        val intent = Intent(Intent.ACTION_DIAL).apply {
                            data = Uri.parse("tel:${selectedNumber!!.number}")
                        }
                        context.startActivity(intent)
                        showCallDialog = false
                    }
                ) {
                    Text(stringResource(R.string.emergency_call))
                }
            },
            dismissButton = {
                TextButton(onClick = { showCallDialog = false }) {
                    Text(stringResource(R.string.dialog_cancel))
                }
            }
        )
    }
}

@Composable
fun EmergencyCard(
    emergency: EmergencyNumber,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.errorContainer
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = emergency.number,
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onErrorContainer,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Column {
                Text(
                    text = emergency.name,
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.onErrorContainer,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = emergency.description,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onErrorContainer.copy(alpha = 0.7f),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}
