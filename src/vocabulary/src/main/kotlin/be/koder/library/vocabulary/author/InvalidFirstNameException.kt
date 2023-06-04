package be.koder.library.vocabulary.author

class InvalidFirstNameException(private var str: String) : RuntimeException(str) {
    init {
        this.str = "Cannot create FirstName from string (str). The minimum length is 2 and the maximum length is 50."
    }
}