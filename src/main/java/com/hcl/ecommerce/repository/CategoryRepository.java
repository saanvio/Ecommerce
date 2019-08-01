package com.hcl.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hcl.ecommerce.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Long>{

}
