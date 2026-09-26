package dev.pol.safetyguide.data.util

import dev.pol.safetyguide.data.model.SupplyItem
import kotlin.math.ceil

object SupplyCalculator {

    private const val WATER_PER_PERSON_PER_DAY = 3 // liters
    private const val FOOD_PER_PERSON_PER_DAY = 1 // package
    private const val MEDICINE_PER_PERSON = 1 // package
    private const val GLOVES_PER_PERSON = 2 // pieces
    private const val MASKS_PER_PERSON = 2 // pieces
    private const val TOILET_PAPER_PER_PERSON = 1 // package
    private const val WIPES_PER_PERSON = 1 // package

    data class SupplyFormula(
        val id: String,
        val category: SupplyCategory,
        val minRequired: (people: Int, days: Int) -> Int
    )

    enum class SupplyCategory {
        HOME, EVAC, OTHER
    }

    private val predefinedFormulas = listOf(
        // === Home Supplies (uses homeDays) ===
        SupplyFormula("ss1", SupplyCategory.HOME) { people, days -> people * WATER_PER_PERSON_PER_DAY * days },
        SupplyFormula("ss2", SupplyCategory.HOME) { people, days -> people * FOOD_PER_PERSON_PER_DAY * days },
        SupplyFormula("ss3", SupplyCategory.HOME) { people, _ -> people * MEDICINE_PER_PERSON },
        SupplyFormula("ss4", SupplyCategory.HOME) { people, _ -> people * MEDICINE_PER_PERSON },
        SupplyFormula("ss5", SupplyCategory.HOME) { people, _ -> ceil(people / 4.0).toInt() },
        SupplyFormula("ss6", SupplyCategory.HOME) { people, _ -> people * GLOVES_PER_PERSON },
        SupplyFormula("ss7", SupplyCategory.HOME) { _, _ -> 1 },
        SupplyFormula("ss8", SupplyCategory.HOME) { _, _ -> 2 },
        SupplyFormula("ss9", SupplyCategory.HOME) { people, _ -> people * MASKS_PER_PERSON },
        SupplyFormula("ss10", SupplyCategory.HOME) { people, _ -> ceil(people / 2.0).toInt() },
        SupplyFormula("ss11", SupplyCategory.HOME) { people, _ -> people * TOILET_PAPER_PER_PERSON },
        SupplyFormula("ss12", SupplyCategory.HOME) { people, _ -> people * WIPES_PER_PERSON },
        SupplyFormula("ss13", SupplyCategory.HOME) { _, _ -> 1 },
        SupplyFormula("ss14", SupplyCategory.HOME) { people, _ -> ceil(people / 4.0).toInt() },
        SupplyFormula("ss15", SupplyCategory.HOME) { people, _ -> ceil(people / 2.0).toInt() },
        SupplyFormula("ss16", SupplyCategory.HOME) { _, _ -> 1 },
        SupplyFormula("ss17", SupplyCategory.HOME) { people, _ -> people },
        SupplyFormula("ss18", SupplyCategory.HOME) { people, _ -> people * 2 },
        SupplyFormula("ss19", SupplyCategory.HOME) { people, _ -> people * 2 },
        SupplyFormula("ss20", SupplyCategory.HOME) { people, _ -> ceil(people / 2.0).toInt() },
        SupplyFormula("ss21", SupplyCategory.HOME) { _, _ -> 1 },
        SupplyFormula("ss22", SupplyCategory.HOME) { _, _ -> 2 },
        SupplyFormula("ss23", SupplyCategory.HOME) { _, _ -> 1 },
        SupplyFormula("ss24", SupplyCategory.HOME) { _, _ -> 1 },
        SupplyFormula("ss25", SupplyCategory.HOME) { _, _ -> 1 },

        // === Evacuation Bag (uses evacDays) ===
        SupplyFormula("se1", SupplyCategory.EVAC) { people, days -> people * WATER_PER_PERSON_PER_DAY * days },
        SupplyFormula("se2", SupplyCategory.EVAC) { people, _ -> people * MEDICINE_PER_PERSON },
        SupplyFormula("se3", SupplyCategory.EVAC) { people, _ -> people * WIPES_PER_PERSON },
        SupplyFormula("se4", SupplyCategory.EVAC) { _, _ -> 1 },
        SupplyFormula("se5", SupplyCategory.EVAC) { people, days -> people * FOOD_PER_PERSON_PER_DAY * days },

        // === Fire (fixed) ===
        SupplyFormula("sf1", SupplyCategory.OTHER) { _, _ -> 1 },
        SupplyFormula("sf2", SupplyCategory.OTHER) { _, _ -> 1 },

        // === Blackout (uses homeDays) ===
        SupplyFormula("sb1", SupplyCategory.HOME) { people, _ -> ceil(people / 2.0).toInt() },
        SupplyFormula("sb2", SupplyCategory.HOME) { _, _ -> 1 },
        SupplyFormula("sb3", SupplyCategory.HOME) { people, days -> people * FOOD_PER_PERSON_PER_DAY * days },
        SupplyFormula("sb4", SupplyCategory.HOME) { _, _ -> 1 },

        // === Hygiene (fixed) ===
        SupplyFormula("sh1", SupplyCategory.OTHER) { people, _ -> people * WIPES_PER_PERSON },
        SupplyFormula("sh2", SupplyCategory.OTHER) { _, _ -> 1 }
    )

    fun calculateMinRequired(supplyId: String, people: Int, homeDays: Int, evacDays: Int): Int {
        val formula = predefinedFormulas.find { it.id == supplyId } ?: return 0
        val days = when (formula.category) {
            SupplyCategory.HOME -> homeDays
            SupplyCategory.EVAC -> evacDays
            SupplyCategory.OTHER -> 0 // not used for fixed items
        }
        return formula.minRequired.invoke(people, days)
    }

    fun isPredefinedSupply(supplyId: String): Boolean {
        return predefinedFormulas.any { it.id == supplyId }
    }

    fun recalculateAllSupplies(supplies: List<SupplyItem>, people: Int, homeDays: Int, evacDays: Int): List<SupplyItem> {
        return supplies.map { supply ->
            if (isPredefinedSupply(supply.id)) {
                supply.copy(minRequired = calculateMinRequired(supply.id, people, homeDays, evacDays))
            } else if (supply.perPersonMultiplier > 0) {
                supply.copy(minRequired = supply.perPersonMultiplier * people)
            } else {
                supply
            }
        }
    }

    fun getHouseholdWord(people: Int): String {
        return when {
            people == 1 -> "osoba"
            people in 2..4 -> "osoby"
            else -> "osób"
        }
    }
}
