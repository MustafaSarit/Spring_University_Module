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
import app.model.University;

@RestController
public class UniversityController {
	
	@Autowired
	private AppService service;
	
	@RequestMapping("/universities")
	public List<LinkedHashMap<String, Object>> getAllUniversities() {
		return service.getAllUniversities();
	}
	
	@RequestMapping("/universities/{id}")
	public LinkedHashMap<String, Object> getUniversity(@PathVariable int id) {
		return service.getUniversity(id);
	}
	
	@RequestMapping(method = RequestMethod.POST, value="/universities")
	public void addUniversities(@RequestBody University[] universities) {
		service.addUniversities(universities);
	}
	
}