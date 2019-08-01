package com.hcl.ecommerce.service;

import com.hcl.ecommerce.dto.ApplicationResponse;

public interface CategoryService {
	ApplicationResponse getCategories();
	ApplicationResponse getProductsByCatId(Long userId,Long categoryId);
	ApplicationResponse getCategoryAnalytics();
}
