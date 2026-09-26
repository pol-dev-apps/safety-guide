package dev.pol.safetyguide.data.db

import androidx.room.*
import dev.pol.safetyguide.data.model.Category
import dev.pol.safetyguide.data.model.ChecklistItem
import dev.pol.safetyguide.data.model.SupplyItem
import kotlinx.coroutines.flow.Flow

@Dao
interface ChecklistDao {
    // Categories
    @Query("SELECT * FROM categories ORDER BY sortOrder")
    fun getAllCategories(): Flow<List<Category>>

    @Query("SELECT * FROM categories WHERE id = :categoryId")
    suspend fun getCategoryById(categoryId: String): Category?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategory(category: Category)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategories(categories: List<Category>)

    @Delete
    suspend fun deleteCategory(category: Category)

    @Query("DELETE FROM categories")
    suspend fun deleteAllCategories()

    // Checklist Items
    @Query("SELECT * FROM checklist_items WHERE categoryId = :categoryId")
    fun getItemsByCategory(categoryId: String): Flow<List<ChecklistItem>>

    @Query("SELECT * FROM checklist_items WHERE id = :itemId")
    suspend fun getItemById(itemId: String): ChecklistItem?

    @Query("SELECT COUNT(*) FROM checklist_items WHERE categoryId = :categoryId")
    fun getTotalItemsCount(categoryId: String): Flow<Int>

    @Query("SELECT COUNT(*) FROM checklist_items WHERE categoryId = :categoryId AND isCompleted = 1")
    fun getCompletedItemsCount(categoryId: String): Flow<Int>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItem(item: ChecklistItem)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItems(items: List<ChecklistItem>)

    @Update
    suspend fun updateItem(item: ChecklistItem)

    @Delete
    suspend fun deleteItem(item: ChecklistItem)

    @Query("DELETE FROM checklist_items WHERE categoryId = :categoryId")
    suspend fun deleteItemsByCategory(categoryId: String)

    @Query("DELETE FROM checklist_items")
    suspend fun deleteAllItems()

    // Supply Items
    @Query("SELECT * FROM supply_items WHERE categoryId = :categoryId")
    fun getSuppliesByCategory(categoryId: String): Flow<List<SupplyItem>>

    @Query("SELECT * FROM supply_items WHERE id = :supplyId")
    suspend fun getSupplyById(supplyId: String): SupplyItem?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSupply(supply: SupplyItem)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSupplies(supplies: List<SupplyItem>)

    @Update
    suspend fun updateSupply(supply: SupplyItem)

    @Update
    suspend fun updateSupplies(supplies: List<SupplyItem>)

    @Delete
    suspend fun deleteSupply(supply: SupplyItem)

    @Query("DELETE FROM supply_items WHERE categoryId = :categoryId")
    suspend fun deleteSuppliesByCategory(categoryId: String)

    @Query("DELETE FROM supply_items")
    suspend fun deleteAllSupplies()

    // Statistics
    @Query("SELECT COUNT(*) FROM checklist_items")
    fun getTotalItems(): Flow<Int>

    @Query("SELECT COUNT(*) FROM checklist_items WHERE isCompleted = 1")
    fun getCompletedItems(): Flow<Int>

    // Combined counts (checklist + supplies)
    @Query("""
        SELECT 
            (SELECT COUNT(*) FROM checklist_items WHERE categoryId = :categoryId) +
            (SELECT COUNT(*) FROM supply_items WHERE categoryId = :categoryId)
    """)
    fun getTotalItemsCountCombined(categoryId: String): Flow<Int>

    @Query("""
        SELECT 
            (SELECT COUNT(*) FROM checklist_items WHERE categoryId = :categoryId AND isCompleted = 1) +
            (SELECT COUNT(*) FROM supply_items WHERE categoryId = :categoryId AND quantity >= minRequired)
    """)
    fun getCompletedItemsCountCombined(categoryId: String): Flow<Int>

    // Global combined counts
    @Query("""
        SELECT 
            (SELECT COUNT(*) FROM checklist_items) +
            (SELECT COUNT(*) FROM supply_items)
    """)
    fun getTotalItemsCombined(): Flow<Int>

    @Query("""
        SELECT 
            (SELECT COUNT(*) FROM checklist_items WHERE isCompleted = 1) +
            (SELECT COUNT(*) FROM supply_items WHERE quantity >= minRequired)
    """)
    fun getCompletedItemsCombined(): Flow<Int>

    // Get all data for export
    @Query("SELECT * FROM categories")
    suspend fun getAllCategoriesForExport(): List<Category>

    @Query("SELECT * FROM checklist_items")
    suspend fun getAllItemsForExport(): List<ChecklistItem>

    @Query("SELECT * FROM supply_items")
    suspend fun getAllSuppliesForExport(): List<SupplyItem>

    // Import data
    @Transaction
    suspend fun importData(
        categories: List<Category>,
        items: List<ChecklistItem>,
        supplies: List<SupplyItem>
    ) {
        deleteAllCategories()
        deleteAllItems()
        deleteAllSupplies()
        insertCategories(categories)
        insertItems(items)
        insertSupplies(supplies)
    }
}
