package dev.pol.safetyguide.data.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "supply_items",
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
data class SupplyItem(
    @PrimaryKey val id: String,
    val categoryId: String,
    val name: String,
    val quantity: Int = 0,
    val unit: String,
    val minRequired: Int = 0,
    val expirationDate: Long? = null,
    val notes: String? = null,
    val perPersonMultiplier: Int = 0 // 0 = no scaling, >0 = multiply by household size
)
