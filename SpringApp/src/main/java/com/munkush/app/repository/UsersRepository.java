package com.munkush.app.repository;

import com.munkush.app.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepository extends JpaRepository<Users,Integer> {
    Users findByUsername(String username);

    Users findByActivationCode(String code);
}
