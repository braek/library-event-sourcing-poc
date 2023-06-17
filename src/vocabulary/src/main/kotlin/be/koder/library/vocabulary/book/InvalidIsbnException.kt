package be.koder.library.vocabulary.book

class InvalidIsbnException(private var str: String) : RuntimeException(str) {
    init {
        this.str = "Cannot create ISBN from string (str). The maximum length is 100."
    }
}