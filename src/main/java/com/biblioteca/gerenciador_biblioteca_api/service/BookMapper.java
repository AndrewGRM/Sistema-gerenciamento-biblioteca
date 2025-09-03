package com.biblioteca.gerenciador_biblioteca_api.service;

import com.biblioteca.gerenciador_biblioteca_api.dto.BookResponseDTO;
import com.biblioteca.gerenciador_biblioteca_api.model.Book;

import java.time.LocalDate;

public class BookMapper {

    public static BookResponseDTO toDTO(Book book) {
       if(book == null) {
           return null;
       }

        LocalDate publicationDate = (book.getPublicationDate() != null) ? LocalDate.of(book.getPublicationDate(),1, 1 ) : null;

       return new BookResponseDTO.Builder()
               .withId(book.getId())
               .withTitle(book.getTitle())
               .withAuthor(book.getAutor())
               .withPublicationDate(publicationDate)
               .build();
    }
}
