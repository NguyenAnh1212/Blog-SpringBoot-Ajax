package com.an.spring_boot_ajax.repo;


import com.an.spring_boot_ajax.model.Category;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepo extends PagingAndSortingRepository<Category, Long> {
}
