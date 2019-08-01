package com.hcl.ecommerce.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.hcl.ecommerce.dto.ProductAnalyticsDto;
import com.hcl.ecommerce.entity.ProductAnalytics;

public interface ProductAnalyticsRepository extends JpaRepository<ProductAnalytics, Long> {

	@Query("select a from ProductAnalytics a where a.user.userId=:userId and a.category.categoryId=:categoryId and a.product.productId=:productId")
	Optional<ProductAnalytics> findByUserIdAndCategoryIdAndProductId(@Param("userId") Long userId,
			@Param("categoryId") Long categoryId, @Param("productId") Long productId);

	@Query("select a.product.productId,sum(a.count) as count from ProductAnalytics a group by a.product.productId")
	List<?> getProductsAnalytics();

	@Query("select New com.hcl.ecommerce.dto.ProductAnalyticsDto( a.product.productId,sum(a.count)) from ProductAnalytics a where a.product.productId=:productId group by a.product.productId")
	ProductAnalyticsDto getProductsAnalyticsById(@Param("productId") Long productId);

}
