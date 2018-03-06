package edu.birzeit.controllers;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

import edu.birzeit.bo.Student;

@RestController
@RequestMapping("/")
public class StudentsController {

	ArrayList<Student> list;

	@RequestMapping(value = "/getAllStudents", method = RequestMethod.GET)
	public ArrayList<Student> getAllStudents() {
		if (list == null) {
			list = new ArrayList<Student>();

			Student s = new Student("John", "London", "CSE");
			list.add(s);

			s = new Student("Mike", "Dubai", "CS");
			list.add(s);

			s = new Student("Steve", "LA", "Marketing");
			list.add(s);
		}
		return list;
	}
	
	@RequestMapping(value = "/getStudentsWithMajor", method = RequestMethod.GET)
	public ArrayList<Student> getAllStudents(@RequestParam(value = "major", defaultValue = "CS") String major) {
		createPublicList();
		ArrayList<Student> res= new ArrayList<Student>();
		for(Student s: list){
			if(s.getMajor().equals(major)){
				res.add(s);
			}
		}
	
		return res;
	}
	

   public void createPublicList(){
	   if (list == null) {
			list = new ArrayList<Student>();

			Student s = new Student("John", "London", "CSE");
			list.add(s);

			s = new Student("Mike", "Dubai", "CS");
			list.add(s);

			s = new Student("Steve", "LA", "Marketing");
			list.add(s);
		}
   }
   
	@RequestMapping(value = "/addNewStudent", method = RequestMethod.POST)
	public HashMap<String, String> addNewStudent(@RequestBody HashMap<String,String> map) {
		
		HashMap<String, String> response = new HashMap<String, String>();
		Student student = new Student(map.get("name"), map.get("address"), map.get("major"));
		
		if (list == null) {
			list = new ArrayList<Student>();
		}
		list.add(student);
		
		String res="";
		
		for(Student s:list)
			res+=s.toString();

		response.put(res, "ok");
		return response;
	}
}