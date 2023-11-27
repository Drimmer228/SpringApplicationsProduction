package com.example.springmodels.models;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name="book_detail")
public class BookDetailModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @OneToOne(mappedBy = "bookDetail")
    private BookModel book;
    @NotBlank(message = "Type is required")
    private String type;

    @NotBlank(message = "Additional info is required")
    private String additionalInfo;

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public BookModel getBook() {
        return book;
    }

    public void setBook(BookModel book) {
        this.book = book;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
