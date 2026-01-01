package com.vinay.employeeManagementSystem.repositories;

import com.vinay.employeeManagementSystem.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepository extends JpaRepository<Users,Integer> {

    public Users findByUserName(String userName);

}
