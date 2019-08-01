package com.hcl.ecommerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hcl.ecommerce.dto.ApplicationResponse;
import com.hcl.ecommerce.dto.LoginRequestDto;
import com.hcl.ecommerce.dto.UserDto;
import com.hcl.ecommerce.service.EcommerceService;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	EcommerceService ecommerceService;

	@PostMapping("/add")
	public ResponseEntity<ApplicationResponse> createUser(@RequestBody UserDto userDto) {
		return new ResponseEntity<>(ecommerceService.createUser(userDto), HttpStatus.CREATED);
	}
	@PostMapping("/login")
	public ResponseEntity<ApplicationResponse> loginUser(@RequestBody LoginRequestDto loginRequestDto) {
		return new ResponseEntity<>(ecommerceService.loginUser(loginRequestDto), HttpStatus.OK);
	}

}
