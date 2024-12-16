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
            dateTime = "Every friday",
            imageUrl = "https://beertime.bg/wp-content/uploads/2022/10/PModern.webp",
            type = EventType.PARTY
        ),
        Event(
            id = "2",
            title = "Happy Hour",
            description = "Enjoy 2-for-1 drinks from 5 PM to 7 PM!",
            dateTime = "2024-12-05T17:00:00",
            imageUrl = "https://beertime.bg/wp-content/uploads/2022/10/IIPPAACan.webp",
            type = EventType.HAPPY_HOUR
        ),
        Event(
            id = "3",
            title = "NEW Happy Hour",
            description = "Enjoy 2-for-1 drinks from 5 PM to 7 PM!",
            dateTime = "2024-12-05T17:00:00",
            imageUrl = null,
            type = EventType.HAPPY_HOUR
        )
    )

    private fun generateMockNews(): List<News> = listOf(
        News(
            id = "1",
            title = "IIPPAACan ",
            description = "We’ve added new craft beers to our menu. Come and try them!",
            imageUrl = "https://beertime.bg/wp-content/uploads/2022/10/PModern.webp",
            type = NewsType.NEW_ARRIVAL
        ),
        News(
            id = "2",
            title = "Check out our updated hours for the holiday season.",
            description = "We are open everyday from 16pm to midnight",
            imageUrl = null,
            type = NewsType.OTHER_NEWS
        ),
        News(
            id = "3",
            title = "Perfeсt outmeal stout",
            description = "We’ve added new craft beers to our menu. Come and try them!",
            imageUrl = "https://beertime.bg/wp-content/uploads/2022/10/PModern.webp",
            type = NewsType.NEW_ARRIVAL
        ),
        News(
            id = "4",
            title = "Check out our updated hours for the holiday season.",
            description = "Check out our updated hours for the holiday season.",
            imageUrl = null,
            type = NewsType.OTHER_NEWS
        ),
        News(
            id = "5",
            title = "Perfeсt outmeal stout",
            description = "We’ve added new craft beers to our menu. Come and try them!",
            imageUrl = "https://beertime.bg/wp-content/uploads/2022/10/PModern.webp",
            type = NewsType.NEW_ARRIVAL
        ),
        News(
            id = "6",
            title = "IIPPAACan ",
            description = "We’ve added new craft beers to our menu. Come and try them!",
            imageUrl = "https://beertime.bg/wp-content/uploads/2022/10/PModern.webp",
            type = NewsType.NEW_ARRIVAL
        ),
    )
}
