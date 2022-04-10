package com.mongodb.controller;
import org.bson.Document;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.mongodb.exception.ResourceNotFoundException;
import com.mongodb.model.Employee;
import com.mongodb.repository.EmployeeRepository;
import com.mongodb.service.SequenceGeneratorService;

@Controller
public class EmployeeController {
	@Autowired
	private EmployeeRepository employeeRepository;
	
	@Autowired
	private SequenceGeneratorService sequenceGeneratorService;

	

	
	
	@GetMapping("/index")
	public String ajoutEmployee(Model m) {
		//employeeRepository.findAll();
		Employee employee = new Employee();
		employee.setId(new Random().nextLong());
		m.addAttribute("employee", employee);
		return "logIN";
	}

	@GetMapping("/employees")
	public String getEmployees(Model m)
			throws ResourceNotFoundException {
		m.addAttribute("listemployee", employeeRepository.findAll());
		return "employes" ;
	}


	@PostMapping("/saveemployee")
	public String createEmployee(Employee employee) {
		//employee.setId(sequenceGeneratorService.generateSequence(Employee.SEQUENCE_NAME));
		employeeRepository.save(employee);
		return "redirect:/employees" ;
	}

	@GetMapping("/ModifierEmployee")
	public String updateEmployee(Long id,Model m) throws ResourceNotFoundException {
		Employee employee = employeeRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: " + id));

		m.addAttribute("employee", employee);
		return "logIN";
	}
	@PostMapping("/filtrerByString")
	public String filtrerByString(String firstname,String lastname,String email,Model m) {
		m.addAttribute("listemployee", employeeRepository.getEmplyesByNameOrEmail(firstname, lastname, email)) ;
		return "employes" ;
	}
	
	@GetMapping("/Sorting")
	public String Sorting(int cle,String name,Model m) {
		List<Employee> li=null;
			if(cle==1) {li=employeeRepository.sortAndLimit1(PageRequest.of(0,(int) employeeRepository.count() , Sort.by(Direction.ASC, name)));}
			else {li=employeeRepository.sortAndLimit1(PageRequest.of(0,(int) employeeRepository.count() , Sort.by(Direction.DESC, name)));}
		
		m.addAttribute("listemployee", li) ;
		return "employes" ;
	}
	@GetMapping("/deleteEmployee")
	public String deleteEmployee(Long id)
			throws ResourceNotFoundException {
		Employee employee = employeeRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: " + id));

		employeeRepository.delete(employee);
		return "redirect:/employees" ;
	}
}
