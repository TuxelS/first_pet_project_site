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
	public String adminMainPage(Model model) //просто список категорий
	{
		model.addAttribute("ListOfType",userService.allList());
		return "admin/adminMainPage";
	}
	
	//Изменения в TYPEOF
	@GetMapping("/TypeOf/createNewTypeOf")
	public String createNewTypeOf(Model model)
	{
		model.addAttribute("Type", new Type());
		System.out.println("GETMAPPING: createNewTypeOf вход");
		return "admin/TypeOf/createNewTypeOf";
	}
	
	@PostMapping("/TypeOf/create")
	public String createNewType(@ModelAttribute("Type") Type type)
	{
		userService.createType(type);
		System.out.println("POSTMAPPING: createNewType вход");
		return "redirect:/admin";
	}
	
	@GetMapping("/TypeOf/editTypeOf")
	public String chooseTypeOf(Model model)
	{
		model.addAttribute("allList",userService.allList());
		return "admin/TypeOf/listOfTypes";
	}
	
	@GetMapping("/TypeOf/editTypeOf/{id}")
	public String editType(@PathVariable("id") Integer id, Model model){
		model.addAttribute("Type",userService.getTypeById(id));
		return "admin/TypeOf/editTypeOf";
	}
	
	@PatchMapping("/TypeOf/editType")
	public String editTypeOf(@ModelAttribute("Type") Type type){
		userService.editType(type);
		return "redirect:/admin";
	}
	
	@GetMapping("/TypeOf/deleteTypeOf")
	public String deleteTypeOf(Model model)
	{
		model.addAttribute("Type", new Type());
		model.addAttribute("allList",userService.allList());
		return "admin/TypeOf/deleteTypeOf";
	}
	
	@DeleteMapping("/TypeOf/delete")
	public String deleteTypeOf(@ModelAttribute("Type") Type type)
	{
		userService.deleteType(type);
		return "redirect:/admin";
	}
	//ИЗМЕНЕНИЯ В PRODUCT
	
	
	@GetMapping("/{category}/createNewProduct")
	public String createProduct(@PathVariable("category") String category, Model model)
	{	
		userService.setDefiniteCategory(category);
		model.addAttribute("Product",new Product());
		
		return "admin/Product/createNewProduct";
	}
	@PostMapping("/Product/create")
	public String createProduct(@ModelAttribute("Product") Product product,
			@RequestParam("fileImage") MultipartFile multipartFile)
	{
		try {
			userService.saveProduct(product, multipartFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "redirect:/admin";
	}
	
	@GetMapping("/{category}/deleteProduct")
	public String deleteProduct(@PathVariable("category") String category, Model model)
	{
		model.addAttribute("listOfCategory",userService.insideCategory(category));
		model.addAttribute("Product", new Product());
		return "admin/Product/deleteProduct";
	}
	
	@DeleteMapping("/Product/delete")
	public String deleteProduct(@ModelAttribute("Product") Product product)
	{
		userService.deleteProduct(product);
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
		model.addAttribute("Product", userService.getProductById(id));
		return "admin/Product/editProduct";
	}
	
	@PostMapping("/Product/editProduct")
	public String editProduct(@ModelAttribute("Product") Product newProduct,
						      @RequestParam("fileImage") MultipartFile multipartFile
						      ) throws IOException
	{	
		//меняем всё кроме id и fk_id
		Product oldProduct = userService.getProductById(newProduct.getId());
		userService.editProduct(oldProduct,newProduct, multipartFile);
		return "redirect:/admin";
	}
}
