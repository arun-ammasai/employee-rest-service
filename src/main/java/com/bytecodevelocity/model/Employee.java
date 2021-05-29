package com.bytecodevelocity.model;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Size(min=3,max = 5,message = "Invalid chars for Name")
    private String name;
    @Email
    private String email;

    @OneToMany(mappedBy = "employee", cascade = CascadeType.REMOVE)
    private List<Department> allDepartments;

    public Employee() {
    }

    public Employee(Long id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Department> getAllDepartments() {
        return allDepartments;
    }

    public void setAllDepartments(List<Department> allDepartments) {
        this.allDepartments = allDepartments;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
