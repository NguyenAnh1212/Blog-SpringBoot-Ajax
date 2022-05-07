package com.an.spring_boot_ajax.repo;


import com.an.spring_boot_ajax.model.Blog;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlogRepo extends PagingAndSortingRepository<Blog, Long> {

}
