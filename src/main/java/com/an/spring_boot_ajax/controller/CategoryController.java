package com.an.spring_boot_ajax.controller;

import com.an.spring_boot_ajax.model.Category;
import com.an.spring_boot_ajax.service.category.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private ICategoryService categoryService;

    @GetMapping("/list")
    public ModelAndView showList(){
        ModelAndView modelAndView = new ModelAndView("category/list_category");
        modelAndView.addObject("categories", categoryService.findAll());
        return modelAndView;
    }


    @GetMapping
    public ResponseEntity<Iterable<Category>> findAllCategory(){
        Iterable<Category> categories = categoryService.findAll();
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Category> findCateById(@PathVariable Long id){
        Category categories = categoryService.findById(id).get();
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Category> saveCategory(@RequestBody Category category){
        return new ResponseEntity<>(categoryService.save(category), HttpStatus.CREATED);
    }

    //    @PutMapping("{/id}")
//    public ResponseEntity<Category> updateCategory(@PathVariable Long id, @RequestBody Category category){
//        Optional<Category> categoryOptional = categoryService.findById(id);
//        if(!categoryOptional.isPresent()){
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//        category.setId(categoryOptional.get().getId());
//        return new ResponseEntity<>(categoryService.save(category), HttpStatus.OK);
//    }
    @PutMapping("/{id}")
    public ResponseEntity<Category> updateCategory(@PathVariable Long id, @RequestBody Category category){
        category.setId(id);
        categoryService.save(category);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Category> deleteCategory(@PathVariable Long id ) {
        Category categories = categoryService.findById(id).get();
        if (categories != null) categoryService.remove(id);
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }
}