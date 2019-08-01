package com.hcl.ecommerce.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.hcl.ecommerce.dto.ApplicationResponse;
import com.hcl.ecommerce.dto.CategoryDto;
import com.hcl.ecommerce.dto.ProductDto;
import com.hcl.ecommerce.entity.Category;
import com.hcl.ecommerce.entity.CategoryAnalytics;
import com.hcl.ecommerce.entity.Product;
import com.hcl.ecommerce.entity.User;
import com.hcl.ecommerce.repository.CategoryAnalyticsRepository;
import com.hcl.ecommerce.repository.CategoryRepository;
import com.hcl.ecommerce.repository.ProductRepository;
import com.hcl.ecommerce.util.EcommerceConstants;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	CategoryRepository categoryRepository;

	@Autowired
	ProductRepository productRepository;

	@Autowired
	CategoryAnalyticsRepository categoryAnalyticsReposiitory;

	@Override
	public ApplicationResponse getCategories() {
		List<Category> categoriesList = categoryRepository.findAll();
		List<CategoryDto> categoriesDtoList = new ArrayList<>();
		categoriesList.stream().forEach(p -> {
			CategoryDto categoryDto = new CategoryDto();
			BeanUtils.copyProperties(p, categoryDto);
			categoriesDtoList.add(categoryDto);
		});
		return new ApplicationResponse(HttpStatus.OK.value(), EcommerceConstants.FETCHED_MESSAGE, categoriesDtoList,
				null);
	}

	@Override
	public ApplicationResponse getProductsByCatId(Long userId, Long categoryId) {
		List<Product> productList = productRepository.getAllProducts(categoryId);
		List<ProductDto> productDtoList = new ArrayList<>();
		productList.stream().forEach(p -> {
			ProductDto productDto = new ProductDto();
			BeanUtils.copyProperties(p, productDto);
			productDtoList.add(productDto);
		});
		insertCount(userId, categoryId);
		return new ApplicationResponse(HttpStatus.OK.value(), EcommerceConstants.FETCHED_MESSAGE, null, productDtoList);
	}

	private void insertCount(Long userId, Long categoryId) {
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
			categoryAnalytics.get().setCount(count + 1);
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
	public ApplicationResponse getCategoryAnalytics() {
		List<?> categoryAnalyticsList=categoryAnalyticsReposiitory.getCategoryAnalytics();
		
		return null;
	}

}
