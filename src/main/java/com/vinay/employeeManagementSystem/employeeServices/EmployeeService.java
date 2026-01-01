package com.vinay.employeeManagementSystem.employeeServices;

import com.vinay.employeeManagementSystem.model.Employee;
import com.vinay.employeeManagementSystem.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;
@Service
public class EmployeeService
    {

        @Autowired
        EmployeeRepository repo;

        public List<Employee> getEmployees() {
            return repo.findAll();
        }

        public Employee getEmployeeById(Integer id) {
            return repo.findById(id).orElse(null);
        }


        public List<Employee> findEmpByDepartment(Employee employee,String department){
           List<Employee> employees = repo.findByDepartment(department);
           if(employees.isEmpty()){
               System.out.println("No employees found with Department: "+department);
               return new ArrayList<>();
           }
           else {
               return employees;
           }
        }

        public List<Employee> postEmployees(List<Employee> emps){
            return repo.saveAll(emps);
        }

        public Employee updateEmployeeById(Integer id,Employee employee) {
            Employee updateEmp = repo.findById(id).orElse(null);
            if(updateEmp != null){
                updateEmp.setName(employee.getName());
                updateEmp.setEmail(employee.getEmail());
                updateEmp.setDepartment(employee.getDepartment());
                updateEmp.setSalary(employee.getSalary());
                updateEmp.setJoiningDate(employee.getJoiningDate());

                return repo.save(updateEmp);
            }
            else{
                return null ;
            }
        }

        public boolean updateSalaryById(Integer id, Employee employee) {
            Employee emp = repo.findById(id).orElse(null);
            //boolean EmployeeIsPresent = repo.existsById(id);
            if (emp != null){
                emp.setSalary(employee.getSalary());
                repo.save(emp);
                return true;
            }
            else {
                return false;
            }
        }

        public boolean deleteEmployeeByID(Integer id) {
            if (repo.existsById(id)) {
                repo.deleteById(id);
                return true;
            }
            else
                return false;
        }

        public boolean deleteAllEmployees() {
            boolean emp = repo.findAll().isEmpty();
            if(!emp){
                repo.deleteAll();
                return true;
            }
            else
                return false;

        }
    }
