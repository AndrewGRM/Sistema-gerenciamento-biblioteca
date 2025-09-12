package com.biblioteca.gerenciador_biblioteca_api.controller;

import com.biblioteca.gerenciador_biblioteca_api.dto.BookResponseDTO;
import com.biblioteca.gerenciador_biblioteca_api.model.Book;
import com.biblioteca.gerenciador_biblioteca_api.service.BookService;
import com.biblioteca.gerenciador_biblioteca_api.util.ApiConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping(ApiConstants.BOOKS_URL)
public class BookController {

    @Autowired
    private BookService bookService;

    @PostMapping()
    public Book saveBook(@RequestBody Book book) {
        return bookService.saveBook(book);
    }

    @GetMapping()
    public ResponseEntity<Page<BookResponseDTO>> findAllBooks(Pageable pageable) {
        return ResponseEntity.ok().body(bookService.findAllBooks(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookResponseDTO> findBookById(@PathVariable Long id) {
        BookResponseDTO bookDTO = bookService.findBookById(id);
        return ResponseEntity.ok().body(bookDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable Long id,@RequestBody Book book ) {
        Optional<Book> updatedBookOptional = bookService.updateBook(id, book);
        if(updatedBookOptional.isPresent()) {
            Book updatedBook = updatedBookOptional.get();
            return ResponseEntity.ok().body(updatedBook);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Book> deleteBookById(@PathVariable Long id) {
        boolean deletedBook = bookService.deleteBookById(id);
        if(deletedBook) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
