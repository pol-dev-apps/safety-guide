package dev.pol.safetyguide.ui.screens

import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Article
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import dev.pol.safetyguide.R
import dev.pol.safetyguide.data.util.SupplyCalculator
import dev.pol.safetyguide.ui.theme.EvacuationCardBackground
import dev.pol.safetyguide.ui.theme.EvacuationCardContent
import dev.pol.safetyguide.viewmodel.OperationResult
import dev.pol.safetyguide.viewmodel.SettingsViewModel
import kotlinx.coroutines.launch

private const val MAX_HOUSEHOLD_SIZE = 50
private const val MAX_HOME_DAYS = 30
private const val MAX_EVAC_DAYS = 7

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    var showResetDialog by remember { mutableStateOf(false) }
    var showImportDialog by remember { mutableStateOf(false) }
    var showAboutDialog by remember { mutableStateOf(false) }
    var importData by remember { mutableStateOf<String?>(null) }
    val householdSize by viewModel.householdSize.collectAsState()
    val homeDays by viewModel.homeDays.collectAsState()
    val evacDays by viewModel.evacDays.collectAsState()
    val exportResult by viewModel.exportResult.collectAsState()
    val importResult by viewModel.importResult.collectAsState()

    // Handle export result
    LaunchedEffect(exportResult) {
        when (val result = exportResult) {
            is OperationResult.Success -> {
                scope.launch {
                    snackbarHostState.showSnackbar(context.getString(R.string.alert_export_success))
                }
                viewModel.clearExportResult()
            }
            is OperationResult.Error -> {
                scope.launch {
                    val message = if (result.formatArgs != null) {
                        context.getString(result.messageRes, *result.formatArgs)
                    } else {
                        context.getString(result.messageRes)
                    }
                    snackbarHostState.showSnackbar(message)
                }
                viewModel.clearExportResult()
            }
            else -> {}
        }
    }

    // Handle import result
    LaunchedEffect(importResult) {
        when (val result = importResult) {
            is OperationResult.Success -> {
                scope.launch {
                    snackbarHostState.showSnackbar(context.getString(R.string.alert_import_success))
                }
                viewModel.clearImportResult()
            }
            is OperationResult.Error -> {
                scope.launch {
                    val message = if (result.formatArgs != null) {
                        context.getString(result.messageRes, *result.formatArgs)
                    } else {
                        context.getString(result.messageRes)
                    }
                    snackbarHostState.showSnackbar(message)
                }
                viewModel.clearImportResult()
            }
            else -> {}
        }
    }

    val exportLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.CreateDocument("application/json")
    ) { uri ->
        uri?.let { viewModel.exportData(context, it) }
    }

    val importLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument()
    ) { uri ->
        uri?.let {
            scope.launch {
                val data = viewModel.readDataFromUri(context, it)
                if (data != null) {
                    importData = data
                    showImportDialog = true
                } else {
                    snackbarHostState.showSnackbar(context.getString(R.string.alert_import_read_error))
                }
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.settings_title)) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
        ) {
            // Household Size
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = stringResource(R.string.settings_household_size),
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        IconButton(
                            onClick = {
                                if (householdSize > 1) {
                                    viewModel.updateHouseholdSize(householdSize - 1)
                                }
                            },
                            enabled = householdSize > 1
                        ) {
                            Icon(
                                imageVector = Icons.Default.Remove,
                                contentDescription = stringResource(R.string.accessibility_decrease),
                                tint = if (householdSize > 1) {
                                    MaterialTheme.colorScheme.onPrimaryContainer
                                } else {
                                    MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.3f)
                                }
                            )
                        }

                        Text(
                            text = "$householdSize",
                            style = MaterialTheme.typography.headlineLarge,
                            modifier = Modifier.padding(horizontal = 16.dp),
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )

                        Spacer(modifier = Modifier.width(8.dp))

                        IconButton(
                            onClick = {
                                if (householdSize < MAX_HOUSEHOLD_SIZE) {
                                    viewModel.updateHouseholdSize(householdSize + 1)
                                }
                            },
                            enabled = householdSize < MAX_HOUSEHOLD_SIZE
                        ) {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = stringResource(R.string.accessibility_increase),
                                tint = if (householdSize < MAX_HOUSEHOLD_SIZE) {
                                    MaterialTheme.colorScheme.onPrimaryContainer
                                } else {
                                    MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.3f)
                                }
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = stringResource(R.string.settings_household_description),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.7f),
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                }
            }

            HorizontalDivider()

            // Home Days
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer
                )
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = stringResource(R.string.settings_home_days),
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        IconButton(
                            onClick = {
                                if (homeDays > 1) {
                                    viewModel.updateHomeDays(homeDays - 1)
                                }
                            },
                            enabled = homeDays > 1
                        ) {
                            Icon(
                                imageVector = Icons.Default.Remove,
                                contentDescription = stringResource(R.string.accessibility_decrease),
                                tint = if (homeDays > 1) {
                                    MaterialTheme.colorScheme.onSecondaryContainer
                                } else {
                                    MaterialTheme.colorScheme.onSecondaryContainer.copy(alpha = 0.3f)
                                }
                            )
                        }

                        Text(
                            text = "$homeDays",
                            style = MaterialTheme.typography.headlineLarge,
                            modifier = Modifier.padding(horizontal = 16.dp),
                            color = MaterialTheme.colorScheme.onSecondaryContainer
                        )

                        Spacer(modifier = Modifier.width(8.dp))

                        IconButton(
                            onClick = {
                                if (homeDays < MAX_HOME_DAYS) {
                                    viewModel.updateHomeDays(homeDays + 1)
                                }
                            },
                            enabled = homeDays < MAX_HOME_DAYS
                        ) {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = stringResource(R.string.accessibility_increase),
                                tint = if (homeDays < MAX_HOME_DAYS) {
                                    MaterialTheme.colorScheme.onSecondaryContainer
                                } else {
                                    MaterialTheme.colorScheme.onSecondaryContainer.copy(alpha = 0.3f)
                                }
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = stringResource(R.string.settings_min_home_days, homeDays, pluralStringResource(R.plurals.days, homeDays)),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSecondaryContainer.copy(alpha = 0.7f),
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                }
            }

            HorizontalDivider()

            // Evacuation Days
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = EvacuationCardBackground
                )
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = stringResource(R.string.settings_evac_days),
                        style = MaterialTheme.typography.titleMedium,
                        color = EvacuationCardContent
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        IconButton(
                            onClick = {
                                if (evacDays > 1) {
                                    viewModel.updateEvacDays(evacDays - 1)
                                }
                            },
                            enabled = evacDays > 1
                        ) {
                            Icon(
                                imageVector = Icons.Default.Remove,
                                contentDescription = stringResource(R.string.accessibility_decrease),
                                tint = if (evacDays > 1) {
                                    EvacuationCardContent
                                } else {
                                    EvacuationCardContent.copy(alpha = 0.3f)
                                }
                            )
                        }

                        Text(
                            text = "$evacDays",
                            style = MaterialTheme.typography.headlineLarge,
                            modifier = Modifier.padding(horizontal = 16.dp),
                            color = EvacuationCardContent
                        )

                        Spacer(modifier = Modifier.width(8.dp))

                        IconButton(
                            onClick = {
                                if (evacDays < MAX_EVAC_DAYS) {
                                    viewModel.updateEvacDays(evacDays + 1)
                                }
                            },
                            enabled = evacDays < MAX_EVAC_DAYS
                        ) {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = stringResource(R.string.accessibility_increase),
                                tint = if (evacDays < MAX_EVAC_DAYS) {
                                    EvacuationCardContent
                                } else {
                                    EvacuationCardContent.copy(alpha = 0.3f)
                                }
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = stringResource(R.string.settings_min_evac_days, evacDays, pluralStringResource(R.plurals.days, evacDays)),
                        style = MaterialTheme.typography.bodySmall,
                        color = EvacuationCardContent.copy(alpha = 0.7f),
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                }
            }

            HorizontalDivider()

            // Export section
            ListItem(
                headlineContent = { Text(stringResource(R.string.settings_export)) },
                supportingContent = { Text(stringResource(R.string.settings_export_description)) },
                leadingContent = {
                    Icon(
                        imageVector = Icons.Default.Upload,
                        contentDescription = null
                    )
                },
                modifier = Modifier.clickable {
                    exportLauncher.launch("prepper_backup.json")
                }
            )

            HorizontalDivider()

            // Import section
            ListItem(
                headlineContent = { Text(stringResource(R.string.settings_import)) },
                supportingContent = { Text(stringResource(R.string.settings_import_description)) },
                leadingContent = {
                    Icon(
                        imageVector = Icons.Default.Download,
                        contentDescription = null
                    )
                },
                modifier = Modifier.clickable {
                    importLauncher.launch(arrayOf("application/json"))
                }
            )

            HorizontalDivider()

            // Reset section
            ListItem(
                headlineContent = { Text(stringResource(R.string.settings_reset)) },
                supportingContent = { Text(stringResource(R.string.settings_reset_description)) },
                leadingContent = {
                    Icon(
                        imageVector = Icons.Default.Refresh,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.error
                    )
                },
                modifier = Modifier.clickable {
                    showResetDialog = true
                }
            )

            HorizontalDivider()

            // About section
            ListItem(
                headlineContent = { Text(stringResource(R.string.settings_about)) },
                supportingContent = { Text(stringResource(R.string.settings_version)) },
                leadingContent = {
                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = null
                    )
                },
                modifier = Modifier.clickable {
                    showAboutDialog = true
                }
            )

            HorizontalDivider()

            // Info card
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                )
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = stringResource(R.string.home_title),
                        style = MaterialTheme.typography.titleMedium
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = stringResource(R.string.info_description),
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = stringResource(R.string.settings_version),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = stringResource(R.string.about_license),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }

    // Reset confirmation dialog
    if (showResetDialog) {
        AlertDialog(
            onDismissRequest = { showResetDialog = false },
            title = { Text(stringResource(R.string.dialog_reset_title)) },
            text = {
                Text(stringResource(R.string.dialog_reset_text))
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        viewModel.resetProgress()
                        showResetDialog = false
                    },
                    colors = ButtonDefaults.textButtonColors(
                        contentColor = MaterialTheme.colorScheme.error
                    )
                ) {
                    Text(stringResource(R.string.dialog_reset_confirm_button))
                }
            },
            dismissButton = {
                TextButton(onClick = { showResetDialog = false }) {
                    Text(stringResource(R.string.dialog_cancel))
                }
            }
        )
    }

    // Import confirmation dialog
    if (showImportDialog && importData != null) {
        AlertDialog(
            onDismissRequest = { showImportDialog = false },
            title = { Text(stringResource(R.string.dialog_import_title)) },
            text = {
                Text(stringResource(R.string.dialog_import_text))
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        importData?.let { viewModel.importData(it) }
                        showImportDialog = false
                    }
                ) {
                    Text(stringResource(R.string.dialog_import_confirm_button))
                }
            },
            dismissButton = {
                TextButton(onClick = { showImportDialog = false }) {
                    Text(stringResource(R.string.dialog_cancel))
                }
            }
        )
    }

    // About dialog
    if (showAboutDialog) {
        AlertDialog(
            onDismissRequest = { showAboutDialog = false },
            title = {
                Text(
                    text = stringResource(R.string.home_title),
                    style = MaterialTheme.typography.titleLarge
                )
            },
            text = {
                Column(
                    modifier = Modifier.verticalScroll(rememberScrollState())
                ) {
                    Text(
                        text = stringResource(R.string.disclaimer_description),
                        style = MaterialTheme.typography.bodyMedium
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = stringResource(R.string.disclaimer_not_official),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.error,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = stringResource(R.string.disclaimer_informational),
                        style = MaterialTheme.typography.bodyMedium
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = stringResource(R.string.disclaimer_source),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = stringResource(R.string.settings_version),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = stringResource(R.string.about_license),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            },
            confirmButton = {
                TextButton(onClick = { showAboutDialog = false }) {
                    Text(stringResource(R.string.dialog_close))
                }
            }
        )
    }
}
