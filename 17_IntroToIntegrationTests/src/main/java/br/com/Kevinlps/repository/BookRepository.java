package br.com.Kevinlps.repository;

import br.com.Kevinlps.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book,Long> {
}
