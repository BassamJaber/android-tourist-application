package edu.birzeit.controllers;

import java.util.Arrays;
import java.util.Collection;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class HelloWorldController {
	@RequestMapping(value = "/testMyApp", method = RequestMethod.GET)
	public String test() {
		String result = "Awesome, it works :) ";
		return result;
	}

	@RequestMapping(value = "/testWithParams", method = RequestMethod.GET)
	public String handlePassedParams(
			@RequestParam(value = "name", defaultValue = "no-name") String name,
			@RequestParam(value = "age", defaultValue = "23") int id) {
		if (name == null) {
			name = "don't send me null parameters again!! ";
		}
		String result = " Hello, " + name + " your age is: " + id;
		return result;
	}
	
	@RequestMapping(value = "/sortParameter", method = RequestMethod.GET)
	public String handleIntegerParams(
			@RequestParam(value = "num1", defaultValue = "1") int num1,
			@RequestParam(value = "num2", defaultValue = "2") int num2,
			@RequestParam(value = "num3", defaultValue = "3") int num3) {
		int []array={num1,num2,num3};
		Arrays.sort(array);
		String result = "Parameters sorted : ";
		for(int i:array){
			result+=i+" ";
		}
		return result;
	}
}