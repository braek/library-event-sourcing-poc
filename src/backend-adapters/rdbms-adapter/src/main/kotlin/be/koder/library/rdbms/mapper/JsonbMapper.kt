package be.koder.library.rdbms.mapper

import be.koder.library.domain.event.Event
import be.koder.library.vocabulary.domain.AggregateId
import be.koder.library.vocabulary.event.EventId
import be.koder.library.vocabulary.time.Timestamp
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.databind.ObjectMapper
import org.jooq.JSONB
import java.io.IOException

object JsonbMapper {

    private val objectMapper: ObjectMapper = ObjectMapper()

    init {
        objectMapper.addMixIn(Event::class.java, EventMixin::class.java)
    }

    fun write(event: Event): JSONB {
        try {
            return JSONB.valueOf(objectMapper.writeValueAsString(event))
        } catch (e: IOException) {
            throw RuntimeException(e)
        }
    }

    abstract class EventMixin {

        @JsonIgnore
        abstract fun id(): EventId

        @JsonIgnore
        abstract fun occurredOn(): Timestamp

        @JsonIgnore
        abstract fun tags(): Set<AggregateId>
    }
}