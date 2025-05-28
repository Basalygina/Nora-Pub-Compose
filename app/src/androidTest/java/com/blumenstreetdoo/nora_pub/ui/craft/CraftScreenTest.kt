package com.blumenstreetdoo.nora_pub.ui.craft

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
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
class CraftScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private lateinit var craftViewModel: CraftViewModel

    @Before
    fun setUp() {
        craftViewModel = mockk(relaxed = true)
    }

    @Test
    fun craftScreen_showsLoadingIndicator() {
        every { craftViewModel.craftState } returns MutableStateFlow(CraftScreenState.Loading)
        every { craftViewModel.craftFilterState } returns MutableStateFlow(CraftFilterState())
        every { craftViewModel.defaultFilterState } returns CraftFilterState()

        composeTestRule.setContent {
            CraftScreen(
                craftViewModel = craftViewModel,
                onBeerClick = {}
            )
        }

        composeTestRule.onNodeWithTag(TestTags.LOADING_INDICATOR)
            .assertIsDisplayed()
    }

    @Test
    fun craftScreen_showsErrorMessage() {
        val errorText = "Something went wrong"
        every { craftViewModel.craftState } returns MutableStateFlow(
            CraftScreenState.Error(errorText)
        )
        every { craftViewModel.craftFilterState } returns MutableStateFlow(CraftFilterState())
        every { craftViewModel.defaultFilterState } returns CraftFilterState()

        composeTestRule.setContent {
            CraftScreen(
                craftViewModel = craftViewModel,
                onBeerClick = {}
            )
        }

        composeTestRule.onNodeWithTag(TestTags.ERROR_MESSAGE)
            .assertIsDisplayed()
    }

    @Test
    fun craftScreen_showsBeerListContent() {
        val beers = listOf(
            sampleBeerOnTap("1"),
            sampleBeerOnTap("2")
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

        beers.forEach {
            composeTestRule
                .onNodeWithTag("BeerItem_${it.id}")
                .assertExists()
                .assertIsDisplayed()
        }
    }

    @Test
    fun craftScreen_clicksBeerItem() {
        val beers = listOf(sampleBeerOnTap("1"))
        var clickedBeerId: String? = null

        every { craftViewModel.craftState } returns MutableStateFlow(CraftScreenState.Content(beers))
        every { craftViewModel.craftFilterState } returns MutableStateFlow(CraftFilterState())
        every { craftViewModel.defaultFilterState } returns CraftFilterState()

        composeTestRule.setContent {
            CraftScreen(
                craftViewModel = craftViewModel,
                onBeerClick = { clickedBeerId = it.id }
            )
        }

        composeTestRule
            .onNodeWithTag("BeerItem_1")
            .assertExists()
            .performClick()

        assert(clickedBeerId == "1")
    }
}
