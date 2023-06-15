package be.koder.library.rdbms.event

import be.koder.library.vocabulary.author.AuthorId
import be.koder.library.vocabulary.domain.AggregateId
import java.util.regex.Pattern

object TagMapper {

    fun map(str: String): AggregateId {
        val regex = Pattern.compile("^\\w+#\\w+$")
        if (regex.matcher(str).matches()) {
            val parts = str.split("#")
            val type = parts[0]
            val value = parts[1]
            if (Tag.AUTHOR.name.lowercase() == type) {
                return AuthorId.fromString(value)
            }
        }
        throw IllegalArgumentException(String.format("Cannot map String (%s) to AggregateId", str))
    }

    fun map(aggregateId: AggregateId): String {
        if (aggregateId is AuthorId) {
            return Tag.AUTHOR.name.lowercase() + "#" + aggregateId.toString()
        }
        throw IllegalArgumentException(String.format("Cannot map AggregateId (%s) to String", aggregateId.toString()))
    }

    enum class Tag {
        AUTHOR
    }
}