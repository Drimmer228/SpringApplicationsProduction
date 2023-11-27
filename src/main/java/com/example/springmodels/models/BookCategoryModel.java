package com.example.springmodels.models;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

import java.util.List;

@Entity
@Table(name="book_category")
public class BookCategoryModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @OneToMany(mappedBy = "category")
    private List<BookModel> books;
    @NotBlank(message = "Category is required")
    private String categoryName;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<BookModel> getBooks() {
        return books;
    }

    public void setBooks(List<BookModel> books) {
        this.books = books;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
