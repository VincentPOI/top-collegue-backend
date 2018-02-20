package dev.entite;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "VOTE")
public class Vote {

	public enum AVIS {
		LIKE, DISLIKE
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Integer id;

	@ManyToOne
	private Collegue col;
	@Column(name = "AVIS")
	@Enumerated(EnumType.STRING)
	private AVIS avis;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Collegue getCol() {
		return col;
	}

	public void setCol(Collegue col) {
		this.col = col;
	}

	public AVIS getAction() {
		return avis;
	}

	public void setAction(AVIS avis) {
		this.avis = avis;
	}

	public Vote() {
	}

	public Vote(Collegue col, AVIS avis) {
		super();
		this.col = col;
		this.avis = avis;
	}

}
