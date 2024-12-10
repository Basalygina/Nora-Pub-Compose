import com.blumenstreetdoo.nora_pub.domain.api.EventNewsRepository
import com.blumenstreetdoo.nora_pub.domain.models.Event
import com.blumenstreetdoo.nora_pub.domain.models.EventType
import com.blumenstreetdoo.nora_pub.domain.models.News
import com.blumenstreetdoo.nora_pub.domain.models.NewsType
import com.blumenstreetdoo.nora_pub.domain.models.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class EventNewsRepositoryImpl : EventNewsRepository {
    override fun getEvents(): Flow<Resource<List<Event>>> = flow {
        emit(Resource.Success(generateMockEvents())) // add ?: emptyList()
    }

    override fun getEventById(id: String): Resource<Event> {
        val event = generateMockEvents().find { it.id == id }
        return event?.let { Resource.Success(it) } ?: Resource.Error("Event not found")
    }

    override fun getNews(): Flow<Resource<List<News>>> = flow {
        emit(Resource.Success(generateMockNews())) // add ?: emptyList()
    }

    override fun getNewsById(id: String): Resource<News> {
        val news = generateMockNews().find { it.id == id }
        return news?.let { Resource.Success(it) } ?: Resource.Error("News not found")
    }

    // Mock data generation functions
    private fun generateMockEvents(): List<Event> = listOf(
        Event(
            id = "1",
            title = "Party Night",
            description = "Join us for an amazing party night with live music!",
            dateTime = "2024-12-31T20:00:00",
            imageUrl = "https://example.com/party.jpg",
            type = EventType.PARTY
        ),
        Event(
            id = "2",
            title = "Happy Hour",
            description = "Enjoy 2-for-1 drinks from 5 PM to 7 PM!",
            dateTime = "2024-12-05T17:00:00",
            imageUrl = "https://example.com/happy_hour.jpg",
            type = EventType.HAPPY_HOUR
        )
    )

    private fun generateMockNews(): List<News> = listOf(
        News(
            id = "1",
            title = "New Arrival: Craft Beers",
            description = "We’ve added new craft beers to our menu. Come and try them!",
            imageUrl = "https://example.com/craft_beer.jpg",
            type = NewsType.NEW_ARRIVAL
        ),
        News(
            id = "2",
            title = "Holiday Hours",
            description = "Check out our updated hours for the holiday season.",
            imageUrl = "https://example.com/holiday_hours.jpg",
            type = NewsType.OTHER_NEWS
        )
    )
}
