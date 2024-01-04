package com.jar_assignment.kirana_transactions.repository;

import com.jar_assignment.kirana_transactions.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepo extends JpaRepository<User, Integer> {
    Optional<User> findByUsername(String username);
}
