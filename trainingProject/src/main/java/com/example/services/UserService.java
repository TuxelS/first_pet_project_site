package com.example.services;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Optional;

import javax.lang.model.element.Element;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.entities.Product;
import com.example.entities.Type;
import com.example.repositories.ProductDAO;
import com.example.repositories.TypeDAO;


@Service
public class UserService {
	private TypeDAO typeDAO;
	private ProductDAO productDAO;
	private String uploadDir = "src/main/resources/static/img/dishes/";
	public UserService(TypeDAO typeDAO, ProductDAO productDAO)
	{
		this.typeDAO=typeDAO;
		this.productDAO=productDAO;
	}
	public List<Type> allList() 
	{
		return typeDAO.allTypes();
	}
	
	public List<Product> insideCategory(String category) {
		return productDAO.getDefiniteProducts(category);
	}
	public void createType(Type type) {
		System.out.println("UserService: void createType вход");
		typeDAO.create(type);
		
	}
	public void editType(Type type) {
		
		typeDAO.edit(type);
		
	}
	public void deleteType(Type type) {
		typeDAO.delete(type);
	}
	
	public Type getTypeById(Integer id){
		return typeDAO.getProductById(id);	
	}
	
	
	 public void saveProduct(Product product, MultipartFile file) throws IOException {
	       	//служебная логика
		 	// Укажите правильный путь к директории
		    File directory = new File(uploadDir);
		    // Создаем директорию, если она не существует
		    if (!directory.exists()) {
		        boolean created = directory.mkdirs();
		        if (!created) {
		            System.err.println("Не удалось создать директорию: " + uploadDir);
		            return; // Выход из метода, если директорию не удалось создать
		        }
		    }
		    // Сохраняем файл
		    File imageFile = new File(uploadDir + file.getOriginalFilename());
		    try {
		        file.transferTo(imageFile.toPath());
		        product.setPhotoUrl(file.getOriginalFilename().toString()); // Путь для доступа к изображению
		    } catch (IllegalStateException e) {
		        System.err.println("IllegalStateException: " + e.getMessage());
		        e.printStackTrace();
		    } catch (IOException e) {
		        System.err.println("IOException: " + e.getMessage());
		        e.printStackTrace();
		    }
		    //сохраняем в бд
		    productDAO.saveProduct(product,getDefiniteCategory());
		 	      
	 }
	 
	 public void deleteProduct(Product product)
	 {
		 //служебная логика
		 //удаление файла картинки
		 
		 try {
			Files.deleteIfExists((new File(uploadDir+productDAO.getProductById(product.getId()).getPhotoUrl()).toPath()));
			productDAO.deleteProduct(product);
		} catch (IOException e) {
			e.printStackTrace();
		}
		 
	 }

	public Product getProductById(Integer id)
	{
		return productDAO.getProductById(id);
		
	}
	public void editProduct(Product oldProduct, Product newProduct, MultipartFile multipartFile) throws IOException {
		oldProduct.setCost(newProduct.getCost());
		oldProduct.setName(newProduct.getName());
		oldProduct.setDescription(newProduct.getDescription());
		if(!multipartFile.isEmpty())	//меняем фото
		{
			Files.delete((new File(uploadDir+oldProduct.getPhotoUrl())).toPath());
			oldProduct.setPhotoUrl(multipartFile.getOriginalFilename());
			multipartFile.transferTo((new File(uploadDir+multipartFile.getOriginalFilename()).toPath()));
		}
		
		productDAO.editProduct(oldProduct);
		
	}
	
	private String definiteCategory;		//нужно для создания product
	
	public void setDefiniteCategory(String category) {
		definiteCategory = category;
	}
	
	public String getDefiniteCategory(){
		String tmpCategory = definiteCategory;
		definiteCategory = null;
		return tmpCategory;
	}
}
