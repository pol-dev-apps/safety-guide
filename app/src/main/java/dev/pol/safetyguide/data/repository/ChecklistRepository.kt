package dev.pol.safetyguide.data.repository

import dev.pol.safetyguide.data.db.ChecklistDao
import dev.pol.safetyguide.data.db.SeedData
import dev.pol.safetyguide.data.model.*
import dev.pol.safetyguide.data.util.SupplyCalculator
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ChecklistRepository @Inject constructor(
    private val dao: ChecklistDao
) {
    // Categories
    fun getAllCategories(): Flow<List<Category>> = dao.getAllCategories()

    suspend fun getCategoryById(categoryId: String): Category? = dao.getCategoryById(categoryId)

    // Checklist Items
    fun getItemsByCategory(categoryId: String): Flow<List<ChecklistItem>> =
        dao.getItemsByCategory(categoryId)

    suspend fun getItemById(itemId: String): ChecklistItem? = dao.getItemById(itemId)

    fun getTotalItemsCount(categoryId: String): Flow<Int> = dao.getTotalItemsCount(categoryId)

    fun getCompletedItemsCount(categoryId: String): Flow<Int> = dao.getCompletedItemsCount(categoryId)

    suspend fun insertItem(item: ChecklistItem) = dao.insertItem(item)

    suspend fun updateItem(item: ChecklistItem) = dao.updateItem(item)

    suspend fun deleteItem(item: ChecklistItem) = dao.deleteItem(item)

    // Supply Items
    fun getSuppliesByCategory(categoryId: String): Flow<List<SupplyItem>> =
        dao.getSuppliesByCategory(categoryId)

    suspend fun getSupplyById(supplyId: String): SupplyItem? = dao.getSupplyById(supplyId)

    suspend fun insertSupply(supply: SupplyItem) = dao.insertSupply(supply)

    suspend fun updateSupply(supply: SupplyItem) = dao.updateSupply(supply)

    suspend fun deleteSupply(supply: SupplyItem) = dao.deleteSupply(supply)

    // Statistics
    fun getTotalItems(): Flow<Int> = dao.getTotalItems()

    fun getCompletedItems(): Flow<Int> = dao.getCompletedItems()

    // Combined counts (checklist + supplies)
    fun getTotalItemsCountCombined(categoryId: String): Flow<Int> = dao.getTotalItemsCountCombined(categoryId)

    fun getCompletedItemsCountCombined(categoryId: String): Flow<Int> = dao.getCompletedItemsCountCombined(categoryId)

    fun getTotalItemsCombined(): Flow<Int> = dao.getTotalItemsCombined()

    fun getCompletedItemsCombined(): Flow<Int> = dao.getCompletedItemsCombined()

    // Export/Import
    suspend fun exportData(): Triple<List<Category>, List<ChecklistItem>, List<SupplyItem>> {
        return Triple(
            dao.getAllCategoriesForExport(),
            dao.getAllItemsForExport(),
            dao.getAllSuppliesForExport()
        )
    }

    suspend fun importData(
        categories: List<Category>,
        items: List<ChecklistItem>,
        supplies: List<SupplyItem>
    ) {
        dao.importData(categories, items, supplies)
    }

    // Recalculate supply minRequired based on household size and days
    suspend fun recalculateSupplyMinRequired(householdSize: Int, homeDays: Int, evacDays: Int) {
        val allSupplies = dao.getAllSuppliesForExport()
        val recalculatedSupplies = SupplyCalculator.recalculateAllSupplies(allSupplies, householdSize, homeDays, evacDays)
        dao.updateSupplies(recalculatedSupplies)
    }

    // Reset - clears everything and restores default data
    suspend fun resetProgress() {
        // Delete all existing data
        dao.deleteAllItems()
        dao.deleteAllSupplies()

        // Re-insert seed data
        dao.insertCategories(SeedData.getCategories())
        dao.insertItems(SeedData.getChecklistItems())
        dao.insertSupplies(SeedData.getSupplyItems())
    }

}
