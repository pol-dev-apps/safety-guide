package dev.pol.safetyguide.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.pol.safetyguide.data.model.CategoryWithProgress
import dev.pol.safetyguide.data.repository.ChecklistRepository
import dev.pol.safetyguide.data.repository.PreferencesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: ChecklistRepository,
    private val preferencesRepository: PreferencesRepository
) : ViewModel() {

    val householdSize: StateFlow<Int> = preferencesRepository.householdSize
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), PreferencesRepository.DEFAULT_HOUSEHOLD_SIZE)

    val categories: StateFlow<List<CategoryWithProgress>> = repository.getAllCategories()
        .flatMapLatest { categories ->
            // For each category, observe its items count changes
            val categoryProgressFlows = categories.map { category ->
                combine(
                    repository.getTotalItemsCountCombined(category.id),
                    repository.getCompletedItemsCountCombined(category.id)
                ) { total, completed ->
                    CategoryWithProgress(
                        category = category,
                        totalItems = total,
                        completedItems = completed
                    )
                }
            }
            combine(categoryProgressFlows) { it.toList() }
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val totalItems: StateFlow<Int> = repository.getTotalItemsCombined()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)

    val completedItems: StateFlow<Int> = repository.getCompletedItemsCombined()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)
}
