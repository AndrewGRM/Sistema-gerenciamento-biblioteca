package com.biblioteca.gerenciador_biblioteca_api.service;

import com.biblioteca.gerenciador_biblioteca_api.model.Book;
import com.biblioteca.gerenciador_biblioteca_api.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    public Book saveBook(Book book) {
        Book saveBook = bookRepository.save(book);
        return saveBook;
    }

    public List<Book> findAllBooks() {
        List<Book> allBooks = bookRepository.findAll();
        return allBooks;
    }

    public Optional<Book> findBookById(Long id) {
        Optional<Book> BookById = bookRepository.findById(id);
        return BookById;
    }

    public Optional<Book> updateBook(Long id, Book book) {
        Optional<Book> bookById = bookRepository.findById(id);
        if (bookById.isPresent()) {
            Book existingBook = bookById.get();
            existingBook.setTitulo(book.getTitulo());
            existingBook.setAutor(book.getAutor());
            existingBook.setGenero(book.getGenero());
            existingBook.setAnoPublicacao(book.getAnoPublicacao());
            bookRepository.save(existingBook);
            return Optional.of(bookRepository.save(existingBook));
        } else {
            return  Optional.empty();
        }
    }

    public boolean deleteBookById(Long id) {
        boolean bookExists = bookRepository.existsById(id);
        if(bookExists) {
            bookRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }
}
