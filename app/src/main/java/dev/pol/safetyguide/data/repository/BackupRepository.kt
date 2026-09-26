package dev.pol.safetyguide.data.repository

import android.content.Context
import android.net.Uri
import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dev.pol.safetyguide.R
import dev.pol.safetyguide.data.model.Category
import dev.pol.safetyguide.data.model.ChecklistItem
import dev.pol.safetyguide.data.model.SupplyItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

private const val TAG = "BackupRepository"

data class BackupData(
    val version: Int = 1,
    val categories: List<Category>,
    val items: List<ChecklistItem>,
    val supplies: List<SupplyItem>,
    val exportDate: Long = System.currentTimeMillis()
)

sealed class BackupResult {
    data class Success(
        val categoriesImported: Int = 0,
        val itemsImported: Int = 0,
        val suppliesImported: Int = 0,
        val itemsDropped: Int = 0,
        val suppliesDropped: Int = 0
    ) : BackupResult()
    data class Error(val messageRes: Int, val formatArgs: Array<Any>? = null) : BackupResult()
}

@Singleton
class BackupRepository @Inject constructor(
    private val repository: ChecklistRepository
) {
    private val gson = Gson()

    suspend fun exportData(context: Context, uri: Uri): BackupResult {
        return try {
            val (categories, items, supplies) = repository.exportData()
            val backupData = BackupData(
                categories = categories,
                items = items,
                supplies = supplies
            )
            val json = gson.toJson(backupData)

            withContext(Dispatchers.IO) {
                context.contentResolver.openOutputStream(uri)?.use { outputStream ->
                    outputStream.write(json.toByteArray())
                } ?: throw IOException(context.getString(R.string.error_file_open))
            }

            Log.d(TAG, "Export successful: ${categories.size} categories, ${items.size} items, ${supplies.size} supplies")
            BackupResult.Success(
                categoriesImported = categories.size,
                itemsImported = items.size,
                suppliesImported = supplies.size
            )
        } catch (e: IOException) {
            Log.e(TAG, "Export failed", e)
            BackupResult.Error(R.string.error_export_failed, arrayOf(e.message ?: ""))
        } catch (e: Exception) {
            Log.e(TAG, "Export failed with unexpected error", e)
            BackupResult.Error(R.string.error_export_unknown)
        }
    }

    suspend fun readDataFromUri(context: Context, uri: Uri): String? {
        return withContext(Dispatchers.IO) {
            try {
                context.contentResolver.openInputStream(uri)?.use { inputStream ->
                    inputStream.bufferedReader().readText()
                }
            } catch (e: IOException) {
                Log.e(TAG, "Failed to read data from URI", e)
                null
            } catch (e: Exception) {
                Log.e(TAG, "Failed to read data from URI", e)
                null
            }
        }
    }

    suspend fun importData(json: String): BackupResult {
        return try {
            // Validate JSON size (max 1MB)
            if (json.length > 1_000_000) {
                return BackupResult.Error(R.string.error_file_too_large)
            }

            val type = object : TypeToken<BackupData>() {}.type
            val backupData: BackupData = gson.fromJson(json, type)

            // Validate data limits
            if (backupData.categories.size > 100 || backupData.items.size > 1000 || backupData.supplies.size > 500) {
                return BackupResult.Error(R.string.error_data_exceeds_limits)
            }

            // Validate string lengths and sanitize categories
            val sanitizedCategories = backupData.categories.mapNotNull { category ->
                if (category.id.isNullOrBlank() || category.name.isNullOrBlank() || category.name.length > 100) {
                    null
                } else {
                    category.copy(
                        id = category.id,
                        name = category.name.take(100),
                        icon = category.icon.take(50),
                        sortOrder = category.sortOrder.coerceIn(0, 1000)
                    )
                }
            }

            // Build valid category ID set for foreign key validation
            val validCategoryIds = sanitizedCategories.map { it.id }.toSet()

            // Sanitize items and filter by valid category IDs
            val sanitizedItems = backupData.items.mapNotNull { item ->
                if (item.id.isNullOrBlank() || item.categoryId.isNullOrBlank() || item.title.isNullOrBlank() || item.title.length > 200) {
                    null
                } else {
                    item.copy(
                        id = item.id,
                        categoryId = item.categoryId,
                        title = item.title.take(200),
                        description = (item.description ?: "").take(500)
                    )
                }
            }.filter { it.categoryId in validCategoryIds }

            // Sanitize supplies and filter by valid category IDs
            val sanitizedSupplies = backupData.supplies.mapNotNull { supply ->
                if (supply.id.isNullOrBlank() || supply.categoryId.isNullOrBlank() || supply.name.isNullOrBlank() || supply.name.length > 200) {
                    null
                } else {
                    supply.copy(
                        id = supply.id,
                        categoryId = supply.categoryId,
                        name = supply.name.take(200),
                        unit = supply.unit.take(20),
                        quantity = supply.quantity.coerceIn(0, 10000),
                        minRequired = supply.minRequired.coerceIn(0, 10000),
                        perPersonMultiplier = supply.perPersonMultiplier.coerceIn(0, 100),
                        notes = (supply.notes ?: "").take(500)
                    )
                }
            }.filter { it.categoryId in validCategoryIds }

            // Calculate dropped counts
            val itemsDropped = backupData.items.size - sanitizedItems.size
            val suppliesDropped = backupData.supplies.size - sanitizedSupplies.size

            // Validate at least some data was preserved
            if (sanitizedCategories.isEmpty() && sanitizedItems.isEmpty() && sanitizedSupplies.isEmpty()) {
                return BackupResult.Error(R.string.error_data_empty)
            }

            repository.importData(
                categories = sanitizedCategories,
                items = sanitizedItems,
                supplies = sanitizedSupplies
            )

            Log.d(TAG, "Import successful: ${sanitizedCategories.size} categories, ${sanitizedItems.size} items, ${sanitizedSupplies.size} supplies (dropped: $itemsDropped items, $suppliesDropped supplies)")
            BackupResult.Success(
                categoriesImported = sanitizedCategories.size,
                itemsImported = sanitizedItems.size,
                suppliesImported = sanitizedSupplies.size,
                itemsDropped = itemsDropped,
                suppliesDropped = suppliesDropped
            )
        } catch (e: Exception) {
            Log.e(TAG, "Import failed", e)
            BackupResult.Error(R.string.error_import_invalid)
        }
    }
}
