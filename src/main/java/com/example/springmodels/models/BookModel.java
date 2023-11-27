package com.example.springmodels.models;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="books")
public class BookModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @NotBlank(message = "Title is required")
    private String title;
    @NotBlank(message = "Author is required")
    private String author;
    @NotNull(message = "Page count is required")
    private int pageCount;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "book_detail_id")
    private BookDetailModel bookDetail;
    @ManyToOne
    @JoinColumn(name = "book_category_id")
    private BookCategoryModel category;

    public BookModel() {}

    public BookModel(int id, String title, String author, int pageCount) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.pageCount = pageCount;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public BookCategoryModel getCategory() {
        return category;
    }

    public void setCategory(BookCategoryModel category) {
        this.category = category;
    }

    public BookDetailModel getBookDetail() {
        return bookDetail;
    }

    public void setBookDetail(BookDetailModel bookDetail) {
        this.bookDetail = bookDetail;
    }
}
