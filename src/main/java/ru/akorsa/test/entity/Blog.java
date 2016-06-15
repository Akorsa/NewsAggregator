package ru.akorsa.test.entity;

import org.hibernate.validator.constraints.URL;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
public class Blog {

    @Id
    @GeneratedValue
    private Integer id;

    @URL(message = "Invalid URL!")
    private String urlRss;

    @URL(message = "Invalid URL!")
    private String url;

    @Size(min = 1, message = "Name must be at least 1 character!")
    private String name;

    private String isRss;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "blog", cascade = CascadeType.REMOVE)
    private List<Item> items;

    public Blog(String name) {
        this.name = name;
    }

    public Blog() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUrlRss() {
        return urlRss;
    }

    public void setUrlRss(String urlRss) {
        this.urlRss = urlRss;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public String getIsRss() {
        return isRss;
    }

    public void setIsRss(String isRss) {
        this.isRss = isRss;
    }
}
