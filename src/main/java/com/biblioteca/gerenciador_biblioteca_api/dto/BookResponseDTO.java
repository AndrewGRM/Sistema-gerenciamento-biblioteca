package com.biblioteca.gerenciador_biblioteca_api.dto;

import java.time.LocalDate;

public class BookResponseDTO {

    Long id;
    String title;
    String author;
    LocalDate publicationDate;

    public BookResponseDTO(Builder builder) {
        this.id = builder.id;
        this.title = builder.title;
        this.author = builder.author;
        this.publicationDate = builder.publicationDate;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public LocalDate getPublicationDate() {
        return publicationDate;
    }

    public static class Builder {
        private Long id;
        private String title;
        private String author;
        private LocalDate publicationDate;

        public Builder withId(Long id) {
            this.id = id;
            return this;
        }

        public Builder withTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder withAuthor(String author) {
            this.author = author;
            return this;
        }

        public Builder withPublicationDate(LocalDate publicationDate){
            this.publicationDate = publicationDate;
            return this;
        }

        public BookResponseDTO build(){
            return new BookResponseDTO(this);
        }
    }
}
