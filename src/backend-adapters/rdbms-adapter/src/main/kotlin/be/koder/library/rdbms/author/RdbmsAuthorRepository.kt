package be.koder.library.rdbms.author

import be.koder.library.domain.author.Author
import be.koder.library.domain.author.AuthorRepository
import be.koder.library.domain.author.event.AuthorRemoved
import be.koder.library.rdbms.event.RdbmsEventStore
import be.koder.library.vocabulary.author.AuthorId
import org.springframework.transaction.annotation.Transactional
import java.util.*

open class RdbmsAuthorRepository(private val eventStore: RdbmsEventStore) : AuthorRepository {

    override fun get(id: AuthorId): Optional<Author> {
        val eventStream = eventStore.query(id)
        if (eventStream.isEmpty() || eventStream.containsEventOfType(AuthorRemoved::class.simpleName!!)) {
            return Optional.empty()
        }
        return Optional.of(Author(eventStream))
    }

    @Transactional
    override fun save(aggregate: Author) {
        // TODO: update inline projections
        eventStore.appendMutations(aggregate)
    }
}