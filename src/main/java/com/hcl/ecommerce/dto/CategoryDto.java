package com.hcl.ecommerce.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDto implements Serializable{/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long categoryId;
	private String categoryName;

}
