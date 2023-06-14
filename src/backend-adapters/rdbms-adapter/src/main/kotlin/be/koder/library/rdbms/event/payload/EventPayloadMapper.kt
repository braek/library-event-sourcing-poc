package be.koder.library.rdbms.event.payload

import be.koder.library.domain.author.event.AuthorCreated
import be.koder.library.domain.author.event.AuthorModified
import be.koder.library.domain.author.event.AuthorRemoved
import be.koder.library.domain.event.Event
import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import org.jooq.JSONB
import java.io.IOException

object EventPayloadMapper {

    private val objectMapper: ObjectMapper = ObjectMapper()

    fun convertPayloadToJson(event: Event): JSONB {
        if (event is AuthorCreated) {
            return write(
                AuthorCreatedPayload(
                    event.firstName.toString(),
                    event.lastName.toString(),
                    event.emailAddress.toString()
                )
            )
        }
        if (event is AuthorModified) {
            return write(
                AuthorModifiedPayload(
                    event.firstName.toString(),
                    event.lastName.toString(),
                    event.emailAddress.toString()
                )
            )
        }
        if (event is AuthorRemoved) {
            return write(AuthorRemovedPayload())
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

    fun <T : EventPayload> read(payload: JSONB, clazz: Class<T>): T {
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
        if (AuthorModified::class.java.simpleName.equals(type)) {
            return read(json, AuthorModifiedPayload::class.java)
        }
        if (AuthorRemoved::class.java.simpleName.equals(type)) {
            return read(json, AuthorRemovedPayload::class.java)
        }
        throw IllegalArgumentException("Cannot convert JSON to Event")
    }
}