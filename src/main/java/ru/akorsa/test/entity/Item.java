package ru.akorsa.test.entity;

import org.hibernate.annotations.Type;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldIndex;
import org.springframework.data.elasticsearch.annotations.FieldType;

import javax.persistence.*;
import java.util.Date;

@Entity
@Document(indexName = "item")
public class Item {

    @Id
    @GeneratedValue
    private Integer id;

    @Field(type = FieldType.String, index = FieldIndex.analyzed, store = true)
    @Column(length = 1000)
    private String title;

    @Lob
    @Type(type = "org.hibernate.type.StringClobType")
    @Column(length = Integer.MAX_VALUE)
    @Field(type = FieldType.String, index = FieldIndex.analyzed, store = true)
    private String description;

    @Column(name = "published_date")
    @Field(type = FieldType.Date, store = true)
    private Date publishedDate;

    @Column(length = 1000)
    @Field(type = FieldType.String, index = FieldIndex.analyzed, store = true)
    private String link;

    @ManyToOne
    @JoinColumn(name = "blog_id")
    @Field(type = FieldType.Nested, store = true)
    private Blog blog;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(Date publishedDate) {
        this.publishedDate = publishedDate;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Blog getBlog() {
        return blog;
    }

    public void setBlog(Blog blog) {
        this.blog = blog;
    }
}
