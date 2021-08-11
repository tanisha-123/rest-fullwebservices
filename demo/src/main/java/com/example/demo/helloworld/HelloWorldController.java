package com.example.demo.helloworld;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

//Controller
@RestController
public class HelloWorldController {
	
	@Autowired
	private MessageSource messageSource;
	
	
	
	//GET
	//URI
	@RequestMapping(method= RequestMethod.GET, path = "/hello-world")
	public String helloworld() {
		return "hello world";
	}
	@GetMapping(path = "/hello-world-bean")
	public  HelloWorldBean helloWorldBean() {
		return new HelloWorldBean( "hello world");
	}
// hello-world/path-variable/in28minutes
	// to specify a specific path like in 28 minutes
	@GetMapping(path = "/hello-world/path-variable/{name}")
	public  HelloWorldBean helloWorldPathVariable(@PathVariable String name) {
		return new HelloWorldBean(String.format("hello world,%s",name));
	}
	
	@GetMapping(path = "/hello-world-internationalized")
	public String HelloWorldInternationalized(@RequestHeader(name="Accept-language",required=false)Locale locale) {
		return messageSource.getMessage("good.morning.message", null,locale);
	}
}
