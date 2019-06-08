package app.controller;

import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import app.model.AppService;
import app.model.Student;

@RestController
public class StudentController {
	
	@Autowired
	private AppService service;
	
	@RequestMapping("/students")
	public List<LinkedHashMap<String, Object>> getAllStudents() {
		return service.getAllStudents();
	}
	
	@RequestMapping(method = RequestMethod.POST, value="/students")
	public LinkedHashMap<String, Object> addStudent(@RequestBody Student student) {
		return service.addStudent(student);
		
	}
	
	@RequestMapping(value = "/students/{id}")
	public LinkedHashMap<String, Object> getStudent(@PathVariable int id) {
		return service.getStudent(id);
	}
	
}