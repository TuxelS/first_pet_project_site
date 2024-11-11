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

import com.example.models.ProductModel;
import com.example.models.TypeOfModel;
import com.example.services.UserService;

@Controller
@RequestMapping("/admin")
public class AdminController {
	
	private final UserService userService;
	
	public AdminController(UserService userService)
	{
		this.userService=userService;
	}
	
	@GetMapping("/{category}")
	public String menuOfCategory(@PathVariable("category") String category, Model model)
	{
		model.addAttribute("ListOfDefiniteCategory",userService.insideCategory(category));
		model.addAttribute("category",category);
		return "admin/adminMenuOfCategory";
	}
	
	@GetMapping({"","/"})
	public String adminMainPage(Model model)
	{
		model.addAttribute("ListOfType",userService.allList());
		return "admin/adminMainPage";
	}
	//Изменения в TYPEOF
	@GetMapping("/TypeOf/createNewTypeOf")
	public String createNewTypeOf(Model model)
	{
		model.addAttribute("TypeOfModel", new TypeOfModel());
		return "admin/TypeOf/createNewTypeOf";
	}
	
	@PostMapping("/TypeOf/create")
	public String createNewType(@ModelAttribute("TypeOfModel") TypeOfModel typeOfModel)
	{
		userService.saveNewTypeOf(typeOfModel);
		return "redirect:/admin";
	}
	
	@GetMapping("/TypeOf/editTypeOf")
	public String editTypeOf(Model model)
	{
		model.addAttribute("TypeOfModel", new TypeOfModel());
		model.addAttribute("allList",userService.allList());
		return "admin/TypeOf/editTypeOf";
	}
	
	@PatchMapping("/TypeOf/edit")
	public String editTypeOf(@ModelAttribute("TypeOfModel") TypeOfModel typeOfModel)
	{
		userService.editTypeOf(typeOfModel);
		return "redirect:/admin";
	}
	
	@GetMapping("/TypeOf/deleteTypeOf")
	public String deleteTypeOf(Model model)
	{
		model.addAttribute("TypeOfModel", new TypeOfModel());
		model.addAttribute("allList",userService.allList());
		return "admin/TypeOf/deleteTypeOf";
	}
	
	@DeleteMapping("/TypeOf/delete")
	public String deleteTypeOf(@ModelAttribute("TypeOfModel") TypeOfModel typeOfModel)
	{
		userService.deleteTypeOf(typeOfModel);
		return "redirect:/admin";
	}
	//ИЗМЕНЕНИЯ В PRODUCT
	
	@GetMapping("/{category}/createNewProduct")
	public String createProduct(@PathVariable("category") String category, Model model)
	{	
		ProductModel productModel = new ProductModel();
		userService.getProductWithRightIdOfCategory(productModel, category);
		model.addAttribute("ProductModel",productModel);
		model.addAttribute("category",category);
		return "admin/Product/createNewProduct";
	}
	@PostMapping("/Product/create")
	public String createProduct(@ModelAttribute("ProductModel") ProductModel productModel,
			@RequestParam("fileImage") MultipartFile multipartFile)
	{
		try {
			userService.saveProductModel(productModel, multipartFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "redirect:/admin";
	}
	
	@GetMapping("/{category}/deleteProduct")
	public String deleteProduct(@PathVariable("category") String category, Model model)
	{
		model.addAttribute("listOfCategory",userService.insideCategory(category));
		model.addAttribute("Product", new ProductModel());
		return "admin/Product/deleteProduct";
	}
	
	@DeleteMapping("/Product/delete")
	public String deleteProduct(@ModelAttribute("Product") ProductModel productModel)
	{
		userService.deleteProduct(productModel);
		return "redirect:/admin";
	}
	
	@GetMapping("/{category}/editProduct")
	public String chooseProductForEdit(@PathVariable("category") String category, Model model)
	{
		model.addAttribute("listOfCategory",userService.insideCategory(category));
		return "admin/Product/listOfProducts";
	}
	
	@GetMapping("/{category}/editProduct/{id}")
	public String editProduct(@PathVariable("id") Integer id, Model model)
	{
		model.addAttribute("Product", userService.getProductWithId(id));
		return "admin/Product/editProduct";
	}
	
	@PostMapping("/Product/editProduct")
	public String editProduct(@ModelAttribute("Product") ProductModel newProduct, @RequestParam("fileImage") MultipartFile multipartFile) throws IOException
	{	//меняем всё кроме id и fk_id
		ProductModel oldProduct = userService.getProductWithId(newProduct.getId());//старая версия
		userService.editProduct(oldProduct,newProduct, multipartFile);
		return "redirect:/admin";
	}
}
