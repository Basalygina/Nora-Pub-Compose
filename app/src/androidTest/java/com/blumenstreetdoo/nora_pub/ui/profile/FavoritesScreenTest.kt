package com.blumenstreetdoo.nora_pub.ui.profile

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.blumenstreetdoo.nora_pub.testutil.BeerTestFactory.sampleFavoriteBeer
import com.blumenstreetdoo.nora_pub.ui.common.TestTags
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.Assert.*

@RunWith(AndroidJUnit4::class)
class FavoritesScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun favoritesContent_displaysCorrectHeader() {
        composeTestRule.setContent {
            FavoritesScreen(
                state = FavoriteScreenState.Content(
                    favorites = listOf(sampleFavoriteBeer("123"))
                ),
                onItemClick = {},
                onIconFavoriteClick = {}
            )
        }

        composeTestRule
            .onNodeWithText("Favorite craft beer:")
            .assertIsDisplayed()
    }

    @Test
    fun favoritesContent_displaysAllItems() {
        val beers = listOf(
            sampleFavoriteBeer("1"),
            sampleFavoriteBeer("2")
        )

        composeTestRule.setContent {
            FavoritesScreen(
                state = FavoriteScreenState.Content(beers),
                onItemClick = {},
                onIconFavoriteClick = {}
            )
        }

        beers.forEach {
            composeTestRule
                .onNodeWithTag("${TestTags.FAVORITE_ITEM}_${it.id}")
                .assertExists()
                .assertIsDisplayed()
        }
    }

    @Test
    fun favoritesContent_clicksOnItem() {
        val beer = sampleFavoriteBeer("1")
        var itemClicked = false

        composeTestRule.setContent {
            FavoritesScreen(
                state = FavoriteScreenState.Content(listOf(beer)),
                onItemClick = { itemClicked = true },
                onIconFavoriteClick = {}
            )
        }

        composeTestRule
            .onNodeWithTag("${TestTags.FAVORITE_ITEM}_${beer.id}")
            .performClick()

        assertTrue(itemClicked)
    }

    @Test
    fun favoritesContent_clicksOnHeartIcon() {
        val beer = sampleFavoriteBeer("1")
        var iconClicked = false

        composeTestRule.setContent {
            FavoritesScreen(
                state = FavoriteScreenState.Content(listOf(beer)),
                onItemClick = {},
                onIconFavoriteClick = { iconClicked = true }
            )
        }

        composeTestRule
            .onNodeWithTag("${TestTags.FAVORITE_ITEM_HEART_ICON}_${beer.id}")
            .performClick()

        assertTrue(iconClicked)
    }

    @Test
    fun favoritesContent_showsNoteIconWhenNoteIsNotEmpty() {
        val beer = sampleFavoriteBeer("1").copy(note = "This is a note")

        composeTestRule.setContent {
            FavoritesScreen(
                state = FavoriteScreenState.Content(listOf(beer)),
                onItemClick = {},
                onIconFavoriteClick = {}
            )
        }

        composeTestRule
            .onNodeWithTag("${TestTags.FAVORITE_ITEM_NOTE_ICON}_${beer.id}", useUnmergedTree = true)
            .assertExists()
    }

    @Test
    fun favoritesError_displaysCorrectMessage() {
        val errorMessage = "Something went wrong"
        composeTestRule.setContent {
            FavoritesScreen(
                state = FavoriteScreenState.Error(errorMessage),
                onItemClick = {},
                onIconFavoriteClick = {}
            )
        }

        composeTestRule
            .onNodeWithText(errorMessage)
            .assertIsDisplayed()
    }

    @Test
    fun favoritesEmpty_displaysEmptyState() {
        composeTestRule.setContent {
            FavoritesScreen(
                state = FavoriteScreenState.Empty,
                onItemClick = {},
                onIconFavoriteClick = {}
            )
        }

        composeTestRule
            .onNodeWithText("Favorites list is empty")
            .assertIsDisplayed()
    }
}
