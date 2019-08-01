package com.hcl.ecommerce.service;

import com.hcl.ecommerce.dto.CommonResponse;
import com.hcl.ecommerce.dto.LoginRequestDto;
import com.hcl.ecommerce.dto.UserDto;

public interface EcommerceService {
	
	CommonResponse createUser(UserDto userDto);
	CommonResponse loginUser(LoginRequestDto loginRequestDto);
	

}
