package com.codersnextdoor.bringIt.api.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface UserRepository extends JpaRepository<User, Long> {
    Set<User> findByLastName(String lastName);

}
