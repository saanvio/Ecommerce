package com.hcl.ecommerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hcl.ecommerce.dto.ApplicationResponse;
import com.hcl.ecommerce.service.CategoryService;

@RestController
@RequestMapping("/category")
public class CategoryController {

	@Autowired
	CategoryService categoryService;

	@GetMapping("/all")
	public ResponseEntity<ApplicationResponse> getCategories() {
		return new ResponseEntity<>(categoryService.getCategories(), HttpStatus.OK);
	}
	

	@GetMapping("/{userId}/{catId}")
	public ResponseEntity<ApplicationResponse> getProductsByCatId(@PathVariable Long userId,@PathVariable Long catId) {
		return new ResponseEntity<>(categoryService.getProductsByCatId(userId,catId), HttpStatus.OK);
	}
	
}
