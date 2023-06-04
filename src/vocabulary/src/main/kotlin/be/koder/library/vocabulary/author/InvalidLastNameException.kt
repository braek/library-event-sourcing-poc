package be.koder.library.vocabulary.author

class InvalidLastNameException(private var str: String) : RuntimeException(str) {
    init {
        this.str = "Cannot create LastName from string (str). The minimum length is 2 and the maximum length is 50."
    }
}