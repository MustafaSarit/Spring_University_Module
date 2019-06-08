package app.model;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;


@Entity
public class Student {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	private int uni_id;
	
	private String name;
	
	private LocalDate started_at;
	private LocalDate created;
	private LocalDate updated;
	
	@ManyToOne(targetEntity = University.class)
	private University university;

	// Constructors
	public Student(){}
	public Student(int uni_id, String name, LocalDate started) {
		this.uni_id = uni_id;
		this.name = name;
		this.started_at = started;
	}
	
	
//---------------------------------------------------------------------------------------------------------------------------------------
// Getters and Setters
	
	public University getUniversity() {
		return university;
	}
	public void setUniversity(University university) {
		this.university = university;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getUni_id() {
		return uni_id;
	}
	public void setUni_id(int uni_id) {
		this.uni_id = uni_id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public LocalDate getStarted_at() {
		return started_at;
	}
	public void setStarted_at(LocalDate started_at) {
		this.started_at = started_at;
	}
	public LocalDate getCreated() {
		return created;
	}
	public void setCreated(LocalDate created) {
		this.created = created;
	}
	public LocalDate getUpdated() {
		return updated;
	}
	public void setUpdated(LocalDate updated) {
		this.updated = updated;
	}
}
