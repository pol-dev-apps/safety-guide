package dev.pol.safetyguide.viewmodel

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.pol.safetyguide.data.repository.BackupRepository
import dev.pol.safetyguide.data.repository.BackupResult
import dev.pol.safetyguide.data.repository.ChecklistRepository
import dev.pol.safetyguide.data.repository.PreferencesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class OperationResult {
    data object Idle : OperationResult()
    data object Success : OperationResult()
    data class Error(val messageRes: Int, val formatArgs: Array<Any>? = null) : OperationResult()
}

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val preferencesRepository: PreferencesRepository,
    private val backupRepository: BackupRepository,
    private val checklistRepository: ChecklistRepository,
    @ApplicationContext private val context: Context
) : ViewModel() {

    private val _exportResult = MutableStateFlow<OperationResult>(OperationResult.Idle)
    val exportResult: StateFlow<OperationResult> = _exportResult.asStateFlow()

    private val _importResult = MutableStateFlow<OperationResult>(OperationResult.Idle)
    val importResult: StateFlow<OperationResult> = _importResult.asStateFlow()

    val householdSize: StateFlow<Int> = preferencesRepository.householdSize
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), PreferencesRepository.DEFAULT_HOUSEHOLD_SIZE)

    val homeDays: StateFlow<Int> = preferencesRepository.homeDays
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), PreferencesRepository.DEFAULT_HOME_DAYS)

    val evacDays: StateFlow<Int> = preferencesRepository.evacDays
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), PreferencesRepository.DEFAULT_EVAC_DAYS)

    val isDisclaimerAccepted: StateFlow<Boolean> = preferencesRepository.isDisclaimerAccepted
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)

    fun acceptDisclaimer() {
        viewModelScope.launch {
            preferencesRepository.acceptDisclaimer()
        }
    }

    fun updateHouseholdSize(newSize: Int) {
        viewModelScope.launch {
            preferencesRepository.updateHouseholdSize(newSize)
            checklistRepository.recalculateSupplyMinRequired(newSize, homeDays.value, evacDays.value)
        }
    }

    fun updateHomeDays(days: Int) {
        viewModelScope.launch {
            preferencesRepository.updateHomeDays(days)
            checklistRepository.recalculateSupplyMinRequired(householdSize.value, days, evacDays.value)
        }
    }

    fun updateEvacDays(days: Int) {
        viewModelScope.launch {
            preferencesRepository.updateEvacDays(days)
            checklistRepository.recalculateSupplyMinRequired(householdSize.value, homeDays.value, days)
        }
    }

    fun exportData(context: Context, uri: Uri) {
        viewModelScope.launch {
            _exportResult.value = OperationResult.Idle
            when (val result = backupRepository.exportData(context, uri)) {
                is BackupResult.Success -> _exportResult.value = OperationResult.Success
                is BackupResult.Error -> _exportResult.value = OperationResult.Error(result.messageRes, result.formatArgs)
            }
        }
    }

    suspend fun readDataFromUri(context: Context, uri: Uri): String? {
        return backupRepository.readDataFromUri(context, uri)
    }

    fun importData(json: String) {
        viewModelScope.launch {
            _importResult.value = OperationResult.Idle
            when (val result = backupRepository.importData(json)) {
                is BackupResult.Success -> _importResult.value = OperationResult.Success
                is BackupResult.Error -> _importResult.value = OperationResult.Error(result.messageRes, result.formatArgs)
            }
        }
    }

    fun resetProgress() {
        viewModelScope.launch {
            checklistRepository.resetProgress()
            checklistRepository.recalculateSupplyMinRequired(householdSize.value, homeDays.value, evacDays.value)
        }
    }

    fun clearExportResult() {
        _exportResult.value = OperationResult.Idle
    }

    fun clearImportResult() {
        _importResult.value = OperationResult.Idle
    }
}

