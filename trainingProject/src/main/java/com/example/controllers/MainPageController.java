package com.example.controllers;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.entities.Product;
import com.example.entities.Type;
import com.example.services.ProductService;
import com.example.services.TypeService;

@Controller
@RequestMapping("/fabric")
public class MainPageController {

	private final TypeService typeService;
	private final ProductService productService;

	public MainPageController(TypeService typeService, ProductService productService) {
		this.typeService = typeService;
		this.productService = productService;
	}

	@GetMapping("")
	public String mainPage(Model model) {
		return "user/mainPage";
	}

	@GetMapping({ "/menu", "/menu/" })
	public String menu(Model model) {
		Map<Type, List<Product>> productMap = new LinkedHashMap<>();
		typeService.allList().stream()
				.forEach(e -> productMap.put(e, productService.getProductsByCategory(e.getCategory()).stream()
						.sorted(Comparator.comparing(product -> product.getId())).collect(Collectors.toList())));
		model.addAttribute("productMap", productMap);
		return "user/menu";
	}

}
