package dev.pol.safetyguide.data.model

data class CategoryWithProgress(
    val category: Category,
    val totalItems: Int,
    val completedItems: Int
) {
    val progress: Float
        get() = if (totalItems > 0) completedItems.toFloat() / totalItems else 0f

    val progressText: String
        get() = "$completedItems/$totalItems"
}
