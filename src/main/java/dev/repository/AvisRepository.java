package dev.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.entite.Avis;
import dev.entite.Collegue;

public interface AvisRepository extends JpaRepository<Avis, Integer> {
	List<Avis> findByCol(Collegue col);
}
