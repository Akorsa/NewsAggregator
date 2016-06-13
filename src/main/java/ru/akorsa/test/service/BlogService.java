package ru.akorsa.test.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.access.method.P;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import ru.akorsa.test.entity.Blog;
import ru.akorsa.test.entity.Item;
import ru.akorsa.test.entity.User;
import ru.akorsa.test.exception.RssException;
import ru.akorsa.test.repository.BlogRepository;
import ru.akorsa.test.repository.ItemRepository;
import ru.akorsa.test.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class BlogService {

    @Autowired
    private BlogRepository blogRepository;

    @Autowired
    private RssService rssService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    @Qualifier("infoq")
    private ParserService InfoqParserServiceImpl;

    @Autowired
    @Qualifier("jcg")
    private ParserService JCGParserServiceImpl;

    // 1 hour = 60 seconds * 60 minutes * 1000
    @Scheduled(fixedDelay = 3600000)
    public void reloadBlogs() {
        List<Blog> blogs = blogRepository.findAll();
        for (Blog blog : blogs) {
            saveItems(blog);
        }
    }

    public void save(Blog blog, String name) throws RssException {
        User user = userRepository.findByName(name);
        blog.setUser(user);
        blogRepository.save(blog);
        if (blog.getIsRss().equals("yes")) {
            saveItems(blog);
        } else {
            saveItemsForBlog(blog);
        }

    }

    private void saveItemsForBlog(Blog blog) throws RssException {
        try {
            List<Item> items = new ArrayList<Item>();
            if (blog.getUrl().equals("https://www.infoq.com/java/news/")) {
                items = InfoqParserServiceImpl.getItems(blog.getUrl());
            } else {
                if (blog.getUrl().equals("https://www.javacodegeeks.com/")) {
                    items = JCGParserServiceImpl.getItems(blog.getUrl());
                } else {
                    throw new Exception();
                }

            }
            for (Item item : items) {
                Item savedItem = itemRepository.findByBlogAndLink(blog, item.getLink());
                if (savedItem == null) {
                    item.setBlog(blog);
                    itemRepository.save(item);
                }
            }
        } catch (RssException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RssException(e);
        }
    }

    private void saveItems(Blog blog) {
        try {
            List<Item> items = rssService.getItems(blog.getUrlRss());
            for (Item item : items) {
                Item savedItem = itemRepository.findByBlogAndLink(blog, item.getLink());
                if (savedItem == null) {
                    item.setBlog(blog);
                    itemRepository.save(item);
                }
            }
        } catch (RssException e) {
            e.printStackTrace();
        }
    }

    @PreAuthorize("#blog.user.name == authentication.name or hasRole('ROLE_ADMIN')")
    public void delete(@P("blog") Blog blog) {
        blogRepository.delete(blog);
    }

    public Blog findOne(int id) {
        return blogRepository.findOne(id);
    }
}
