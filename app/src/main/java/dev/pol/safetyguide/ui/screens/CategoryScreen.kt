package dev.pol.safetyguide.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import dev.pol.safetyguide.R
import dev.pol.safetyguide.data.model.ChecklistItem
import dev.pol.safetyguide.data.model.SupplyItem
import dev.pol.safetyguide.ui.components.ChecklistItemCard
import dev.pol.safetyguide.ui.components.SupplyItemCard
import dev.pol.safetyguide.viewmodel.CategoryViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryScreen(
    categoryId: String,
    onBackClick: () -> Unit,
    viewModel: CategoryViewModel = hiltViewModel()
) {
    LaunchedEffect(categoryId) {
        viewModel.loadCategory(categoryId)
    }

    val category by viewModel.category.collectAsState()
    val items by viewModel.items.collectAsState(initial = emptyList())
    val supplies by viewModel.supplies.collectAsState(initial = emptyList())
    val completedItems by viewModel.completedItems.collectAsState(initial = 0)
    val totalItems by viewModel.totalItems.collectAsState(initial = 0)

    var showAddItemDialog by remember { mutableStateOf(false) }
    var showAddSupplyDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = category?.name ?: "")
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.back_button)
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer
                )
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = stringResource(R.string.category_progress),
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                    Text(
                        text = stringResource(R.string.progress_format, completedItems, totalItems),
                        style = MaterialTheme.typography.headlineMedium,
                        color = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                }
            }

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // Checklist items section
                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = stringResource(R.string.checklist_section),
                            style = MaterialTheme.typography.titleMedium
                        )
                        IconButton(onClick = { showAddItemDialog = true }) {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = stringResource(R.string.add_item)
                            )
                        }
                    }
                }

                items(items) { item ->
                    ChecklistItemCard(
                        item = item,
                        onCheckedChange = { isChecked ->
                            viewModel.toggleItemCompletion(item, isChecked)
                        },
                        onDelete = {
                            viewModel.deleteItem(item)
                        }
                    )
                }

                // Supply items section
                item {
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = stringResource(R.string.supplies_section),
                            style = MaterialTheme.typography.titleMedium
                        )
                        IconButton(onClick = { showAddSupplyDialog = true }) {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = stringResource(R.string.add_supply)
                            )
                        }
                    }
                }

                items(supplies) { supply ->
                    SupplyItemCard(
                        supply = supply,
                        onQuantityChange = { newQuantity ->
                            viewModel.updateSupplyQuantity(supply, newQuantity)
                        },
                        onDelete = {
                            viewModel.deleteSupply(supply)
                        },
                        onExpirationDateChange = { newDate ->
                            viewModel.updateSupplyExpirationDate(supply, newDate)
                        }
                    )
                }

                // Empty state
                if (items.isEmpty() && supplies.isEmpty()) {
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(32.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = stringResource(R.string.empty_category),
                                style = MaterialTheme.typography.bodyLarge,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            }
        }
    }

    // Add Item Dialog
    if (showAddItemDialog) {
        AddItemDialog(
            onDismiss = { showAddItemDialog = false },
            onConfirm = { title, description ->
                viewModel.addItem(title, description)
                showAddItemDialog = false
            }
        )
    }

    // Add Supply Dialog
    if (showAddSupplyDialog) {
        AddSupplyDialog(
            onDismiss = { showAddSupplyDialog = false },
            onConfirm = { name, quantity, unit, expirationDate, perPersonMultiplier, minRequired ->
                viewModel.addSupply(name, quantity, unit, expirationDate, perPersonMultiplier, minRequired)
                showAddSupplyDialog = false
            }
        )
    }
}

@Composable
fun AddItemDialog(
    onDismiss: () -> Unit,
    onConfirm: (String, String?) -> Unit
) {
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(stringResource(R.string.add_item)) },
        text = {
            Column {
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text(stringResource(R.string.item_title)) },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text(stringResource(R.string.item_description_optional)) },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = { onConfirm(title, description.ifBlank { null }) },
                enabled = title.isNotBlank()
            ) {
                Text(stringResource(R.string.dialog_add))
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(stringResource(R.string.dialog_cancel))
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun AddSupplyDialog(
    onDismiss: () -> Unit,
    onConfirm: (String, Int, String, Long?, Int, Int) -> Unit
) {
    var name by remember { mutableStateOf("") }
    var quantity by remember { mutableStateOf("0") }
    var unit by remember { mutableStateOf("szt") }
    var expirationDate by remember { mutableStateOf<Long?>(null) }
    var perPersonMultiplier by remember { mutableStateOf("0") }
    var minRequired by remember { mutableStateOf("0") }
    var showDatePicker by remember { mutableStateOf(false) }
    val units = listOf(
        stringResource(R.string.unit_szt),
        stringResource(R.string.unit_l),
        stringResource(R.string.unit_kg),
        stringResource(R.string.unit_opak),
        stringResource(R.string.unit_par),
        stringResource(R.string.unit_kpl)
    )
    val datePickerState = rememberDatePickerState()
    val dateFormat = remember { java.text.SimpleDateFormat("dd.MM.yyyy", java.util.Locale.getDefault()) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(stringResource(R.string.add_supply)) },
        text = {
            Column(
                modifier = Modifier.verticalScroll(rememberScrollState())
            ) {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text(stringResource(R.string.item_title)) },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = quantity,
                    onValueChange = { quantity = it },
                    label = { Text(stringResource(R.string.item_quantity)) },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = minRequired,
                    onValueChange = { minRequired = it },
                    label = { Text(stringResource(R.string.minimum_label)) },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(stringResource(R.string.unit_label))
                FlowRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    units.forEach { unitOption ->
                        FilterChip(
                            selected = unit == unitOption,
                            onClick = { unit = unitOption },
                            label = { Text(unitOption) }
                        )
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = expirationDate?.let { dateFormat.format(java.util.Date(it)) } ?: "",
                    onValueChange = {},
                    label = { Text(stringResource(R.string.item_expiration_optional)) },
                    readOnly = true,
                    modifier = Modifier.fillMaxWidth(),
                    trailingIcon = {
                        Row {
                            if (expirationDate != null) {
                                IconButton(onClick = { expirationDate = null }) {
                                    Icon(Icons.Default.Clear, stringResource(R.string.delete_date))
                                }
                            }
                            IconButton(onClick = { showDatePicker = true }) {
                                Icon(Icons.Default.CalendarToday, stringResource(R.string.pick_date))
                            }
                        }
                    }
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = perPersonMultiplier,
                    onValueChange = { perPersonMultiplier = it },
                    label = { Text(stringResource(R.string.per_person_multiplier)) },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = stringResource(R.string.per_person_multiplier_description),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    val qty = quantity.toIntOrNull() ?: 0
                    val multiplier = perPersonMultiplier.toIntOrNull() ?: 0
                    val min = minRequired.toIntOrNull() ?: 0
                    if (name.isNotBlank() && qty >= 0 && multiplier >= 0 && min >= 0) {
                        onConfirm(name, qty, unit, expirationDate, multiplier, min)
                    }
                },
                enabled = name.isNotBlank() && (quantity.toIntOrNull() ?: -1) >= 0
            ) {
                Text(stringResource(R.string.dialog_add))
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(stringResource(R.string.dialog_cancel))
            }
        }
    )

    if (showDatePicker) {
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(
                    onClick = {
                        datePickerState.selectedDateMillis?.let {
                            expirationDate = it
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
