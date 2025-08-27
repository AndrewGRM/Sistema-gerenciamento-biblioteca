package com.biblioteca.gerenciador_biblioteca_api.service;

import com.biblioteca.gerenciador_biblioteca_api.dto.BookResponseDTO;
import com.biblioteca.gerenciador_biblioteca_api.exception.ResourceNotFoundException;
import com.biblioteca.gerenciador_biblioteca_api.model.Book;
import com.biblioteca.gerenciador_biblioteca_api.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    public Book saveBook(Book book) {
        Book saveBook = bookRepository.save(book);
        return saveBook;
    }

    public Page<BookResponseDTO> findAllBooks(Pageable pageable) {
        Page<Book> bookPage = bookRepository.findAll(pageable);
        return bookPage.map(BookMapper::toDTO);
    }

    public BookResponseDTO findBookById(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Livro não encontrado com o ID: " + id));
        return BookMapper.toDTO(book);
    }

    public Optional<Book> updateBook(Long id, Book book) {
        Optional<Book> bookById = bookRepository.findById(id);
        if (bookById.isPresent()) {
            Book existingBook = bookById.get();
            existingBook.setTitle(book.getTitle());
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
