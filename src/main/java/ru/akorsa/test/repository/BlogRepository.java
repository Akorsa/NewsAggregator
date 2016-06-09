package ru.akorsa.test.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.akorsa.test.entity.Blog;
import ru.akorsa.test.entity.User;

import java.util.List;

public interface BlogRepository extends JpaRepository<Blog, Integer> {

    List<Blog> findByUser(User user);

}
