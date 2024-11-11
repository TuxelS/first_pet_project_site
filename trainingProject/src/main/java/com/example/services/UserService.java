package com.example.services;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.stream.Collectors;

import org.hibernate.validator.internal.util.privilegedactions.NewInstance;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.models.ProductModel;
import com.example.models.TypeOfModel;
import com.example.repositories.ProductDAO;
import com.example.repositories.TypeOfDAO;

@Service
public class UserService {
	private TypeOfDAO typeOfDAO;
	private ProductDAO productDAO;
	private String uploadDir = "src/main/resources/static/img/dishes/";
	public UserService(TypeOfDAO typeOfDAO, ProductDAO productDAO)
	{
		this.typeOfDAO=typeOfDAO;
		this.productDAO=productDAO;
	}
	public List<TypeOfModel> allList() 
	{
		return typeOfDAO.allCategories();
	}
	
	public List<ProductModel> insideCategory(String category) {
		List<ProductModel> definiteCategoryList;
		definiteCategoryList = productDAO.getListOfAllProducts()  //подстраиваемся под конкретную категорию
			.stream()
			.filter(product->
						product.getFk_TypeOf_Id().equals(typeOfDAO.getIdOfCategory(category).getId()))  //getIdOfCategory returns definite Id of arg:category in table [TypeOf]
			.collect(Collectors.toList());
		return definiteCategoryList;
	}
	public void saveNewTypeOf(TypeOfModel typeOfModel) {
		typeOfDAO.create(typeOfModel);
	}
	public void editTypeOf(TypeOfModel typeOfModel) {
		
		typeOfDAO.editTypeOf(typeOfModel);
		
	}
	public void deleteTypeOf(TypeOfModel typeOfModel) {
		typeOfDAO.deleteTypeOf(typeOfModel);
	}
	
	public void getProductWithRightIdOfCategory(ProductModel productModel, String category)
	{
		productModel.setFk_TypeOf_Id(typeOfDAO.getIdOfCategory(category).getId());
	}
	
	 public void saveProductModel(ProductModel productModel, MultipartFile file) throws IOException {
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
		        productModel.setPhoto_url(file.getOriginalFilename().toString()); // Путь для доступа к изображению
		        productModel.setId(productDAO.maxId() + 1);
		    } catch (IllegalStateException e) {
		        System.err.println("IllegalStateException: " + e.getMessage());
		        e.printStackTrace();
		    } catch (IOException e) {
		        System.err.println("IOException: " + e.getMessage());
		        e.printStackTrace();
		    }
		    //сохраняем в бд
		    productDAO.saveProductToDB(productModel);
		 	      
	 }
	 
	 public void deleteProduct(ProductModel productModel)
	 {
		 //служебная логика
		 //удаление файла картинки
		 
		 try {
			Files.deleteIfExists((new File(uploadDir+productDAO.getPhoto_urlFromId(productModel)).toPath()));
			productDAO.deleteProduct(productModel);
		} catch (IOException e) {
			e.printStackTrace();
		}
		 
	 }

	public ProductModel getProductWithId(Integer id)
	{
		return productDAO.getProductWithId(id);
		
	}
	public void editProduct(ProductModel oldProduct, ProductModel newProduct, MultipartFile multipartFile) throws IOException {
		oldProduct.setCost(newProduct.getCost());
		oldProduct.setName(newProduct.getName());
		oldProduct.setText(newProduct.getText());
		if(!multipartFile.isEmpty())		//меняем фото
		{
			Files.delete((new File(uploadDir+oldProduct.getPhoto_url())).toPath());
			oldProduct.setPhoto_url(multipartFile.getOriginalFilename());
			multipartFile.transferTo((new File(uploadDir+multipartFile.getOriginalFilename()).toPath()));
		}
		
		productDAO.editProduct(oldProduct);
		
	}
}
