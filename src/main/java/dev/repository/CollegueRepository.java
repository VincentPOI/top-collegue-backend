package dev.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.entite.Collegue;

public interface CollegueRepository extends JpaRepository<Collegue, Integer> {
	Collegue findByNom(String nom);
}
