package com.biblioteca.gerenciador_biblioteca_api.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;

    private String autor;

    private Integer PublicationDate;

    private String genero;

    public Book(Long id, String titulo, String autor, Integer PublicationDate, String genero) {
        this.id = id;
        this.titulo = titulo;
        this.autor = autor;
        this.PublicationDate = PublicationDate;
        this.genero = genero;
    }

    public Book(){

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return titulo;
    }

    public void setTitle(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public Integer getAnoPublicacao() {
        return PublicationDate;
    }

    public void setAnoPublicacao(Integer anoPublicacao) {
        this.PublicationDate = PublicationDate;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }
}
