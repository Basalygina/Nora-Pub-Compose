package com.blumenstreetdoo.nora_pub.ui.craft

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CraftBottomSheetTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun filterSheet_appliesFilterOnApplyClick() {
        var appliedFilter: CraftFilterState? = null

        composeTestRule.setContent {
            CraftFilterScreen(
                filterState = CraftFilterState(country = "Germany"),
                defaultFilter = CraftFilterState(),
                countries = listOf("Germany", "USA"),
                onApply = { appliedFilter = it },
                onReset = {}
            )
        }

        composeTestRule
            .onNodeWithTag("ApplyButton")
            .assertExists()
            .performClick()

        assertEquals("Germany", appliedFilter?.country)
    }

    @Test
    fun filterSheet_resetsFiltersOnResetClick() {
        var wasReset = false

        composeTestRule.setContent {
            CraftFilterScreen(
                filterState = CraftFilterState(country = "USA"),
                defaultFilter = CraftFilterState(),
                countries = listOf("Germany", "USA"),
                onApply = {},
                onReset = { wasReset = true }
            )
        }

        composeTestRule
            .onNodeWithTag("ResetButton")
            .assertExists()
            .performClick()

        assertTrue(wasReset)
    }
}
