package com.bytecodevelocity.service;

import com.bytecodevelocity.exception_handler.EmployeeNotFound;
import com.bytecodevelocity.model.Department;
import com.bytecodevelocity.model.DepartmentRepository;
import com.bytecodevelocity.model.Employee;
import com.bytecodevelocity.model.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
public class EmployeeRepoController {

    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    DepartmentRepository departmentRepository;

    @GetMapping("/jpa/employees")
    public List<Employee> getAll() {
        return employeeRepository.findAll();
    }

    @GetMapping("/jpa/employees/{empId}")
    public EntityModel<Employee> getEmployeeById(@PathVariable Long empId){
        Employee employee = employeeRepository.findById(empId).get();
        if(null == employee)
            throw new EmployeeNotFound("Employee Not Found .");
        EntityModel<Employee> model = EntityModel.of(employee);
        Link link = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getAll()).withRel("all-employees");
        model.add(link);
        return model;
    }

    @PostMapping("/jpa/employees")
    public ResponseEntity<Object> saveEmployee(@Valid @RequestBody Employee emp){
        Employee newEmployee = employeeRepository.save(emp);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("{employeeId}")
                .buildAndExpand(newEmployee.getId())
                .toUri();
        return ResponseEntity.created(uri).build();
    }

    @PostMapping("/jpa/adddepartment/{empId}")
    public ResponseEntity<Object> saveEmployee(@PathVariable Long empId, @RequestBody Department department){
        Employee employee = employeeRepository.findById(empId).get();
        if(null == employee)
            throw new EmployeeNotFound("Employee Not Found .");

        department.setEmployee(employee);
        departmentRepository.save(department);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("{empId}")
                .buildAndExpand(employee.getId())
                .toUri();
        return ResponseEntity.created(uri).build();
    }

    @DeleteMapping("jpa/employee/delete/{empId}")
    public void deleteEmployee(@PathVariable Long empId){
        Employee emp = employeeRepository.findById(empId).get();
        if(null == emp)
            throw new EmployeeNotFound("Employee Not Found .");
        employeeRepository.delete(emp);
    }


}
