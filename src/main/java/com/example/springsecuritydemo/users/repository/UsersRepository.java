package com.example.springsecuritydemo.users.repository;

import com.example.springsecuritydemo.users.domain.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<Users,Long> {
}
