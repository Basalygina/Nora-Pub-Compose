package com.blumenstreetdoo.nora_pub.data.db.dao

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.blumenstreetdoo.nora_pub.data.db.AppDataBase
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.*
import org.junit.*
import org.junit.Assert.*
import com.blumenstreetdoo.nora_pub.testutil.BeerTestFactory.sampleBeerEntity
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config


@OptIn(ExperimentalCoroutinesApi::class)
@Config(sdk = [28], manifest = Config.NONE)
@RunWith(RobolectricTestRunner::class)
class FavoriteBeerDaoTest {

    private lateinit var db: AppDataBase
    private lateinit var dao: FavoriteBeerDao

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        db = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDataBase::class.java
        ).allowMainThreadQueries().build()
        dao = db.favoriteBeerDao()
    }

    @After
    fun tearDown() {
        db.close()
        Dispatchers.resetMain()
    }

    @Test
    fun `addFavoriteBeer inserts and retrieves beer`() = runTest {
        val beer = sampleBeerEntity("77")

        dao.addFavoriteBeer(beer)
        val result = dao.getFavoriteBeerById("77").first()

        assertEquals(beer, result)
    }

    @Test
    fun `deleteFavoriteBeerById removes the beer`() = runTest {
        val beer = sampleBeerEntity("13")
        dao.addFavoriteBeer(beer)

        dao.deleteFavoriteBeerById("13")
        val result = dao.getFavoriteBeerById("13").first()

        assertNull(result)
    }

    @Test
    fun `getAllFavoriteBeers returns all inserted beers`() = runTest {
        val beer1 = sampleBeerEntity("901")
        val beer2 = sampleBeerEntity("902")

        dao.addFavoriteBeer(beer1)
        dao.addFavoriteBeer(beer2)

        val result = dao.getAllFavoriteBeers().first()

        assertTrue(result.containsAll(listOf(beer1, beer2)))
        assertEquals(2, result.size)
    }

    @Test
    fun `isBeerFavorite returns true for inserted beer`() = runTest {
        val beer = sampleBeerEntity("111")
        dao.addFavoriteBeer(beer)

        val result = dao.isBeerFavorite("111")

        assertTrue(result)
    }

    @Test
    fun `isBeerFavorite returns false for non-inserted beer`() = runTest {
        val result = dao.isBeerFavorite("999")
        assertFalse(result)
    }

    @Test
    fun `updateNote updates the note of a beer`() = runTest {
        val beer = sampleBeerEntity("5")
        dao.addFavoriteBeer(beer)

        dao.updateNote("5", "New note")

        val updatedBeer = dao.getFavoriteBeerById("5").first()
        assertEquals("New note", updatedBeer?.note)
    }
}
