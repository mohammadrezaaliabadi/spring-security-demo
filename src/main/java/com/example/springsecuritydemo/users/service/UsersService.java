package com.example.springsecuritydemo.users.service;

import com.example.springsecuritydemo.users.domain.Users;
import com.example.springsecuritydemo.users.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class UsersService implements UserDetailsService {

    private final UsersRepository usersRepository;

    @Autowired
    public UsersService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return usersRepository.findByEmail(username);
    }

    public Users findById(Long id) {
        return usersRepository.findById(id).get();
    }

    public List<Users> findAll(){
        return usersRepository.findAll();
    }

    @PreAuthorize("#users.email != authentication.name")
    public void deleteById(Users users){
        usersRepository.deleteById(users.getId());
    }

    @Transactional
    public Users registerUser(Users users) {
        return usersRepository.saveAndFlush(users);
    }

}