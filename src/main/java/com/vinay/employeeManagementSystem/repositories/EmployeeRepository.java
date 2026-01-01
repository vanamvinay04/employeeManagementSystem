package com.vinay.employeeManagementSystem.repositories;

import com.vinay.employeeManagementSystem.model.Employee;
import com.vinay.employeeManagementSystem.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee,Integer> {
    List<Employee> findByDepartment(String department);
}
