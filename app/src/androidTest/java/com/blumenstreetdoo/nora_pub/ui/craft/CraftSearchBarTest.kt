package com.blumenstreetdoo.nora_pub.ui.craft

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.blumenstreetdoo.nora_pub.ui.common.TestTags
import io.mockk.mockk
import io.mockk.verify
import io.mockk.every
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CraftSearchBarTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private lateinit var craftViewModel: CraftViewModel

    @Before
    fun setUp() {
        craftViewModel = mockk(relaxed = true)
    }

    @Test
    fun searchBar_isVisible() {
        every { craftViewModel.craftState } returns MutableStateFlow(CraftScreenState.Loading)
        every { craftViewModel.craftFilterState } returns MutableStateFlow(CraftFilterState())
        every { craftViewModel.defaultFilterState } returns CraftFilterState()

        composeTestRule.setContent {
            CraftScreen(
                craftViewModel = craftViewModel,
                onBeerClick = {}
            )
        }

        composeTestRule.onNodeWithTag(TestTags.SEARCH_BAR).assertIsDisplayed()
    }

    @Test
    fun searchBar_updatesFilterOnTextChange() {
        val filterStateFlow = MutableStateFlow(CraftFilterState())
        every { craftViewModel.craftState } returns MutableStateFlow(CraftScreenState.Loading)
        every { craftViewModel.craftFilterState } returns filterStateFlow
        every { craftViewModel.defaultFilterState } returns CraftFilterState()

        composeTestRule.setContent {
            CraftScreen(
                craftViewModel = craftViewModel,
                onBeerClick = {}
            )
        }

        composeTestRule
            .onNodeWithTag(TestTags.SEARCH_BAR)
            .performTextInput("IPA")

        verify {
            craftViewModel.updateFilter(withArg {
                assert(it.searchQuery == "IPA")
            })
        }
    }

    @Test
    fun searchBar_clearsFilterOnClearClick() {
        val filterStateFlow = MutableStateFlow(CraftFilterState(searchQuery = "Initial"))
        every { craftViewModel.craftState } returns MutableStateFlow(CraftScreenState.Loading)
        every { craftViewModel.craftFilterState } returns filterStateFlow
        every { craftViewModel.defaultFilterState } returns CraftFilterState()

        composeTestRule.setContent {
            CraftScreen(
                craftViewModel = craftViewModel,
                onBeerClick = {}
            )
        }

        composeTestRule
            .onNode(hasContentDescription("Clear"))
            .performClick()

        verify {
            craftViewModel.updateFilter(withArg {
                assert(it.searchQuery?.isEmpty() == true)
            })
        }
    }
}
