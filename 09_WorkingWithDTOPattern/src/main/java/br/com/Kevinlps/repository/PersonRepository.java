package br.com.Kevinlps.repository;

import br.com.Kevinlps.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person,Long> {
}
