package dev.pol.safetyguide.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.pol.safetyguide.data.model.Category
import dev.pol.safetyguide.data.model.ChecklistItem
import dev.pol.safetyguide.data.model.SupplyItem
import dev.pol.safetyguide.data.repository.ChecklistRepository
import dev.pol.safetyguide.data.repository.PreferencesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val repository: ChecklistRepository,
    private val preferencesRepository: PreferencesRepository
) : ViewModel() {

    private val _category = MutableStateFlow<Category?>(null)
    val category: StateFlow<Category?> = _category.asStateFlow()

    private val _items = MutableStateFlow<List<ChecklistItem>>(emptyList())
    val items: StateFlow<List<ChecklistItem>> = _items.asStateFlow()

    private val _supplies = MutableStateFlow<List<SupplyItem>>(emptyList())
    val supplies: StateFlow<List<SupplyItem>> = _supplies.asStateFlow()

    val totalItems: StateFlow<Int> = _category.flatMapLatest { cat ->
        cat?.let { repository.getTotalItemsCountCombined(it.id) } ?: flowOf(0)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)

    val completedItems: StateFlow<Int> = _category.flatMapLatest { cat ->
        cat?.let { repository.getCompletedItemsCountCombined(it.id) } ?: flowOf(0)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)

    val householdSize: StateFlow<Int> = preferencesRepository.householdSize
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), PreferencesRepository.DEFAULT_HOUSEHOLD_SIZE)

    private var currentCategoryId: String = ""

    fun loadCategory(categoryId: String) {
        currentCategoryId = categoryId
        viewModelScope.launch {
            _category.value = repository.getCategoryById(categoryId)
            repository.getItemsByCategory(categoryId).collect {
                _items.value = it
            }
        }
        viewModelScope.launch {
            repository.getSuppliesByCategory(categoryId).collect {
                _supplies.value = it
            }
        }
    }

    fun toggleItemCompletion(item: ChecklistItem, isCompleted: Boolean) {
        viewModelScope.launch {
            repository.updateItem(
                item.copy(
                    isCompleted = isCompleted,
                    completedAt = if (isCompleted) System.currentTimeMillis() else null
                )
            )
        }
    }

    fun updateSupplyQuantity(supply: SupplyItem, newQuantity: Int) {
        viewModelScope.launch {
            repository.updateSupply(supply.copy(quantity = newQuantity))
        }
    }

    fun updateSupplyExpirationDate(supply: SupplyItem, newDate: Long?) {
        viewModelScope.launch {
            repository.updateSupply(supply.copy(expirationDate = newDate))
        }
    }

    fun addItem(title: String, description: String?) {
        viewModelScope.launch {
            repository.insertItem(
                ChecklistItem(
                    id = UUID.randomUUID().toString(),
                    categoryId = currentCategoryId,
                    title = title,
                    description = description
                )
            )
        }
    }

    fun addSupply(name: String, quantity: Int, unit: String, expirationDate: Long? = null, perPersonMultiplier: Int = 0, minRequired: Int = 0) {
        viewModelScope.launch {
            repository.insertSupply(
                SupplyItem(
                    id = UUID.randomUUID().toString(),
                    categoryId = currentCategoryId,
                    name = name,
                    quantity = quantity,
                    unit = unit,
                    minRequired = minRequired,
                    expirationDate = expirationDate,
                    perPersonMultiplier = perPersonMultiplier
                )
            )
        }
    }

    fun deleteItem(item: ChecklistItem) {
        viewModelScope.launch {
            repository.deleteItem(item)
        }
    }

    fun deleteSupply(supply: SupplyItem) {
        viewModelScope.launch {
            repository.deleteSupply(supply)
        }
    }
}
