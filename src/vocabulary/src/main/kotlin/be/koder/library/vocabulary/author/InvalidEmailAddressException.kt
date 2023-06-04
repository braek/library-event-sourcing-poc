package be.koder.library.vocabulary.author

class InvalidEmailAddressException(private var str: String) : RuntimeException(str) {
    init {
        this.str = "Cannot create EmailAddress from string (str). The maximum length is 100."
    }
}