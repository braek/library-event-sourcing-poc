package be.koder.library.test

class TestUtils {
    companion object {
        @JvmStatic
        fun fail() {
            kotlin.test.fail("Should not be called")
        }
    }
}