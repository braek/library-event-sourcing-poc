package be.koder.library.rdbms.event.stored

import be.koder.library.domain.author.event.AuthorCreated
import be.koder.library.domain.author.event.AuthorModified
import be.koder.library.domain.author.event.AuthorRemoved
import be.koder.library.domain.event.Event
import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import org.jooq.JSONB
import java.io.IOException

object StoredEventMapper {

    private val objectMapper: ObjectMapper = ObjectMapper()

    init {
        objectMapper.registerModule(KotlinModule())
    }

    fun toJsonb(event: Event): JSONB {
        if (event is AuthorCreated) {
            return write(StoredAuthorCreated(event))
        }
        if (event is AuthorModified) {
            return write(StoredAuthorModified(event))
        }
        if (event is AuthorRemoved) {
            return write(StoredAuthorRemoved(event))
        }
        throw IllegalArgumentException("Cannot convert Event to JSON")
    }

    private fun write(payload: StoredEvent): JSONB {
        try {
            return JSONB.valueOf(objectMapper.writeValueAsString(payload))
        } catch (e: JsonProcessingException) {
            throw RuntimeException(e)
        }
    }

    fun toEvent(json: JSONB, type: String): Event {
        if (AuthorCreated::class.java.simpleName.equals(type)) {
            return read(json, StoredAuthorCreated::class.java).toEvent()
        }
        if (AuthorModified::class.java.simpleName.equals(type)) {
            return read(json, StoredAuthorModified::class.java).toEvent()
        }
        if (AuthorRemoved::class.java.simpleName.equals(type)) {
            return read(json, StoredAuthorRemoved::class.java).toEvent()
        }
        throw IllegalArgumentException("Cannot convert JSON to Event")
    }

    private fun <T : StoredEvent> read(payload: JSONB, clazz: Class<T>): T {
        try {
            return objectMapper.readValue(payload.data(), clazz)
        } catch (e: IOException) {
            throw RuntimeException(e)
        }
    }
}