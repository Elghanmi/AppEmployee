package com.mongodb.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.mongodb.model.Employee;

@Repository
public interface EmployeeRepository extends MongoRepository<Employee, Long>{
	@Query("{$or :[{firstName: ?0},{lastName: ?1},{emailId: ?2}]}")            //SQL Equivalent : select count(*) from book where author=? or name=?
    List<Employee> getEmplyesByNameOrEmail(String author, String name,String email);
	
    @Query(value = "{author:?0}", sort= "{name:1}") //ASC
    List<Employee> getEmplyeesDESCByFirstname(String author);
    
    @Query(value = "{author=?0}", sort= "{name:-1}") //DESC
    List<Employee> getEmplyeesASCByFirstname(String author);
   
    @Query("{}")
	public List<Employee> sortAndLimit1(Pageable pageable);
}
