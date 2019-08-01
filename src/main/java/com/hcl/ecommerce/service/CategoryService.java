package com.hcl.ecommerce.service;

import java.util.List;

import com.hcl.ecommerce.dto.CategoryAnalyticsDto;
import com.hcl.ecommerce.dto.CategoryDto;
import com.hcl.ecommerce.dto.ProductDto;

public interface CategoryService {
	List<CategoryDto> getCategories();
	List<ProductDto> getProductsByCatId(Long userId,Long categoryId);
	List<CategoryAnalyticsDto> getCategoryAnalytics();
	CategoryAnalyticsDto getCategoryAnalyticsById(Long categoryId);
	
	
}
