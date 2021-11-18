package edu.egg.Library.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import org.hibernate.annotations.GenericGenerator;

@Entity
public class Book {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    private long isbn;
    private String title;
    private Integer pubDate;
    private Integer example;
    private Integer exampleBorrow;
    private Integer exampleRemain;
    private boolean register;
    @OneToOne
    private Author author;
    @OneToOne
    private Editorial editorial;

    public Book() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getIsbn() {
        return isbn;
    }

    public void setIsbn(long isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getPubDate() {
        return pubDate;
    }

    public void setPubDate(Integer pubDate) {
        this.pubDate = pubDate;
    }

    public Integer getExample() {
        return example;
    }

    public void setExample(Integer example) {
        this.example = example;
    }

    public Integer getExampleBorrow() {
        return exampleBorrow;
    }

    public void setExampleBorrow(Integer exampleBorrow) {
        this.exampleBorrow = exampleBorrow;
    }

    public Integer getExampleRemain() {
        return exampleRemain;
    }

    public void setExampleRemain(Integer exampleRemain) {
        this.exampleRemain = exampleRemain;
    }

    public boolean isRegister() {
        return register;
    }

    public void setRegister(boolean register) {
        this.register = register;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public Editorial getEditorial() {
        return editorial;
    }

    public void setEditorial(Editorial editorial) {
        this.editorial = editorial;
    }

}
