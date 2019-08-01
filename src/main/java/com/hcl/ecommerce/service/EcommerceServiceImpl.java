package com.hcl.ecommerce.service;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.hcl.ecommerce.dto.ApplicationResponse;
import com.hcl.ecommerce.dto.LoginRequestDto;
import com.hcl.ecommerce.dto.UserDto;
import com.hcl.ecommerce.entity.User;
import com.hcl.ecommerce.exception.EcommerceException;
import com.hcl.ecommerce.exception.UserNotFoundException;
import com.hcl.ecommerce.repository.UserRepository;
import com.hcl.ecommerce.util.EcommerceConstants;

@Service
public class EcommerceServiceImpl implements EcommerceService {
	public static final Logger LOGGER = LoggerFactory.getLogger(EcommerceServiceImpl.class);

	@Autowired
	UserRepository userRepostory;
	

	

	@Override
	public ApplicationResponse createUser(UserDto userDto) {

		LOGGER.info("Create user impl");
		if (!emailvalidation(userDto.getEmail()))
			throw new EcommerceException(EcommerceConstants.ERROR_EMAIL_MESSAGE);
		if (!phoneNumberValidatoin(userDto.getPhoneNumber()))
			throw new EcommerceException(EcommerceConstants.ERROR_PHONE_NUMBER_MESSAGE);
		if (!userDto.getPassword().equals(userDto.getConfirmPassword()))
			throw new EcommerceException(EcommerceConstants.ERROR_PASSWORD_MISMATCH);
		Optional<User> employeeExist = userRepostory.findByEmail(userDto.getEmail());
		if (employeeExist.isPresent())
			throw new UserNotFoundException(EcommerceConstants.ERROR_EMPLOYEE_ALREADY_EXIST);
		User user=new User();
		BeanUtils.copyProperties(userDto, user,"confirmPassword");

		userRepostory.save(user);
		return new ApplicationResponse(HttpStatus.CREATED.value(), EcommerceConstants.CREATED_MESSAGE,null,null);
	}


	@Override
	public ApplicationResponse loginUser(LoginRequestDto loginRequestDto) {
		
		Optional<User> user=userRepostory.findByUserNameAndPassword(loginRequestDto.getUserName(), loginRequestDto.getPassword());
		if(!user.isPresent())
			throw new EcommerceException(EcommerceConstants.ERROR_INVALID_CREDENTIALS);
		
		return new ApplicationResponse(HttpStatus.OK.value(), EcommerceConstants.LOGIN_MESSAGE,null,null);
	}
	

	private boolean phoneNumberValidatoin(Long number) {

		String num = number.toString();
		Pattern p = Pattern.compile("^[0-9]{10}$");
		Matcher m = p.matcher(num);
		return (m.find() && m.group().equals(num));
	}

	private boolean emailvalidation(String email) {
		String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." + "[a-zA-Z0-9_+&*-]+)*@" + "(?:[a-zA-Z0-9-]+\\.)+[a-z"
				+ "A-Z]{2,7}$";
		Pattern pat = Pattern.compile(emailRegex);
		if (email == null)
			return false;
		return pat.matcher(email).matches();
	}


}
