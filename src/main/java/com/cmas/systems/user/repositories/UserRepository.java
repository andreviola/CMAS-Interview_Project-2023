package com.cmas.systems.user.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cmas.systems.user.entities.User;

/**
 * Interface extending JpaRepository
 */
@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    
}
