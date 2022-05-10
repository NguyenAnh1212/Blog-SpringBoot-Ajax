package com.an.spring_boot_ajax.model;

import org.springframework.web.multipart.MultipartFile;

public class BlogForm {
    private Long id;
    private String title;
    private String content;
    private MultipartFile image;
    private Category category;

    public BlogForm() {
    }

    public BlogForm(String title, String content, MultipartFile image, Category category) {
        this.title = title;
        this.content = content;
        this.image = image;
        this.category = category;
    }

    public BlogForm(Long id, String title, String content, MultipartFile image, Category category) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.image = image;
        this.category = category;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public MultipartFile getImage() {
        return image;
    }

    public void setImage(MultipartFile image) {
        this.image = image;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
