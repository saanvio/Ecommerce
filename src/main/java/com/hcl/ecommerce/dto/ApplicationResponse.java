package com.hcl.ecommerce.dto;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationResponse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer statusCode;
	private String message;
	
	@JsonInclude(Include.NON_NULL)
	private List<CategoryDto> categoriesList;
	
	@JsonInclude(Include.NON_NULL)
	private List<ProductDto> productLists;
	
	@JsonInclude(Include.NON_NULL)
	private List<CategoryAnalyticsDto> categoriesAnalyticsLists;
	

}
