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
public class UserDto implements Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = 1L;

	private String userName;
	private String password;
	private String confirmPassword;
	private String email;
	private Long phoneNumber;
	private String gender;

}
