package com.hcl.ecommerce.service;

import java.util.List;

import com.hcl.ecommerce.dto.ProductAnalyticsDto;
import com.hcl.ecommerce.dto.ProductDto;

public interface ProductService {
	ProductDto getProductById(Long userId, Long categoryId, Long poductId);

	List<ProductAnalyticsDto> getProductAnalytics();

	ProductAnalyticsDto getProductAnalyticsById(Long productId);
}
