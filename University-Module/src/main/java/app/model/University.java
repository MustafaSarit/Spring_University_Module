package app.model;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class University {
	
	@Id
	private int id;
	
	private int api_id;
	
	private String name;
	private String city;
	private String web_page;
	private String type;
	
	private LocalDate founded_at;
	private LocalDate created;
	private LocalDate updated;
	
	@OneToMany(targetEntity = Student.class, mappedBy = "uni_id", orphanRemoval = true, fetch = FetchType.LAZY)
	private List<Student> students;
	
	// Constructors
	public University(){}
	
	public University(int id, String name, String city, LocalDate founded, String type, String web_page) {
		this.id = id;
		this.name = name;
		this.city = city;
		this.founded_at = founded;
		this.type = type;
		this.web_page = web_page;
	}
	
	

//---------------------------------------------------------------------------------------------------------------------------------------
// Getters and Setters
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getApi_id() {
		return api_id;
	}
	public void setApi_id(int api_id) {
		this.api_id = api_id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getWeb_page() {
		return web_page;
	}
	public void setWeb_page(String web_page) {
		this.web_page = web_page;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public LocalDate getFounded_at() {
		return founded_at;
	}
	public void setFounded_at(LocalDate founded_at) {
		this.founded_at = founded_at;
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
	public void setStudents(List<Student> students) {
		this.students = students;
	}
	public List<Student> getStudents() {
		return students;
	}
}
