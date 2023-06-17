package be.koder.library.vocabulary.book

class InvalidTitleException(private var str: String) : RuntimeException(str) {
    init {
        this.str = "Cannot create Title from string (str). The maximum length is 100."
    }
}