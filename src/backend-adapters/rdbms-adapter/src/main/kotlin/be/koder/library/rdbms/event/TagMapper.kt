package be.koder.library.rdbms.event

import be.koder.library.vocabulary.author.AuthorId
import be.koder.library.vocabulary.domain.AggregateId
import java.util.regex.Pattern

object TagMapper {

    fun map(str: String): AggregateId {
        val regex = Pattern.compile("^\\w+#\\w+$")
        if (regex.matcher(str).matches()) {
            val chunks = str.split("#")
            val type = chunks[0]
            val value = chunks[1]
            if (AuthorId::class.java.simpleName.equals(type)) {
                return AuthorId.fromString(value)
            }
        }
        throw IllegalArgumentException("Cannot create AggregateId from String")
    }

    fun map(aggregateId: AggregateId): String {
        return aggregateId.javaClass.simpleName + "#" + aggregateId.getValue().toString()
    }
}