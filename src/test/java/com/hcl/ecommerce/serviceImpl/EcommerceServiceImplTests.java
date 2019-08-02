package com.hcl.ecommerce.serviceImpl;

import java.util.Optional;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.hcl.ecommerce.dto.ApplicationResponse;
import com.hcl.ecommerce.dto.CommonResponse;
import com.hcl.ecommerce.dto.UserDto;
import com.hcl.ecommerce.entity.User;
import com.hcl.ecommerce.repository.UserRepository;
import com.hcl.ecommerce.service.EcommerceServiceImpl;
import com.hcl.ecommerce.util.EcommerceConstants;

@RunWith(MockitoJUnitRunner.class)
public class EcommerceServiceImplTests {

	@Mock
	UserRepository userRepository;

	@InjectMocks
	EcommerceServiceImpl ecommerceServiceImpl;

	User user;
	UserDto userDto;

	@Before
	public void setUp() {
		user = getUser();
		userDto = getUserDto();
	}

	@Test
	public void createUserImpl_1Test() {

		Mockito.when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));
		Mockito.when(userRepository.save(user)).thenReturn(user);

		CommonResponse actual = ecommerceServiceImpl.createUser(userDto);
		Assert.assertEquals(EcommerceConstants.CREATED_MESSAGE, actual.getMessage());

	}
	
	@Test
	public void createUserImpl_2Test() {

		user.setEmail("jkjcom");
		Mockito.when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));

		ecommerceServiceImpl.createUser(userDto);

	}

	public User getUser() {
		return new User(1L, "Priya", "1234", "p@gmail.com", 2342342344L, "female");
	}

	public UserDto getUserDto() {
		return new UserDto("Priya", "1234", "1234", "h@gmail.com", 2342342344L, "female");
	}

}
