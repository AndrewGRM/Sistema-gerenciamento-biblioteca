package com.biblioteca.gerenciador_biblioteca_api.service;

import com.biblioteca.gerenciador_biblioteca_api.dto.BookResponseDTO;
import com.biblioteca.gerenciador_biblioteca_api.model.Book;

import java.time.LocalDate;

public class BookMapper {

    public static BookResponseDTO toDTO(Book book) {
        return new BookResponseDTO.Builder()
                .withId(book.getId())
                .withTitle(book.getTitle())
                .withAuthor(book.getAutor())
                .withPublicationDate(LocalDate.of(book.getAnoPublicacao(), 1, 1))
                .build();
    }
}
