package com.hcl.ecommerce.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hcl.ecommerce.dto.ProductAnalyticsDto;
import com.hcl.ecommerce.dto.ProductDto;
import com.hcl.ecommerce.entity.Category;
import com.hcl.ecommerce.entity.Product;
import com.hcl.ecommerce.entity.ProductAnalytics;
import com.hcl.ecommerce.entity.User;
import com.hcl.ecommerce.exception.EcommerceException;
import com.hcl.ecommerce.exception.UserNotFoundException;
import com.hcl.ecommerce.repository.CategoryRepository;
import com.hcl.ecommerce.repository.ProductAnalyticsRepository;
import com.hcl.ecommerce.repository.ProductRepository;
import com.hcl.ecommerce.repository.UserRepository;
import com.hcl.ecommerce.util.EcommerceConstants;

@Service
public class ProductServiceImpl implements ProductService {
	public static final Logger LOGGER = LoggerFactory.getLogger(CategoryServiceImpl.class);

	@Autowired
	UserRepository userRepository;

	@Autowired
	CategoryRepository categoryRepository;

	@Autowired
	ProductRepository productRepository;

	@Autowired
	ProductAnalyticsRepository productAnalyticsReposiitory;

	@Override
	public ProductDto getProductById(Long userId, Long categoryId, Long productId) {
		LOGGER.info("get products by id in service");

		Optional<User> userDb = userRepository.findById(userId);
		if (!userDb.isPresent())
			throw new UserNotFoundException(EcommerceConstants.ERROR_USER_NOT_FOUND_MESSAGE);

		Optional<Category> categoryDb = categoryRepository.findById(categoryId);
		if (!categoryDb.isPresent())
			throw new EcommerceException(EcommerceConstants.ERROR_CATEGORY_NOT_FOUND_MESSAGE);

		Optional<Product> productDb = productRepository.findById(productId);
		if (!productDb.isPresent())
			throw new EcommerceException(EcommerceConstants.ERROR_PRODUCT_NOT_FOUND_MESSAGE);
		ProductDto productDto = new ProductDto();

		BeanUtils.copyProperties(productDb.get(), productDto);

		insertProductCount(userId, categoryId, productId);

		return productDto;
	}

	private void insertProductCount(Long userId, Long categoryId, Long productId) {
		Optional<ProductAnalytics> productAnalytics = productAnalyticsReposiitory
				.findByUserIdAndCategoryIdAndProductId(userId, categoryId, productId);

		int count = 1;
		User user = new User();
		user.setUserId(userId);

		Category category = new Category();
		category.setCategoryId(categoryId);

		Product product = new Product();
		product.setProductId(productId);

		if (productAnalytics.isPresent()) {
			productAnalytics.get().setUser(user);
			productAnalytics.get().setCategory(category);
			productAnalytics.get().setProduct(product);
			productAnalytics.get().setCount(count + 1);
			productAnalyticsReposiitory.save(productAnalytics.get());

		} else {

			ProductAnalytics productAnalyticsDb = new ProductAnalytics();
			productAnalyticsDb.setUser(user);
			productAnalyticsDb.setCategory(category);
			productAnalyticsDb.setProduct(product);
			productAnalyticsDb.setCount(count);
			productAnalyticsReposiitory.save(productAnalyticsDb);

		}
	}

	@Override
	public List<ProductAnalyticsDto> getProductAnalytics() {

		List<?> productAnalyticsList = productAnalyticsReposiitory.getProductsAnalytics();
		List<ProductAnalyticsDto> productAnalyticsDtoList = new ArrayList<>();
		for (Object o : productAnalyticsList) {
			Object[] oo = (Object[]) o;
			ProductAnalyticsDto productAnalyticsDto = ProductAnalyticsDto.builder().productId((Long) oo[0])
					.count((Long) oo[1]).build();
			productAnalyticsDtoList.add(productAnalyticsDto);

		}
		return productAnalyticsDtoList;
	}

	@Override
	public ProductAnalyticsDto getProductAnalyticsById(Long productId) {
		Optional<Product> productDb = productRepository.findById(productId);
		if (!productDb.isPresent())
			throw new EcommerceException(EcommerceConstants.ERROR_PRODUCT_NOT_FOUND_MESSAGE);
		return productAnalyticsReposiitory.getProductsAnalyticsById(productId);
	}

}
