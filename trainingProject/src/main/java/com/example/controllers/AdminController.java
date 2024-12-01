package com.example.controllers;

import java.io.IOException;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.entities.Product;
import com.example.entities.Type;
import com.example.services.ProductService;
import com.example.services.TypeService;

@Controller
@RequestMapping("/admin")
public class AdminController {

	private final TypeService typeService;
	private final ProductService productService;

	public AdminController(TypeService typeService, ProductService productService) {
		this.typeService = typeService;
		this.productService = productService;
	}

	@GetMapping("/{category}")
	public String menuOfCategory(@PathVariable("category") String category, Model model) {
		model.addAttribute("listOfDefiniteCategory", productService.getProductsByCategory(category));
		model.addAttribute("category", category);
		return "admin/adminMenuOfCategory";
	}

	@GetMapping("")
	public String adminMainPage(Model model) { // просто список категорий
		model.addAttribute("listOfType", typeService.allList());
		return "admin/adminMainPage";
	}

	// Изменения в TYPEOF
	@GetMapping("/TypeOf/createNewTypeOf")
	public String createNewTypeOf(Model model) {
		model.addAttribute("type", new Type());
		return "admin/TypeOf/createNewTypeOf";
	}

	@PostMapping("/TypeOf/create")
	public String createNewType(@ModelAttribute("type") Type type) {
		typeService.create(type);
		return "redirect:/admin";
	}

	@GetMapping("/TypeOf/editTypeOf")
	public String chooseTypeOf(Model model) {
		model.addAttribute("allList", typeService.allList());
		return "admin/TypeOf/listOfTypes";
	}

	@GetMapping("/TypeOf/editTypeOf/{id}")
	public String editType(@PathVariable("id") Integer id, Model model) {
		model.addAttribute("type", typeService.getTypeById(id));
		return "admin/TypeOf/editTypeOf";
	}

	@PatchMapping("/TypeOf/editType")
	public String editTypeOf(@ModelAttribute("type") Type type) {
		typeService.update(type);
		return "redirect:/admin";
	}

	@GetMapping("/TypeOf/deleteTypeOf")
	public String deleteTypeOf(Model model) {
		model.addAttribute("type", new Type());
		model.addAttribute("allList", typeService.allList()); // доработать удаление всех фоток в категории
		return "admin/TypeOf/deleteTypeOf";
	}

	@DeleteMapping("/TypeOf/delete")
	public String deleteTypeOf(@ModelAttribute("type") Type type) {
		typeService.delete(type);
		return "redirect:/admin";
	}

	// ИЗМЕНЕНИЯ В PRODUCT

	@GetMapping("/{category}/createNewProduct")
	public String createProduct(@PathVariable("category") String category, Model model) {
		model.addAttribute("category", category);
		model.addAttribute("product", new Product());

		return "admin/Product/createNewProduct";
	}

	@PostMapping("/{category}/create")
	public String createProduct(@ModelAttribute("product") Product product, @PathVariable("category") String category,
			@RequestParam("fileImage") MultipartFile multipartFile) {
		productService.create(product, category, multipartFile);
		return "redirect:/admin";
	}

	@GetMapping("/{category}/deleteProduct")
	public String deleteProduct(@PathVariable("category") String category, Model model) {
		model.addAttribute("listOfCategory", productService.getProductsByCategory(category));
		model.addAttribute("product", new Product());
		return "admin/Product/deleteProduct";
	}

	@DeleteMapping("/Product/delete")
	public String deleteProduct(@ModelAttribute("product") Product product) {
		productService.delete(product);
		return "redirect:/admin";
	}

	@GetMapping("/{category}/editProduct")
	public String chooseProductForEdit(@PathVariable("category") String category, Model model) {
		model.addAttribute("listOfCategory", productService.getProductsByCategory(category));
		return "admin/Product/listOfProducts";
	}

	@GetMapping("/{category}/editProduct/{id}")
	public String editProduct(@PathVariable("id") Integer id, Model model) {
		model.addAttribute("product", productService.getProductById(id));
		return "admin/Product/editProduct";
	}

	@PostMapping("/Product/editProduct")
	public String editProduct(@ModelAttribute("product") Product Product,
			@RequestParam("fileImage") MultipartFile multipartFile) throws IOException {
		productService.update(Product, multipartFile);
		return "redirect:/admin";
	}
}
