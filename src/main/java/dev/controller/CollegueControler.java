package dev.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import dev.entite.Avis;
import dev.entite.Collegue;
import dev.entite.Vote;
import dev.entite.Vote.AVIS;
import dev.repository.AvisRepository;
import dev.repository.CollegueRepository;
import dev.repository.VoteRepository;

@RestController
@CrossOrigin
public class CollegueControler {

	@Autowired
	private CollegueRepository cr;
	@Autowired
	private VoteRepository vr;
	@Autowired
	private AvisRepository ar;

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
			vr.save(new Vote(c, AVIS.LIKE));
			c.setScore(c.getScore() + 10);
		} else if (action.get("action").equals("detester")) {
			vr.save(new Vote(c, AVIS.DISLIKE));
			c.setScore(c.getScore() - 5);
		}
		cr.save(c);
		return cr.findOne(c.getId());
	}

	@RequestMapping(value = "/votes", method = RequestMethod.GET)
	public List<Vote> findVotes(@RequestParam(value = "since") Optional<Integer> voteId) {
		int length = vr.findAll().size();
		int id = length - 2;
		List<Vote> votes = new ArrayList<>();
		if (voteId.isPresent()) {
			id = voteId.get();
		}
		for (int i = id; i <= length; i++) {
			votes.add(vr.findOne(i));
		}
		return votes;
	}

	@RequestMapping(value = "/avis/{nom}", method = RequestMethod.GET)
	public List<Avis> getAvis(@PathVariable String nom) {
		Collegue c = cr.findByNom(nom);
		return ar.findByCol(c);
	}

	@RequestMapping(value = "/avis", method = RequestMethod.POST)
	public Avis SaveAvis(@RequestBody Avis avis) {
		return ar.save(avis);
	}

}
