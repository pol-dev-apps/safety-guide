package dev.pol.safetyguide.data.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "checklist_items",
    foreignKeys = [
        ForeignKey(
            entity = Category::class,
            parentColumns = ["id"],
            childColumns = ["categoryId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("categoryId")]
)
data class ChecklistItem(
    @PrimaryKey val id: String,
    val categoryId: String,
    val title: String,
    val description: String? = null,
    val isCompleted: Boolean = false,
    val completedAt: Long? = null
)
