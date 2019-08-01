package com.hcl.ecommerce.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.hcl.ecommerce.dto.CategoryAnalyticsDto;
import com.hcl.ecommerce.entity.CategoryAnalytics;

public interface CategoryAnalyticsRepository extends JpaRepository<CategoryAnalytics, Long> {

	@Query("select a from CategoryAnalytics a where a.user.userId=:userId and a.category.categoryId=:categoryId")
	Optional<CategoryAnalytics> findByUserIdAndCategoryId(@Param("userId") Long userId,
			@Param("categoryId") Long categoryId);

	@Query("select a.category.categoryId,sum(a.count) as count from CategoryAnalytics a group by a.category.categoryId")
	List<?> getCategoryAnalytics();

	@Query("select New com.hcl.ecommerce.dto.CategoryAnalyticsDto( a.category.categoryId,sum(a.count)) from CategoryAnalytics a where a.category.categoryId=:categoryId group by a.category.categoryId")
	CategoryAnalyticsDto getCategoryAnalyticsById(@Param("categoryId") Long categoryId);

}
