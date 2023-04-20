package jp.co.axa.apidemo.controllers;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jp.co.axa.apidemo.entities.Employee;
import jp.co.axa.apidemo.services.EmployeeService;

@RestController
@RequestMapping("/api/v1")
public class EmployeeController {

    private final EmployeeService employeeService;

    private EmployeeController(final EmployeeService employeeService) {
    	this.employeeService = employeeService;
    }

    @GetMapping("/employees")
    public List<Employee> getEmployees() {
        final List<Employee> employees = employeeService.retrieveEmployees();
        return employees;
    }

    @GetMapping("/employees/{employeeId}")
    public Employee getEmployee(final @PathVariable(name="employeeId")Long employeeId) {
    	try {
    		return employeeService.getEmployee(employeeId);
    	} catch (NoSuchElementException e) {
    		return Employee.EMPTY;
    	}
        
    }

    @PostMapping("/employees")
    public void saveEmployee(final Employee employee){
        employeeService.saveEmployee(employee);
        System.out.println("Employee Saved Successfully");
    }

    @PostMapping("/employees/{employeeId}/deletion")
    public void deleteEmployee(final @PathVariable(name="employeeId")Long employeeId){
    	try {
    		employeeService.deleteEmployee(employeeId);
    		System.out.println("Employee Deleted Successfully");
    	} catch (EmptyResultDataAccessException e) {
    		
    		// implement later: implement procedure when requested employeeId does not stored
    		System.out.println("employeeId: " + employeeId + " delete operation requested, but not exist.");
    	}        
    }

    @PutMapping("/employees/{employeeId}")
    public void updateEmployee(final @RequestBody Employee employee,
                               final @PathVariable(name="employeeId")Long employeeId){
        final Employee emp = employeeService.getEmployee(employeeId);
        if(emp == Employee.EMPTY){
            employeeService.updateEmployee(employee);
        }

    }

}
