package be.koder.library.vocabulary.author

class InvalidLastNameException(private var str: String) : RuntimeException(str) {
    init {
        this.str = "Cannot create LastName from string (str)"
    }
}