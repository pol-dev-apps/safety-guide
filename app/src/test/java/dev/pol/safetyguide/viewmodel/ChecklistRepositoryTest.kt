package dev.pol.safetyguide.viewmodel

import dev.pol.safetyguide.data.model.Category
import dev.pol.safetyguide.data.model.CategoryWithProgress
import dev.pol.safetyguide.data.model.ChecklistItem
import dev.pol.safetyguide.data.model.SupplyItem
import dev.pol.safetyguide.data.repository.ChecklistRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ChecklistRepositoryTest {

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var repository: ChecklistRepository

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        repository = mockk()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `getAllCategories returns categories from dao`() = runTest {
        // Given
        val categories = listOf(
            Category("fire", "Pożar", "🔥", 1),
            Category("flood", "Powódź", "🌊", 2)
        )
        coEvery { repository.getAllCategories() } returns flowOf(categories)

        // When
        val result = repository.getAllCategories()

        // Then
        result.collect { collectedCategories ->
            assertEquals(2, collectedCategories.size)
            assertEquals("fire", collectedCategories[0].id)
            assertEquals("flood", collectedCategories[1].id)
        }
    }

    @Test
    fun `getTotalItems returns count from dao`() = runTest {
        // Given
        coEvery { repository.getTotalItems() } returns flowOf(42)

        // When
        val result = repository.getTotalItems()

        // Then
        result.collect { count ->
            assertEquals(42, count)
        }
    }

    @Test
    fun `getCompletedItems returns count from dao`() = runTest {
        // Given
        coEvery { repository.getCompletedItems() } returns flowOf(10)

        // When
        val result = repository.getCompletedItems()

        // Then
        result.collect { count ->
            assertEquals(10, count)
        }
    }
}
