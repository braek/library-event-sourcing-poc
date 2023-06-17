package be.koder.library.domain.book

import be.koder.library.domain.repository.Repository
import be.koder.library.vocabulary.book.BookId

interface BookRepository : Repository<BookId, Book>