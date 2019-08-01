package com.hcl.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hcl.ecommerce.entity.ProductAnalytics;

public interface ProductAnalyticsRepository extends JpaRepository<ProductAnalytics, Long> {

}
