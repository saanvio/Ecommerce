package com.hcl.ecommerce.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.hcl.ecommerce.dto.CategoryDto;
import com.hcl.ecommerce.dto.CommonResponse;
import com.hcl.ecommerce.dto.LoginRequestDto;
import com.hcl.ecommerce.dto.UserDto;
import com.hcl.ecommerce.entity.Category;
import com.hcl.ecommerce.entity.Product;
import com.hcl.ecommerce.entity.User;
import com.hcl.ecommerce.exception.EcommerceException;
import com.hcl.ecommerce.exception.UserNotFoundException;
import com.hcl.ecommerce.repository.CategoryRepository;
import com.hcl.ecommerce.repository.UserRepository;
import com.hcl.ecommerce.service.EcommerceServiceImpl;
import com.hcl.ecommerce.util.EcommerceConstants;

@RunWith(MockitoJUnitRunner.class)
public class EcommerceServiceImplTests {

	@Mock
	UserRepository userRepository;

	@Mock
	CategoryRepository categoryRepository;

	@InjectMocks
	EcommerceServiceImpl ecommerceServiceImpl;

	User user;
	UserDto userDto;
	Category category;
	CategoryDto categoryDto;
	Product product;
	LoginRequestDto loginRequestDto;

	@Before
	public void setUp() {
		user = getUser();
		userDto = getUserDto();
		category = getCategories();
		categoryDto = getCategoryDto();
		product = getProducts();
		loginRequestDto = getLoginRequestDto();
	}

	@Test
	public void createUserImpl_1Test() {

		// Mockito.when(userRepository.save(user)).thenReturn(user);

		CommonResponse actual = ecommerceServiceImpl.createUser(userDto);
		Assert.assertEquals(EcommerceConstants.CREATED_MESSAGE, actual.getMessage());

	}

	@Test(expected = EcommerceException.class)
	public void createUserImpl_2Test() {

		userDto.setEmail("jkjcom");
		ecommerceServiceImpl.emailvalidation(userDto.getEmail());
		ecommerceServiceImpl.createUser(userDto);

	}

	@Test(expected = EcommerceException.class)
	public void createUserImpl_3Test() {

		userDto.setPassword("124");
		userDto.setConfirmPassword("123");
		ecommerceServiceImpl.createUser(userDto);

	}

	@Test(expected = UserNotFoundException.class)
	public void createUserImpl_4Test() {

		userDto.setEmail("p@gmail.com");
		Mockito.when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));
		ecommerceServiceImpl.createUser(userDto);
	}

	@Test(expected = EcommerceException.class)
	public void createUserImpl_5Test() {

		userDto.setPhoneNumber(234235L);
		ecommerceServiceImpl.createUser(userDto);

	}

	@Test
	public void login_1Test() {

		List<Category> categoryList = new ArrayList<>();
		categoryList.add(category);

		List<CategoryDto> categoryDtoList = new ArrayList<>();
		categoryDtoList.add(categoryDto);

//		Mockito.when(userRepository.findByUserNameAndPassword(user.getUserName(), user.getPassword()))
//				.thenReturn(Optional.of(user));
		Mockito.when(categoryRepository.findAll()).thenReturn(categoryList);
		List<CategoryDto> categoryDtosList = ecommerceServiceImpl.getCategories();
		Assert.assertEquals(categoryDtoList.size(), categoryDtosList.size());
	}

	@Test(expected = EcommerceException.class)
	public void login_2Test() {

//		Mockito.when(userRepository.findByUserNameAndPassword(user.getUserName(), user.getPassword()))
//				.thenReturn(Optional.of(user));
		loginRequestDto.setUserName("hari");
		ecommerceServiceImpl.loginUser(loginRequestDto);

	}

	public User getUser() {
		return new User(1L, "Priya", "1234", "p@gmail.com", 2342342344L, "female");
	}

	public UserDto getUserDto() {
		return new UserDto("Priya", "1234", "1234", "h@gmail.com", 2342342344L, "female");
	}

	public Category getCategories() {
		Product product = getProducts();
		List<Product> productslist = new ArrayList<>();
		productslist.add(product);
		return new Category(1L, "Mobiles", productslist);
	}

	public CategoryDto getCategoryDto() {
		return new CategoryDto(1L, "Mobiles");
	}

	public Product getProducts() {
		// Category category = getCategories();
		return new Product(1L, "redmi", null);
	}

	public LoginRequestDto getLoginRequestDto() {
		return new LoginRequestDto("Priya", "1234");
	}

}
