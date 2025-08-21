package com.biblioteca.gerenciador_biblioteca_api.repository;

import com.biblioteca.gerenciador_biblioteca_api.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

}
