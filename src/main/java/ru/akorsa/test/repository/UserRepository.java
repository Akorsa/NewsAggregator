package ru.akorsa.test.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.akorsa.test.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {

    User findByName(String name);
}
