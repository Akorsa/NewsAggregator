package ru.akorsa.test.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.akorsa.test.entity.Blog;
import ru.akorsa.test.entity.Role;
import ru.akorsa.test.entity.User;
import ru.akorsa.test.repository.BlogRepository;
import ru.akorsa.test.repository.ItemRepository;
import ru.akorsa.test.repository.RoleRepository;
import ru.akorsa.test.repository.UserRepository;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class InitDbService {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private BlogRepository blogRepository;

    @PostConstruct
    public void init() {
        Role roleUser = new Role();
        roleUser.setName("ROLE_USER");
        roleRepository.save(roleUser);

        Role roleAdmin = new Role();
        roleAdmin.setName("ROLE_ADMIN");
        roleRepository.save(roleAdmin);

        User userAdmin = new User();
        userAdmin.setName("admin");
        userAdmin.setEnabled(true);
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        userAdmin.setPassword(encoder.encode("admin"));
        List<Role> roles = new ArrayList<Role>();
        roles.add(roleAdmin);
        roles.add(roleUser);
        userAdmin.setRoles(roles);
        userRepository.save(userAdmin);

        Blog blog = new Blog();
        blog.setName("DZone (Java)");
        blog.setUrl("http://feeds.dzone.com/java");
        blog.setUser(userAdmin);
        blogRepository.save(blog);

//        blog = new Blog();
//        blog.setName("Habr");
//        blog.setUrl("https://habrahabr.ru/rss/hub/java/");
//        blog.setUser(userAdmin);
//        blogRepository.save(blog);

    }
}
