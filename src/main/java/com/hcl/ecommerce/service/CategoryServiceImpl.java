package com.hcl.ecommerce.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hcl.ecommerce.dto.CategoryAnalyticsDto;
import com.hcl.ecommerce.dto.ProductDto;
import com.hcl.ecommerce.entity.Category;
import com.hcl.ecommerce.entity.CategoryAnalytics;
import com.hcl.ecommerce.entity.Product;
import com.hcl.ecommerce.entity.User;
import com.hcl.ecommerce.exception.EcommerceException;
import com.hcl.ecommerce.exception.UserNotFoundException;
import com.hcl.ecommerce.repository.CategoryAnalyticsRepository;
import com.hcl.ecommerce.repository.CategoryRepository;
import com.hcl.ecommerce.repository.ProductAnalyticsRepository;
import com.hcl.ecommerce.repository.ProductRepository;
import com.hcl.ecommerce.repository.UserRepository;
import com.hcl.ecommerce.util.EcommerceConstants;

@Service
public class CategoryServiceImpl implements CategoryService {

	public static final Logger LOGGER = LoggerFactory.getLogger(CategoryServiceImpl.class);

	@Autowired
	UserRepository userRepository;

	@Autowired
	CategoryRepository categoryRepository;

	@Autowired
	ProductRepository productRepository;

	@Autowired
	CategoryAnalyticsRepository categoryAnalyticsReposiitory;

	@Autowired
	ProductAnalyticsRepository productAnalyticsReposiitory;

	/*
	 * @Override public List<CategoryDto> getCategories() {
	 * 
	 * LOGGER.info("get categories service impl"); List<Category> categoriesList =
	 * categoryRepository.findAll(); List<CategoryDto> categoriesDtoList = new
	 * ArrayList<>(); categoriesList.stream().forEach(p -> { CategoryDto categoryDto
	 * = new CategoryDto(); BeanUtils.copyProperties(p, categoryDto);
	 * categoriesDtoList.add(categoryDto); }); return categoriesDtoList; }
	 */

	@Override
	public List<ProductDto> getProductsByCatId(Long userId, Long categoryId) {
		LOGGER.info("get products by category id in service impl");
		Optional<User> userDb = userRepository.findById(userId);
		if (!userDb.isPresent())
			throw new UserNotFoundException(EcommerceConstants.ERROR_USER_NOT_FOUND_MESSAGE);

		Optional<Category> categoryDb = categoryRepository.findById(categoryId);
		if (!categoryDb.isPresent())
			throw new EcommerceException(EcommerceConstants.ERROR_CATEGORY_NOT_FOUND_MESSAGE);

		List<Product> productList = productRepository.getAllProducts(categoryId);
		List<ProductDto> productDtoList = new ArrayList<>();
		productList.stream().forEach(p -> {
			ProductDto productDto = new ProductDto();
			BeanUtils.copyProperties(p, productDto);
			productDtoList.add(productDto);
		});
		insertCategoriesCount(userId, categoryId);
		return productDtoList;
	}

	private void insertCategoriesCount(Long userId, Long categoryId) {

		LOGGER.info("insert categories count by clicking on category count");

		Optional<CategoryAnalytics> categoryAnalytics = categoryAnalyticsReposiitory.findByUserIdAndCategoryId(userId,
				categoryId);
		int count = 1;
		Category category = new Category();
		category.setCategoryId(categoryId);

		User user = new User();
		user.setUserId(userId);
		if (categoryAnalytics.isPresent()) {
			count = categoryAnalytics.get().getCount();
			categoryAnalytics.get().setCategory(category);
			categoryAnalytics.get().setUser(user);
			categoryAnalytics.get().setCount(count +1);
			categoryAnalyticsReposiitory.save(categoryAnalytics.get());
		} else {
			CategoryAnalytics categoryAnalyticsDb = new CategoryAnalytics();
			categoryAnalyticsDb.setCategory(category);
			categoryAnalyticsDb.setUser(user);
			categoryAnalyticsDb.setCount(count);
			categoryAnalyticsReposiitory.save(categoryAnalyticsDb);
		}

	}

	@Override
	public List<CategoryAnalyticsDto> getCategoryAnalytics() {

		LOGGER.info("Categories analytics");

		List<?> categoryAnalyticsList = categoryAnalyticsReposiitory.getCategoryAnalytics();

		List<CategoryAnalyticsDto> result = new ArrayList<>();

		for (Object o : categoryAnalyticsList) {
			Object[] oo = (Object[]) o;
			CategoryAnalyticsDto obj = CategoryAnalyticsDto.builder().categoryId((Long) oo[0]).count((Long) oo[1])
					.build();
			result.add(obj);
		}

		return result;
	}

	@Override
	public CategoryAnalyticsDto getCategoryAnalyticsById(Long categoryId) {
		Optional<Category> categoryDb = categoryRepository.findById(categoryId);
		if (!categoryDb.isPresent())
			throw new EcommerceException(EcommerceConstants.ERROR_CATEGORY_NOT_FOUND_MESSAGE);
		return categoryAnalyticsReposiitory.getCategoryAnalyticsById(categoryId);
	}

}
