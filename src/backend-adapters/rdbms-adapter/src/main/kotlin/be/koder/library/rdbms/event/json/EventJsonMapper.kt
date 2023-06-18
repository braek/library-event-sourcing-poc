package be.koder.library.rdbms.event.json

import be.koder.library.domain.author.event.AuthorCreated
import be.koder.library.domain.author.event.AuthorModified
import be.koder.library.domain.author.event.AuthorRemoved
import be.koder.library.domain.event.Event
import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import org.jooq.JSONB
import java.io.IOException

object EventJsonMapper {

    private val objectMapper: ObjectMapper = ObjectMapper()

    init {
        objectMapper.registerModule(KotlinModule())
    }

    fun toJson(event: Event): JSONB {
        if (event is AuthorCreated) {
            return write(AuthorCreatedJson(event))
        }
        if (event is AuthorModified) {
            return write(AuthorModifiedJson(event))
        }
        if (event is AuthorRemoved) {
            return write(AuthorRemovedJson(event))
        }
        throw IllegalArgumentException(String.format("Cannot convert Event (%s) to JSON", event.javaClass.simpleName))
    }

    private fun write(payload: EventJson<*>): JSONB {
        try {
            return JSONB.valueOf(objectMapper.writeValueAsString(payload))
        } catch (e: JsonProcessingException) {
            throw RuntimeException(e)
        }
    }

    fun toEvent(json: JSONB, type: String): Event {
        if (AuthorCreated::class.java.simpleName.equals(type)) {
            return read(json, AuthorCreatedJson::class.java).toEvent()
        }
        if (AuthorModified::class.java.simpleName.equals(type)) {
            return read(json, AuthorModifiedJson::class.java).toEvent()
        }
        if (AuthorRemoved::class.java.simpleName.equals(type)) {
            return read(json, AuthorRemovedJson::class.java).toEvent()
        }
        throw IllegalArgumentException(String.format("Cannot convert JSON to Event (%s)", type))
    }

    private fun <T : EventJson<*>> read(payload: JSONB, clazz: Class<T>): T {
        try {
            return objectMapper.readValue(payload.data(), clazz)
        } catch (e: IOException) {
            throw RuntimeException(e)
        }
    }
}