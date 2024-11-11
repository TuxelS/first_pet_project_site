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

import com.example.models.ProductModel;
import com.example.models.TypeOfModel;
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
		model.addAttribute("ListOfType",userService.allList());
		return "user/mainPage";
	}
	
	@GetMapping({"/menu","/menu/"})
	public String menu(Model model)
	{
		Map<TypeOfModel, List<ProductModel>> productMap = new LinkedHashMap<>();
		userService.allList().stream()
							 .forEach(e->productMap.put(e, userService.insideCategory(e.getCategory()).stream()
									 																  .sorted(Comparator.comparing(ProductModel::getId))
									 																  .collect(Collectors.toList())));
		model.addAttribute("productMap",productMap);
		return "user/menu";
	}
	
	@GetMapping("/{category}")
	public String menuOfCategory(@PathVariable("category") String category, Model model)
	{
		model.addAttribute("ListOfDefiniteCategory",userService.insideCategory(category));
		return "user/menuOfCategory";
	}
}
