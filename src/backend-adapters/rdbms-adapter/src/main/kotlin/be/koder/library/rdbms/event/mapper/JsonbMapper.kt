package be.koder.library.rdbms.event.mapper

import be.koder.library.domain.event.Event
import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import org.jooq.JSONB
import java.lang.IllegalArgumentException

object JsonbMapper {

    private val objectMapper: ObjectMapper = ObjectMapper()

    fun toJson(event: Event): JSONB {
        try {
            return JSONB.valueOf(objectMapper.writeValueAsString(event))
        } catch (e: JsonProcessingException) {
            throw RuntimeException(e)
        }
    }

    fun toEvent(json: JSONB, type: String): Event {
        throw IllegalArgumentException("Cannot map type to Event")
    }
}