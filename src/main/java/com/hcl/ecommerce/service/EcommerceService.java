package com.hcl.ecommerce.service;

import java.util.List;

import com.hcl.ecommerce.dto.CategoryDto;
import com.hcl.ecommerce.dto.CommonResponse;
import com.hcl.ecommerce.dto.LoginRequestDto;
import com.hcl.ecommerce.dto.UserDto;

public interface EcommerceService {
	
	CommonResponse createUser(UserDto userDto);
	List<CategoryDto> loginUser(LoginRequestDto loginRequestDto);

}
