package app.model;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AppService {

	@Autowired
	private UniversityRepo uniRepo;
	
	// Adds university or universities manually
	public void addUniversities(University[] universities) {
		LocalDate date = LocalDate.now();
		for(int i = 0; i < universities.length; i++) {
			universities[i].setCreated(date);
			universities[i].setApi_id(universities[i].getId());
			uniRepo.save(universities[i]);
		}
	}

	// Reads all universities from given URL (this function will be called automatically when application started)
	@SuppressWarnings("unchecked")
	public void autoAddUniversities(){
		JSONParser parser = new JSONParser();
		try {         
			URL url = new URL("https://gitlab.com/kodiasoft/intern/2019/snippets/1859421/raw");
			URLConnection yc = url.openConnection();
			BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
	            
			JSONArray universityList = (JSONArray) parser.parse(in);
			universityList.forEach( uni -> parseUniversityObject((JSONObject) uni));
			in.close();
	            
		} catch (FileNotFoundException e) {
	            e.printStackTrace();
		} catch (IOException e) {
	            e.printStackTrace();
		} catch (ParseException e) {
				e.printStackTrace();
		};
    }
	
	// Saves all universities to database
	private void parseUniversityObject(JSONObject Juni) {
		 DateTimeFormatter format = new DateTimeFormatterBuilder()
	                .appendPattern("yyyy")
	                .parseDefaulting(ChronoField.MONTH_OF_YEAR, 1)
	                .parseDefaulting(ChronoField.DAY_OF_MONTH, 1)
	                .toFormatter();
		 
		int id = ((Long) Juni.get("id")).intValue();
		String year = (String) Juni.get("founded_at");
		University uni = new University(id,
										(String) Juni.get("name"),
										(String) Juni.get("city"),
										LocalDate.parse(year, format),
										(String) Juni.get("type"),
										(String) Juni.get("web_page"));
		uni.setCreated(LocalDate.now());
		uni.setApi_id(uni.getId());
		uniRepo.save(uni);
	}

	// Returns all universities from database
	public List<LinkedHashMap<String, Object>> getAllUniversities() {
		List<LinkedHashMap<String, Object>> set = new ArrayList<>();
		for(int i = 0; i < uniRepo.count(); i++) {
			LinkedHashMap<String, Object> map = new LinkedHashMap<>();
			map.put("id", uniRepo.findById(i).get().getId());
			map.put("name", uniRepo.findById(i).get().getName());
			set.add(map);
		}
		return set;
	}
	
	// Returns specified university(by its id)
	public LinkedHashMap<String, Object> getUniversity(int id) {
		LinkedHashMap<String, Object> map = new LinkedHashMap<>();
		if(uniRepo.findById(id).isPresent()) {
			List<LinkedHashMap<String, Object>> students = new ArrayList<>();
			for(int i = 1; i <= stuRepo.count(); i++) {
				if(stuRepo.findById(i).get().getUni_id() == id) {
					LinkedHashMap<String, Object> stuMap = new LinkedHashMap<>();
					stuMap.put("id", stuRepo.findById(i).get().getId());
					stuMap.put("name", stuRepo.findById(i).get().getName());
					stuMap.put("started_at", stuRepo.findById(i).get().getStarted_at());
					students.add(stuMap);
				}
			
			}
			
			map.put("id", uniRepo.findById(id).get().getId());
			map.put("api_id", uniRepo.findById(id).get().getApi_id());
			map.put("name", uniRepo.findById(id).get().getName());
			map.put("city", uniRepo.findById(id).get().getCity());
			map.put("founded_at", uniRepo.findById(id).get().getFounded_at());
			map.put("web_page", uniRepo.findById(id).get().getWeb_page());
			map.put("type", uniRepo.findById(id).get().getType());
			map.put("students", students);
			return map;
		}else {
			map.put("status", "error");
			map.put("message", id + " numaralı üniversite kaydı bulunamadı");
			return map;
		}
	}

//	University Services	
//---------------------------------------------------------------------------------------------------------------------------------------
//	Student Services

	@Autowired
	private StudentRepo stuRepo;
	
	// Adds a student to database
	public LinkedHashMap<String, Object> addStudent(Student student) {
		LinkedHashMap<String, Object> map = new LinkedHashMap<>();
		LinkedHashMap<String, Object> error = new LinkedHashMap<>();
		LocalDate date = LocalDate.now();
		if(student.getName() == null || student.getName().equals("")) {
			map.put("status", "error");
			map.put("message", "Öğrenci eklenirken hata oluştu");
			error.put("key", "name");
			error.put("error", "Öğrenci adı boş bırakılamaz");
			map.put("errors", error);
			return map;
		}else if(student.getStarted_at().isAfter(date)) {
			map.put("status", "error");
			map.put("message", "Öğrenci eklenirken hata oluştu");
			error.put("key", "started_at");
			error.put("error", "Öğrencinin üniversiteye başlangıç tarihi bugünden büyük olamaz");
			map.put("errors", error);
			return map;
		}else {
			student.setCreated(date);
			student.setUniversity(uniRepo.findById(student.getUni_id()).get());
			stuRepo.save(student);
			map.put("id", student.getId());
			map.put("status", "success");
			map.put("message", student.getName() + " adlı öğrenci " + student.getUniversity().getName() + "ne başarıyla eklendi.");	
			return map;
		}
	}
	
	// Updates Student
	public void updateStudent(Student student, int id) {
	}
	
	// Returns all students from database
	public List<LinkedHashMap<String, Object>> getAllStudents() {
		List<LinkedHashMap<String, Object>> set = new ArrayList<>();
		for(int i = 1; i <= stuRepo.count(); i++) {
			LinkedHashMap<String, Object> map = new LinkedHashMap<>();
			map.put("id", stuRepo.findById(i).get().getId());
			map.put("name", stuRepo.findById(i).get().getName());
			map.put("university", stuRepo.findById(i).get().getUniversity().getName());
			set.add(map);
		}
		return set;
	}

	// Returns specified student(by its id)
	public LinkedHashMap<String, Object> getStudent(int id) {
		LinkedHashMap<String, Object> map = new LinkedHashMap<>();
		if(stuRepo.findById(id).isPresent()) {
			LinkedHashMap<String, Object> uni = new LinkedHashMap<>();
			uni.put("id", stuRepo.findById(id).get().getUniversity().getId());
			uni.put("name", stuRepo.findById(id).get().getUniversity().getName());
			uni.put("founded_at", stuRepo.findById(id).get().getUniversity().getFounded_at());
			uni.put("type", stuRepo.findById(id).get().getUniversity().getType());
			
			map.put("id", stuRepo.findById(id).get().getId());
			map.put("name", stuRepo.findById(id).get().getName());
			map.put("started_at", stuRepo.findById(id).get().getStarted_at());
			map.put("university", uni);
			return map;
		}else {
			map.put("status", "error");
			map.put("message", id + " numaralı öğrenci kaydı bulunamadı");
			return map;
		}
	}
	
}
