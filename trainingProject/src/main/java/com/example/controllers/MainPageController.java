package com.example.controllers;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.entities.Product;
import com.example.entities.Type;
import com.example.services.UserService;

@Controller
@RequestMapping("/fabric")
public class MainPageController {
	
	private final UserService userService;
	
	public MainPageController(UserService userService)
	{
		this.userService=userService;
	}
	
	@GetMapping({"","/"})
	public String mainPage(Model model)
	{
		return "user/mainPage";
	}
	
	@GetMapping({"/menu","/menu/"})
	public String menu(Model model)
	{
		Map<Type, List<Product>> productMap = new LinkedHashMap<>();
		userService.allList().stream()
							 .forEach(e->productMap.put(e, userService.insideCategory(e.getCategory())
									 						.stream()
									 						.sorted(Comparator.comparing(product->product.getId()))
									 						.collect(Collectors.toList())
									 					));
		model.addAttribute("productMap",productMap);
		return "user/menu";
	}
	
}
