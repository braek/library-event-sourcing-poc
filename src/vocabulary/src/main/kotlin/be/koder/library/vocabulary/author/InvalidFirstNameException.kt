package be.koder.library.vocabulary.author

class InvalidFirstNameException(private var str: String) : RuntimeException(str) {
    init {
        this.str = String.format("Cannot create LastName from String (%s)", str)
    }
}