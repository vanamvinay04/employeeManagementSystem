package com.vinay.employeeManagementSystem.employeeServices;

import com.vinay.employeeManagementSystem.model.UserPrincipals;
import com.vinay.employeeManagementSystem.model.Users;
import com.vinay.employeeManagementSystem.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {
    @Autowired
    private UsersRepository repo;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        Users user = repo.findByUserName(userName);
        if(user == null){
            System.out.println("User not found");
            throw new UsernameNotFoundException("User not Found");
        }

        return new UserPrincipals(user);
    }
}
