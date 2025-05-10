package com.blumenstreetdoo.nora_pub.domain.impl

import com.blumenstreetdoo.nora_pub.domain.api.EventNewsInteractor
import com.blumenstreetdoo.nora_pub.domain.api.EventNewsRepository
import com.blumenstreetdoo.nora_pub.domain.models.Event
import com.blumenstreetdoo.nora_pub.domain.models.EventType
import com.blumenstreetdoo.nora_pub.domain.models.News
import com.blumenstreetdoo.nora_pub.domain.models.NewsType
import com.blumenstreetdoo.nora_pub.domain.models.Resource
import io.mockk.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.test.*
import org.junit.*
import org.junit.Assert.*


@OptIn(ExperimentalCoroutinesApi::class)
class EventNewsInteractorImplTest {

    private val repository: EventNewsRepository = mockk()
    private lateinit var interactor: EventNewsInteractor
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        interactor = EventNewsInteractorImpl(repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        clearAllMocks()
    }

    @Test
    fun `getEvents returns success result`() = runTest {
        val events = listOf(Event(id = "123", title = "Test Event", type = EventType.HAPPY_HOUR))
        every { repository.getEvents() } returns flowOf(Resource.Success(events))

        val result = interactor.getEvents().first()

        assertEquals(events, result.first)
        assertNull(result.second)
        verify(exactly = 1) { repository.getEvents() }
    }

    @Test
    fun `getEvents returns error result`() = runTest {
        every { repository.getEvents() } returns flowOf(Resource.Error("Error loading events"))

        val result = interactor.getEvents().first()

        assertNull(result.first)
        assertEquals("Error loading events", result.second)
    }

    @Test
    fun `getEventById returns event on success`() {
        val event = Event(id = "17", title = "Sample", type = EventType.PARTY)
        every { repository.getEventById("17") } returns Resource.Success(event)

        val result = interactor.getEventById("17")

        assertEquals(event, result.first)
        assertNull(result.second)
    }

    @Test
    fun `getEventById returns error on failure`() {
        every { repository.getEventById("456") } returns Resource.Error("Not found")

        val result = interactor.getEventById("456")

        assertNull(result.first)
        assertEquals("Not found", result.second)
    }

    @Test
    fun `getNews returns success`() = runTest {
        val news = listOf(
            News(
                id = "33", title = "Sample News", type = NewsType.NEW_ARRIVAL
            )
        )
        every { repository.getNews() } returns flowOf(Resource.Success(news))

        val result = interactor.getNews().first()

        assertEquals(news, result.first)
        assertNull(result.second)
    }

    @Test
    fun `getNews returns error`() = runTest {
        every { repository.getNews() } returns flowOf(Resource.Error("News error"))

        val result = interactor.getNews().first()

        assertNull(result.first)
        assertEquals("News error", result.second)
    }

    @Test
    fun `getNewsById returns success`() {
        val news = News(id = "42", title = "Breaking", type = NewsType.OTHER_NEWS)
        every { repository.getNewsById("42") } returns Resource.Success(news)

        val result = interactor.getNewsById("42")

        assertEquals(news, result.first)
        assertNull(result.second)
    }

    @Test
    fun `getNewsById returns error`() {
        every { repository.getNewsById("bad") } returns Resource.Error("Not found")

        val result = interactor.getNewsById("bad")

        assertNull(result.first)
        assertEquals("Not found", result.second)
    }
}
