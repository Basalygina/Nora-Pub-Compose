package com.blumenstreetdoo.nora_pub.domain.impl

import com.blumenstreetdoo.nora_pub.domain.api.MenuInteractor
import com.blumenstreetdoo.nora_pub.domain.api.MenuRepository
import com.blumenstreetdoo.nora_pub.domain.models.Beer
import com.blumenstreetdoo.nora_pub.domain.models.Resource
import com.blumenstreetdoo.nora_pub.testutil.BeerTestFactory
import io.mockk.clearMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test


@OptIn(ExperimentalCoroutinesApi::class)
class MenuInteractorImplTest {

    private val repository: MenuRepository = mockk(relaxed = true)
    private lateinit var interactor: MenuInteractor

    private val testDispatcher = StandardTestDispatcher()
    private val testScope = TestScope(testDispatcher)

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        interactor = MenuInteractorImpl(repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        clearMocks(repository)
    }

    @Test
    fun `getCraftList should emit success data`() = testScope.runTest {
        val beer = BeerTestFactory.sampleBeer("1")
        val beerList = listOf(beer)
        val flow = flowOf(Resource.Success(beerList))
        every { repository.getCraftList() } returns flow

        val result = interactor.getCraftList().first()

        assertEquals(beerList, result.first)
        assertNull(result.second)
        verify(exactly = 1) { repository.getCraftList() }
    }

    @Test
    fun `getCraftList should emit error message on failure`() = testScope.runTest {
        val errorMsg = "Network error"
        val flow = flowOf(Resource.Error<List<Beer>>(errorMsg))
        every { repository.getCraftList() } returns flow

        val result = interactor.getCraftList().first()

        assertNull(result.first)
        assertEquals(errorMsg, result.second)
    }

    @Test
    fun `getBeerById should return correct beer when id is valid`() {
        val beer = BeerTestFactory.sampleBeer("5")
        coEvery { repository.getBeerById("5") } returns Resource.Success(beer)

        val result = interactor.getBeerById("5")

        assertEquals(beer, result.first)
        assertNull(result.second)
        coVerify(exactly = 1) { repository.getBeerById("5") }
    }

    @Test
    fun `getBeerById should return error when id is invalid`() {
        val errorMessage = "Beer not found"
        coEvery { repository.getBeerById("1984") } returns Resource.Error(errorMessage)

        val result = interactor.getBeerById("1984")

        assertNull(result.first)
        assertEquals(errorMessage, result.second)
        coVerify { repository.getBeerById("1984") }
    }
}
