package com.hcl.ecommerce.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.hcl.ecommerce.entity.CategoryAnalytics;

public interface CategoryAnalyticsRepository extends JpaRepository<CategoryAnalytics, Long>{
	
	@Query("select a from CategoryAnalytics a where a.user.userId=:userID and a.category.categoryId=:categoryId")
	Optional<CategoryAnalytics> findByUserIdAndCategoryId(Long userID,Long categoryId);
	
	@Query("select a.category.categoryId,sum(a.count) as count from CategoryAnalytics a group by a.category.categoryId")
	List<?> getCategoryAnalytics();

}
