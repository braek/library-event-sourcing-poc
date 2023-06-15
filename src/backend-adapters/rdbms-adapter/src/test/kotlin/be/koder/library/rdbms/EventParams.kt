package be.koder.library.rdbms

import be.koder.library.domain.author.event.AuthorCreated
import be.koder.library.domain.author.event.AuthorModified
import be.koder.library.domain.author.event.AuthorRemoved
import be.koder.library.vocabulary.author.AuthorId
import be.koder.library.vocabulary.author.EmailAddress
import be.koder.library.vocabulary.author.FirstName
import be.koder.library.vocabulary.author.LastName
import org.junit.jupiter.api.extension.ExtensionContext
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.ArgumentsProvider
import java.util.stream.Stream

class EventParams : ArgumentsProvider {
    override fun provideArguments(context: ExtensionContext?): Stream<out Arguments> {
        return Stream.of(
            Arguments.of(AuthorCreated(
                AuthorId.createNew(),
                FirstName("Bruce"),
                LastName("Wayne"),
                EmailAddress("batman@gothamcity.com")
            )),
            Arguments.of(AuthorModified(
                AuthorId.createNew(),
                FirstName("Arthur"),
                LastName("Fleck"),
                EmailAddress("joker@gothamcity.com")
            )),
            Arguments.of(AuthorRemoved(
                AuthorId.createNew()
            ))
        )
    }
}