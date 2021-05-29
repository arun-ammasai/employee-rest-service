package com.bytecodevelocity.model;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class EmployeeDao {
    static List<Employee> list = new ArrayList<>();

    static {
        list.add(new Employee(1234l, "Nancy", "nancy@mail.com"));
        list.add(new Employee(1235l, "Daniel", "daniel@mail.com"));
        list.add(new Employee(1236l, "Scott", "scott@mail.com"));
    }

    public List<Employee> getAllEmployees() {
        return list;
    }

    public Employee getEmployeeById(int empId) {
        return list.stream()
                .filter(emp -> emp.getId() == empId)
                .findAny()
                .orElse(null);
    }

    public Employee saveEmployee(Employee emp) {
        emp.setId(list.size() + 1l);
        list.add(emp);
        return emp;
    }

    public Employee deleteEmployee(int empId) {
        Iterator<Employee> iterator = list.iterator();
        while (iterator.hasNext()) {
            Employee emp = iterator.next();
            if (empId == emp.getId()) {
                iterator.remove();
                return emp;
            }
        }
        return null;
    }
}
