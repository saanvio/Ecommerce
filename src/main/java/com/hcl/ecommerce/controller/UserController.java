package com.hcl.ecommerce.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hcl.ecommerce.dto.CommonResponse;
import com.hcl.ecommerce.dto.LoginRequestDto;
import com.hcl.ecommerce.dto.ProductAnalyticsDto;
import com.hcl.ecommerce.dto.ProductDto;
import com.hcl.ecommerce.dto.UserDto;
import com.hcl.ecommerce.service.EcommerceService;
import com.hcl.ecommerce.service.ProductService;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	EcommerceService ecommerceService;

	@Autowired
	ProductService productService;

	@PostMapping("/add")
	public ResponseEntity<CommonResponse> createUser(@RequestBody UserDto userDto) {
		return new ResponseEntity<>(ecommerceService.createUser(userDto), HttpStatus.CREATED);
	}

	@PostMapping("/login")
	public ResponseEntity<CommonResponse> loginUser(@RequestBody LoginRequestDto loginRequestDto) {
		return new ResponseEntity<>(ecommerceService.loginUser(loginRequestDto), HttpStatus.OK);
	}

	@GetMapping("/{uid}/category/{catId}/product/{productId}")
	public ResponseEntity<ProductDto> getProductsByCatId(@PathVariable Long uid, @PathVariable Long catId,
			@PathVariable Long productId) {
		return new ResponseEntity<>(productService.getProductById(uid, catId, productId), HttpStatus.OK);
	}

	@GetMapping("/product/analytics")
	public ResponseEntity<List<ProductAnalyticsDto>> getProductsAnalytics() {
		return new ResponseEntity<>(productService.getProductAnalytics(), HttpStatus.OK);
	}

	@GetMapping("/product/analytics/{id}")
	public ResponseEntity<ProductAnalyticsDto> getProductsAnalyticsById(@PathVariable Long id) {
		return new ResponseEntity<>(productService.getProductAnalyticsById(id), HttpStatus.OK);
	}

}
