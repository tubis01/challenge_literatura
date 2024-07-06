package com.alurachallenge.challenge_literatura.repository;

import com.alurachallenge.challenge_literatura.models.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AutorRepository extends JpaRepository<Autor, Long> {

}
