package dev.pol.safetyguide.data.db

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import dev.pol.safetyguide.data.model.Category
import dev.pol.safetyguide.data.model.ChecklistItem
import dev.pol.safetyguide.data.model.SupplyItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

private const val TAG = "AppDatabase"

@Database(
    entities = [Category::class, ChecklistItem::class, SupplyItem::class],
    version = 2,
    exportSchema = true
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun checklistDao(): ChecklistDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        private val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL("ALTER TABLE supply_items ADD COLUMN perPersonMultiplier INTEGER NOT NULL DEFAULT 0")
            }
        }

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "prepper_database"
                )
                    .addCallback(DatabaseCallback())
                    .addMigrations(MIGRATION_1_2)
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }

    private class DatabaseCallback : Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                CoroutineScope(Dispatchers.IO + SupervisorJob()).launch {
                    try {
                        populateDatabase(database.checklistDao())
                        Log.d(TAG, "Database seeded successfully")
                    } catch (e: Exception) {
                        Log.e(TAG, "Failed to seed database", e)
                    }
                }
            }
        }

        suspend fun populateDatabase(dao: ChecklistDao) {
            dao.insertCategories(SeedData.getCategories())
            dao.insertItems(SeedData.getChecklistItems())
            dao.insertSupplies(SeedData.getSupplyItems())
        }
    }
}
