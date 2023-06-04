package be.koder.library.vocabulary.author

class InvalidFirstNameException(private var str: String) : RuntimeException(str) {
    init {
        this.str = "Cannot create FirstName from string (str)"
    }
}