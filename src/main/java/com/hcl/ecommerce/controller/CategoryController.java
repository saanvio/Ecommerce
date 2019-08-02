package com.hcl.ecommerce.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hcl.ecommerce.dto.CategoryAnalyticsDto;
import com.hcl.ecommerce.dto.ProductDto;
import com.hcl.ecommerce.service.CategoryService;

@RestController
@RequestMapping("/category")
public class CategoryController {

	@Autowired
	CategoryService categoryService;

//	@GetMapping("/all")
//	public ResponseEntity<List<CategoryDto>> getCategories() {
//		return new ResponseEntity<>(categoryService.getCategories(), HttpStatus.OK);
//	}

	@GetMapping("/{catId}/user/{userId}")
	public ResponseEntity<List<ProductDto>> getProductsByCatId(@PathVariable Long userId, @PathVariable Long catId) {
		return new ResponseEntity<>(categoryService.getProductsByCatId(userId, catId), HttpStatus.OK);
	}

	@GetMapping("/analytics")
	public ResponseEntity<List<CategoryAnalyticsDto>> getCategoriesAnalytics() {
		return new ResponseEntity<>(categoryService.getCategoryAnalytics(), HttpStatus.OK);
	}

	@GetMapping("/analytics/{id}")
	public ResponseEntity<CategoryAnalyticsDto> getCategoriesAnalyticsById(@PathVariable Long id) {
		return new ResponseEntity<>(categoryService.getCategoryAnalyticsById(id), HttpStatus.OK);
	}

}
