package dev.pol.safetyguide.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.pol.safetyguide.R
import dev.pol.safetyguide.data.model.SupplyItem
import dev.pol.safetyguide.ui.theme.CompletedGreen
import dev.pol.safetyguide.ui.theme.IncompleteGray
import dev.pol.safetyguide.ui.theme.PrepperTheme
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SupplyItemCard(
    supply: SupplyItem,
    onQuantityChange: (Int) -> Unit,
    onDelete: () -> Unit,
    onExpirationDateChange: (Long?) -> Unit
) {
    val isComplete = supply.quantity >= supply.minRequired && supply.minRequired > 0
    var showDatePicker by remember { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = supply.expirationDate
    )
    val dateFormat = remember { SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()) }

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = if (isComplete) {
                CompletedGreen.copy(alpha = 0.1f)
            } else {
                MaterialTheme.colorScheme.surface
            }
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Supply info
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = supply.name,
                        style = MaterialTheme.typography.bodyLarge,
                        color = if (isComplete) {
                            CompletedGreen
                        } else {
                            MaterialTheme.colorScheme.onSurface
                        }
                    )

                if (supply.minRequired > 0) {
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = stringResource(R.string.supply_minimum, supply.minRequired, supply.unit),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    if (!isComplete) {
                        val remaining = supply.minRequired - supply.quantity
                        Text(
                            text = stringResource(R.string.supply_below_minimum, remaining, supply.unit),
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.error
                        )
                    } else {
                        Text(
                            text = stringResource(R.string.supply_above_minimum),
                            style = MaterialTheme.typography.bodySmall,
                            color = CompletedGreen
                        )
                    }
                }
                if (supply.perPersonMultiplier > 0) {
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = stringResource(R.string.supply_scaling, supply.perPersonMultiplier),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
                }

                // Quantity controls
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    IconButton(
                        onClick = {
                            if (supply.quantity > 0) {
                                onQuantityChange(supply.quantity - 1)
                            }
                        },
                        enabled = supply.quantity > 0
                    ) {
                        Icon(
                            imageVector = Icons.Default.Remove,
                            contentDescription = stringResource(R.string.accessibility_quantity_decrease),
                            tint = if (supply.quantity > 0) {
                                MaterialTheme.colorScheme.primary
                            } else {
                                IncompleteGray
                            }
                        )
                    }

                    Text(
                        text = "${supply.quantity}",
                        style = MaterialTheme.typography.headlineSmall,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    Text(
                        text = supply.unit,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    IconButton(
                        onClick = { onQuantityChange(supply.quantity + 1) }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = stringResource(R.string.accessibility_quantity_increase),
                            tint = MaterialTheme.colorScheme.primary
                        )
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

            // Expiration date row
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.CalendarToday,
                    contentDescription = null,
                    modifier = Modifier.size(16.dp),
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.width(8.dp))

                if (supply.expirationDate != null) {
                    Text(
                        text = stringResource(R.string.supply_expiration, dateFormat.format(Date(supply.expirationDate))),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier
                            .weight(1f)
                            .clickable { showDatePicker = true }
                    )
                    IconButton(
                        onClick = { onExpirationDateChange(null) },
                        modifier = Modifier.size(24.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Clear,
                            contentDescription = stringResource(R.string.delete_date),
                            modifier = Modifier.size(16.dp),
                            tint = MaterialTheme.colorScheme.error
                        )
                    }
                } else {
                    Text(
                        text = stringResource(R.string.add_expiration_date),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier
                            .weight(1f)
                            .clickable { showDatePicker = true }
                    )
                }
            }
        }
    }

    if (showDatePicker) {
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(
                    onClick = {
                        datePickerState.selectedDateMillis?.let {
                            onExpirationDateChange(it)
                        }
                        showDatePicker = false
                    }
                ) {
                    Text(stringResource(R.string.dialog_ok))
                }
            },
            dismissButton = {
                TextButton(onClick = { showDatePicker = false }) {
                    Text(stringResource(R.string.dialog_cancel))
                }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SupplyItemCardIncompletePreview() {
    PrepperTheme {
        SupplyItemCard(
            supply = SupplyItem(
                id = "ss1",
                categoryId = "home_supplies",
                name = "Woda butelkowana",
                quantity = 2,
                unit = "L",
                minRequired = 9,
                expirationDate = null,
                perPersonMultiplier = 0
            ),
            onQuantityChange = {},
            onDelete = {},
            onExpirationDateChange = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun SupplyItemCardCompletePreview() {
    PrepperTheme {
        SupplyItemCard(
            supply = SupplyItem(
                id = "ss1",
                categoryId = "home_supplies",
                name = "Woda butelkowana",
                quantity = 12,
                unit = "L",
                minRequired = 9,
                expirationDate = System.currentTimeMillis() + 86400000 * 30,
                perPersonMultiplier = 0
            ),
            onQuantityChange = {},
            onDelete = {},
            onExpirationDateChange = {}
        )
    }
}
