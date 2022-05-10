package com.an.spring_boot_ajax.controller;

import com.an.spring_boot_ajax.model.Blog;
import com.an.spring_boot_ajax.model.BlogForm;
import com.an.spring_boot_ajax.model.Category;
import com.an.spring_boot_ajax.service.blog.IBlogService;
import com.an.spring_boot_ajax.service.category.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;


@CrossOrigin("*")
@RestController
@RequestMapping("/blog")
public class BlogController {
    @Autowired
    private IBlogService blogService;
    @Autowired
    private CategoryService categoryService;
    @ModelAttribute("categories")
    private Iterable<Category> listCategories(){
        return categoryService.findAll();
    }
    @Autowired
    Environment env;

    @GetMapping("/list")
    public ModelAndView showList(){
        ModelAndView modelAndView = new ModelAndView("blog/list");
        modelAndView.addObject("blogs", blogService.findAll());
        modelAndView.addObject("categories",categoryService.findAll());
        return modelAndView;
    }

    @GetMapping("/category")
    public ResponseEntity<Iterable<Category>> findAllCategory(){
        Iterable<Category> categories = categoryService.findAll();
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Iterable<Blog>> findAllBlog() {
        List<Blog> blogs = (List<Blog>) blogService.findAll();
        if (blogs.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(blogs, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Blog> findBlogById(@PathVariable Long id) {
        Optional<Blog> blogOptional = blogService.findById(id);
        if (!blogOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(blogOptional.get(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Blog> saveBlog(@ModelAttribute BlogForm blogForm) {
        MultipartFile multipartFile = blogForm.getImage();
        String fileName = multipartFile.getOriginalFilename();
        String fileUpload = env.getProperty("upload.path");
        try {
            FileCopyUtils.copy(multipartFile.getBytes(),new File(fileUpload+fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Blog blog = new Blog(blogForm.getTitle(), blogForm.getContent(),fileName, blogForm.getCategory());
        blogService.save(blog);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);

//        return new ResponseEntity<>(blogService.save(blogForm), HttpStatus.CREATED);
    }

    @PostMapping("/{id}")
    public ResponseEntity<Blog> updateBlog(@PathVariable Long id, @ModelAttribute BlogForm blogForm) {

        Optional<Blog> blogOptional = blogService.findById(id);
        blogForm.setId(blogOptional.get().getId());
        MultipartFile multipartFile = blogForm.getImage();
        String fileName = multipartFile.getOriginalFilename();
        String fileUpload = env.getProperty("upload.path");
        Blog existBlog = new Blog(id, blogForm.getTitle(), blogForm.getContent(),fileName, blogForm.getCategory());
        try {
            FileCopyUtils.copy(multipartFile.getBytes(), new File(fileUpload+fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (existBlog.getImage().equals("filename.jpg")){
            existBlog.setImage(blogOptional.get().getImage());
        }
        blogService.save(existBlog);
        return new ResponseEntity<>(existBlog, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Blog> deleteBlog(@PathVariable Long id) {
        Optional<Blog> blogOptional = blogService.findById(id);
        if (!blogOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        blogService.remove(id);
        return new ResponseEntity<>(blogOptional.get(), HttpStatus.NO_CONTENT);
    }
}
