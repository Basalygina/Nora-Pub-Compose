package com.blumenstreetdoo.nora_pub.ui.craft

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.blumenstreetdoo.nora_pub.testutil.BeerTestFactory.sampleBeerCanned
import com.blumenstreetdoo.nora_pub.testutil.BeerTestFactory.sampleBeerOnTap
import com.blumenstreetdoo.nora_pub.ui.common.TestTags
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CraftTabsTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private lateinit var craftViewModel: CraftViewModel

    @Before
    fun setUp() {
        craftViewModel = mockk(relaxed = true)
    }

    @Test
    fun craftScreen_defaultTabIsTapBeer() {
        val beers = listOf(sampleBeerOnTap("tap1"))
        every { craftViewModel.craftState } returns MutableStateFlow(CraftScreenState.Content(beers))
        every { craftViewModel.craftFilterState } returns MutableStateFlow(CraftFilterState())
        every { craftViewModel.defaultFilterState } returns CraftFilterState()

        composeTestRule.setContent {
            CraftScreen(
                craftViewModel = craftViewModel,
                onBeerClick = {}
            )
        }

        composeTestRule
            .onNodeWithTag("${TestTags.BEER_ITEM_PREFIX}tap1")
            .assertIsDisplayed()
    }

    @Test
    fun craftScreen_switchToCannedTab_showsCannedBeer() {
        val beers = listOf(
            sampleBeerOnTap("tap1"),
            sampleBeerCanned("can1")
        )
        every { craftViewModel.craftState } returns MutableStateFlow(CraftScreenState.Content(beers))
        every { craftViewModel.craftFilterState } returns MutableStateFlow(CraftFilterState())
        every { craftViewModel.defaultFilterState } returns CraftFilterState()

        composeTestRule.setContent {
            CraftScreen(
                craftViewModel = craftViewModel,
                onBeerClick = {}
            )
        }

        composeTestRule.onNodeWithTag(TestTags.TAB_CANNED_BEER).performClick()

        composeTestRule
            .onNodeWithTag("${TestTags.BEER_ITEM_PREFIX}can1")
            .assertIsDisplayed()
    }

    @Test
    fun bothTabsAreDisplayedInCraftScreen() {
        every { craftViewModel.craftState } returns MutableStateFlow(CraftScreenState.Loading)
        every { craftViewModel.craftFilterState } returns MutableStateFlow(CraftFilterState())
        every { craftViewModel.defaultFilterState } returns CraftFilterState()

        composeTestRule.setContent {
            CraftScreen(
                craftViewModel = craftViewModel,
                onBeerClick = {}
            )
        }

        composeTestRule.onNodeWithTag(TestTags.TAB_TAP_BEER).assertIsDisplayed()
        composeTestRule.onNodeWithTag(TestTags.TAB_CANNED_BEER).assertIsDisplayed()
    }
}
