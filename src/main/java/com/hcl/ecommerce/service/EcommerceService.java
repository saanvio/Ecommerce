package com.hcl.ecommerce.service;

import com.hcl.ecommerce.dto.ApplicationResponse;
import com.hcl.ecommerce.dto.LoginRequestDto;
import com.hcl.ecommerce.dto.UserDto;

public interface EcommerceService {
	
	ApplicationResponse createUser(UserDto userDto);
	ApplicationResponse loginUser(LoginRequestDto loginRequestDto);
	

}
