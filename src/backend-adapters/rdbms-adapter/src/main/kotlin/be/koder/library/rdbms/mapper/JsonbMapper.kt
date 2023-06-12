package be.koder.library.rdbms.mapper

import be.koder.library.domain.event.Event
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
}