package com.tm.broadband.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.tm.broadband.model.TestUser;
import com.tm.broadband.model.User;

@Controller
public class TestController {

	public TestController() {
		// TODO Auto-generated constructor stub
	}
	
	@RequestMapping("/test/json")
	public String toTestJson() {
		return "test/test-json";
	}
	
	@RequestMapping(value = "/test/json",  method = RequestMethod.POST)
	@ResponseBody //不加这个注解，报404
	public TestUser doTestJson(@Valid TestUser user, BindingResult result) {
		
		if (result.hasErrors()) {
			
			System.out.println("error");
			List<ObjectError> errors = result.getAllErrors();
			for (ObjectError error: errors) {
				System.out.println(error.getCode() + ", " + error.getObjectName() + ", " + error.getDefaultMessage());
			}
			List<FieldError> fields = result.getFieldErrors();
			for (FieldError field: fields) {
				System.out.println(field.getCode() + ", " + field.getObjectName() + ", " + field.getDefaultMessage() + ", "  + field.getRejectedValue() + ", " + field.getField());
			}
			//user.setResult(result);
			//return user;
		}
 		
		System.out.println("id: " + user.getId());
		System.out.println("username: " + user.getUsername());
		System.out.println("price: " + user.getPrice());
		System.out.println("date: " + user.getDate());
		System.out.println("age: " + user.getAge());
		return user;
	}

}
