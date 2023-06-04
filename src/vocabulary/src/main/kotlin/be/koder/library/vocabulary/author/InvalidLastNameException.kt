package be.koder.library.vocabulary.author

class InvalidLastNameException(private var str: String) : RuntimeException(str) {
    init {
        this.str = String.format("Cannot create LastName from String (%s)", str)
    }
}