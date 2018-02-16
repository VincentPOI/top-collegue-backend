package dev.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import dev.entite.Collegue;
import dev.repository.CollegueRepository;

@RestController
@CrossOrigin
public class CollegueControler {

	@Autowired
	private CollegueRepository cr;

	@RequestMapping(value = "/collegues", method = RequestMethod.GET)
	public List<Collegue> findAllCollegue() {
		return cr.findAll();
	}

	@RequestMapping(value = "/collegues", method = RequestMethod.POST)
	public ResponseEntity<Collegue> SaveCollegue(@RequestBody Collegue collegue) {
		if (cr.findByNom(collegue.getNom()) == null) {
			cr.save(collegue);
			return ResponseEntity.status(HttpStatus.OK).body(cr.findByNom(collegue.getNom()));
		}
		return ResponseEntity.notFound().build();
	}

	@RequestMapping(value = "/collegues/{nom}", method = RequestMethod.GET)
	public Collegue FindCollegueByNom(@PathVariable String nom) {
		return cr.findByNom(nom);
	}

	@RequestMapping(value = "/collegues/{nom}", method = RequestMethod.PATCH)
	public Collegue JudgeCollegue(@PathVariable String nom, @RequestBody Map<String, String> action) {

		Collegue c = cr.findByNom(nom);

		if (action.get("action").equals("aimer")) {
			c.setScore(c.getScore() + 10);
		} else if (action.get("action").equals("detester")) {
			c.setScore(c.getScore() - 5);
		}
		cr.save(c);
		return cr.findOne(c.getId());
	}

}
