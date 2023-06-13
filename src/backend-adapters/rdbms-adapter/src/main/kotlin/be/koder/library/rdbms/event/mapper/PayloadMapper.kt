package be.koder.library.rdbms.event.mapper

import be.koder.library.domain.author.event.AuthorCreated
import be.koder.library.domain.event.Event
import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import org.jooq.JSONB
import java.io.IOException
import java.lang.IllegalArgumentException

object PayloadMapper {

    private val objectMapper: ObjectMapper = ObjectMapper()

    fun convertPayloadToJson(event: Event): JSONB {
        if (event is AuthorCreated) {
            return write(
                AuthorCreatedPayload(
                    event.firstName.toString(), event.lastName.toString(), event.emailAddress.toString()
                )
            )
        }
        throw IllegalArgumentException("Cannot convert Event to JSON")
    }

    private fun write(payload: EventPayload): JSONB {
        try {
            return JSONB.valueOf(objectMapper.writeValueAsString(payload))
        } catch (e: JsonProcessingException) {
            throw RuntimeException(e)
        }
    }

    private fun <T : EventPayload> read(payload: JSONB, clazz: Class<T>): T {
        try {
            return objectMapper.readValue(payload.data(), clazz)
        } catch (e: IOException) {
            throw RuntimeException(e)
        }
    }

    fun convertJsonToPayload(json: JSONB, type: String): EventPayload {
        if (AuthorCreated::class.java.simpleName.equals(type)) {
            return read(json, AuthorCreatedPayload::class.java)
        }
        throw IllegalArgumentException("Cannot convert JSON to Event")
    }
}