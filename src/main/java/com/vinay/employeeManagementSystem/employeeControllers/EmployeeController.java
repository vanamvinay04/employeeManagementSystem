package com.vinay.employeeManagementSystem.employeeControllers;
import com.vinay.employeeManagementSystem.employeeServices.EmployeeService;
import com.vinay.employeeManagementSystem.model.Employee;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("employees")
public class EmployeeController
    {
        @Autowired
        EmployeeService service;

        @GetMapping("csrf-token")
        public CsrfToken getCsrfToken(HttpServletRequest request){
            return (CsrfToken) request.getAttribute("_csrf");

        }

        @GetMapping("")
        public ResponseEntity<List<Employee>> getEmployees(){
            List<Employee> employee = service.getEmployees();
            if(employee != null){
                return new ResponseEntity<>(employee, HttpStatus.OK);
            }
            else{
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }

        @GetMapping("/{id}")
        public ResponseEntity<Employee> getEmployeeById(@PathVariable Integer id){
            Employee employee = service.getEmployeeById(id);
            if (employee != null){
                return new ResponseEntity<Employee>(employee,HttpStatus.OK);
            }
            else{
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

        }

        @GetMapping("by-department")
        public ResponseEntity<?> findEmpByDepartment(Employee employee,
                                                                  @RequestParam String department){
            List<Employee> employees = service.findEmpByDepartment(employee,department);
            if (employees.isEmpty()) {
                return ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body("No employees found with department: " + department);
            }
            else
                return new ResponseEntity<>(employees,HttpStatus.OK);
        }

        @PostMapping("")
        public ResponseEntity<List<Employee>> postEmployees(@RequestBody List<Employee> emps){
            for (Employee s : emps ){
                s.setId(null);
            }
            List<Employee> saveEmployees = service.postEmployees(emps);

            if(saveEmployees != null){
                return new ResponseEntity<>(saveEmployees,HttpStatus.OK);
            }
            else{
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }

        @PutMapping("/{id}")
        public ResponseEntity<Employee> updateEmployeeById(@PathVariable Integer id,
                                                         @RequestBody Employee emp){
            Employee employee = service.updateEmployeeById(id,emp);
            if(employee != null){
                return new ResponseEntity<Employee>(employee,HttpStatus.OK);
            }
            else{
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

        }

        @DeleteMapping("")
        public ResponseEntity<String> deleteAllEmployees(){
            boolean isDeleted = service.deleteAllEmployees();
            if (isDeleted)
                return new ResponseEntity<>("All Employees Deleted.",HttpStatus.OK);
            else
                return new ResponseEntity<>("Bad request not deleted.",HttpStatus.BAD_REQUEST);
        }

        @DeleteMapping("/{id}")
        public ResponseEntity<String> deleteEmployeeByID(@PathVariable Integer id){
            boolean deleteEMp = service.deleteEmployeeByID(id);
            if (deleteEMp)
                return new ResponseEntity<>("Employee deleted.", HttpStatus.OK);
            else
                return new ResponseEntity<>("Employee not found.", HttpStatus.NOT_FOUND);

        }

        @PutMapping("/{id}/update-salary")
        public ResponseEntity<String> updateSalaryById(@PathVariable Integer id,
                                         @RequestBody Employee employee) {
            boolean emp = service.updateSalaryById(id,employee);
            if (emp)
                return new ResponseEntity<>("Salary updated for Employee with ID: " + id,HttpStatus.OK);
            else
                return new ResponseEntity<>("Employee not found with ID: "+ id ,HttpStatus.NOT_FOUND);
        }

    }
