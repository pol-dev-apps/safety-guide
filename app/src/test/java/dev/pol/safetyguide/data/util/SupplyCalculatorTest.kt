package dev.pol.safetyguide.data.util

import dev.pol.safetyguide.data.model.SupplyItem
import org.junit.Assert.assertEquals
import org.junit.Test

class SupplyCalculatorTest {

    @Test
    fun `calculateMinRequired for water with 4 people`() {
        val result = SupplyCalculator.calculateMinRequired("ss1", 4, homeDays = 3, evacDays = 1)
        // 4 people * 3L * 3 days = 36L
        assertEquals(36, result)
    }

    @Test
    fun `calculateMinRequired for water with 1 person`() {
        val result = SupplyCalculator.calculateMinRequired("ss1", 1, homeDays = 3, evacDays = 1)
        // 1 person * 3L * 3 days = 9L
        assertEquals(9, result)
    }

    @Test
    fun `calculateMinRequired for food with 4 people`() {
        val result = SupplyCalculator.calculateMinRequired("ss2", 4, homeDays = 3, evacDays = 1)
        // 4 people * 1 opak * 3 days = 12 opak
        assertEquals(12, result)
    }

    @Test
    fun `calculateMinRequired for fixed item (fire extinguisher)`() {
        val result = SupplyCalculator.calculateMinRequired("sf1", 4, homeDays = 3, evacDays = 1)
        // Fire extinguisher is always 1
        assertEquals(1, result)
    }

    @Test
    fun `calculateMinRequired for gloves with 4 people`() {
        val result = SupplyCalculator.calculateMinRequired("ss6", 4, homeDays = 3, evacDays = 1)
        // 4 people * 2 szt = 8 szt
        assertEquals(8, result)
    }

    @Test
    fun `calculateMinRequired for unknown supply returns 0`() {
        val result = SupplyCalculator.calculateMinRequired("unknown_id", 4, homeDays = 3, evacDays = 1)
        assertEquals(0, result)
    }

    @Test
    fun `isPredefinedSupply returns true for known supply`() {
        val result = SupplyCalculator.isPredefinedSupply("ss1")
        assertEquals(true, result)
    }

    @Test
    fun `isPredefinedSupply returns false for unknown supply`() {
        val result = SupplyCalculator.isPredefinedSupply("custom_supply")
        assertEquals(false, result)
    }

    @Test
    fun `recalculateAllSupplies updates predefined supplies`() {
        val supplies = listOf(
            SupplyItem("ss1", "home_supplies", "Woda", 6, "L", 9),
            SupplyItem("custom", "home_supplies", "Custom", 5, "szt", 0)
        )
        val result = SupplyCalculator.recalculateAllSupplies(supplies, 4, homeDays = 3, evacDays = 1)

        // ss1 should be recalculated: 4 * 3 * 3 = 36
        assertEquals(36, result[0].minRequired)
        // custom should remain unchanged (minRequired = 0 means not recalculated)
        assertEquals(0, result[1].minRequired)
    }

    @Test
    fun `getHouseholdWord returns correct Polish grammar`() {
        assertEquals("osoba", SupplyCalculator.getHouseholdWord(1))
        assertEquals("osoby", SupplyCalculator.getHouseholdWord(2))
        assertEquals("osoby", SupplyCalculator.getHouseholdWord(4))
        assertEquals("osób", SupplyCalculator.getHouseholdWord(5))
        assertEquals("osób", SupplyCalculator.getHouseholdWord(20))
    }

    @Test
    fun `calculateMinRequired for masks with 20 people`() {
        val result = SupplyCalculator.calculateMinRequired("ss9", 20, homeDays = 3, evacDays = 1)
        // 20 people * 2 szt = 40 szt
        assertEquals(40, result)
    }

    @Test
    fun `calculateMinRequired for flashlight scales with people`() {
        val result4 = SupplyCalculator.calculateMinRequired("ss15", 4, homeDays = 3, evacDays = 1)
        val result20 = SupplyCalculator.calculateMinRequired("ss15", 20, homeDays = 3, evacDays = 1)
        // Flashlight: ceil(people / 2)
        assertEquals(2, result4) // ceil(4/2) = 2
        assertEquals(10, result20) // ceil(20/2) = 10
    }
}
