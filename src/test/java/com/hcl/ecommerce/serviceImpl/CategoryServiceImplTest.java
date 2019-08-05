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

import com.hcl.ecommerce.dto.CategoryAnalyticsDto;
import com.hcl.ecommerce.dto.CategoryDto;
import com.hcl.ecommerce.dto.ProductDto;
import com.hcl.ecommerce.dto.UserDto;
import com.hcl.ecommerce.entity.Category;
import com.hcl.ecommerce.entity.CategoryAnalytics;
import com.hcl.ecommerce.entity.Product;
import com.hcl.ecommerce.entity.User;
import com.hcl.ecommerce.exception.EcommerceException;
import com.hcl.ecommerce.exception.UserNotFoundException;
import com.hcl.ecommerce.repository.CategoryAnalyticsRepository;
import com.hcl.ecommerce.repository.CategoryRepository;
import com.hcl.ecommerce.repository.ProductRepository;
import com.hcl.ecommerce.repository.UserRepository;
import com.hcl.ecommerce.service.CategoryServiceImpl;

@RunWith(MockitoJUnitRunner.class)
public class CategoryServiceImplTest {

	@Mock
	CategoryRepository categoryRepository;

	@Mock
	UserRepository userRepository;

	@Mock
	ProductRepository productRepository;

	@Mock
	CategoryAnalyticsRepository categoryAnalyticsRepository;

	@InjectMocks
	CategoryServiceImpl categoryServiceImpl;

	User user;
	UserDto userDto;
	Category category;
	CategoryDto categoryDto;
	Product product;
	ProductDto productDto;
	CategoryAnalytics categoryAnalytics;
	CategoryAnalyticsDto categoryAnalyticsDto;

	@Before
	public void setUp() {
		user = getUser();
		userDto = getUserDto();
		category = getCategories();
		categoryDto = getCategoryDto();
		product = getProducts();
		productDto = getProductDto();
		categoryAnalytics = getCategoryAnalutics();
		categoryAnalyticsDto=getCategoryAnaluticsDto();
	}

	@Test
	public void getProductsByCatId_1Test() {
		List<ProductDto> productDtoList = new ArrayList<ProductDto>();
		productDtoList.add(productDto);

		List<Product> productList = new ArrayList<Product>();
		productList.add(product);

		Mockito.when(userRepository.findById(user.getUserId())).thenReturn(Optional.of(user));
		Mockito.when(categoryRepository.findById(category.getCategoryId())).thenReturn(Optional.of(category));

		Mockito.when(categoryAnalyticsRepository.findByUserIdAndCategoryId(user.getUserId(), category.getCategoryId()))
				.thenReturn(Optional.of(categoryAnalytics));
		// Mockito.when(categoryAnalyticsRepository.save(entity))

		Mockito.when(productRepository.getAllProducts(category.getCategoryId())).thenReturn(productList);

		List<ProductDto> productDtosList = categoryServiceImpl.getProductsByCatId(1L, 1L);
		Assert.assertEquals(productDtoList.size(), productDtosList.size());

	}
	
	@Test(expected = UserNotFoundException.class)
	public void getProductsByCatId_2Test(){
		categoryServiceImpl.getProductsByCatId(2L,category.getCategoryId());
		
	}
	
	@Test(expected = EcommerceException.class)
	public void getProductsByCatId_3Test(){
		Mockito.when(userRepository.findById(user.getUserId())).thenReturn(Optional.of(user));
		categoryServiceImpl.getProductsByCatId(user.getUserId(),2L);
		
	}
//	
//	@Test
//	public void getCategoryAnalyticsTest()
//	{
//		List<CategoryAnalyticsDto> categoriesAnalyticsFto=new ArrayList<CategoryAnalyticsDto>();
//		categoriesAnalyticsFto.add(categoryAnalyticsDto);
//		
//		List<CategoryAnalytics> categoriesAnalytics=new ArrayList<CategoryAnalytics>();
//		categoriesAnalytics.add(categoryAnalytics);
//		
//		Mockito.when(categoryAnalyticsRepository.getCategoryAnalytics()).thenReturn(List<?>);
//		
//	}
	

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
		return new Product(1L, "redmi", null);
	}

	public ProductDto getProductDto() {
		return new ProductDto(1L, "redmi");
	}

	public CategoryAnalytics getCategoryAnalutics() {
		return new CategoryAnalytics(1L, category, user, 0);
	}
	
	public CategoryAnalyticsDto getCategoryAnaluticsDto() {
		return new CategoryAnalyticsDto(1L, 0L);
	}

}
