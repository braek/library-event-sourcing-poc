package be.koder.library.vocabulary.author

data class AuthorListItem(
    val id: AuthorId,
    val firstName: FirstName,
    val lastName: LastName,
    val emailAddress: EmailAddress
)