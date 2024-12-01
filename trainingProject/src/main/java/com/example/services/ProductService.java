package com.example.services;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.example.entities.Product;
import com.example.repositories.ProductRepository;
import com.example.repositories.TypeRepository;
import com.example.entities.Type;

@Service
@Transactional
public class ProductService {

	private ProductRepository productRepository;
	private TypeRepository typeRepository;
	private String uploadDir = "src/main/resources/static/img/dishes/";

	public ProductService(ProductRepository productRepository, TypeRepository typeRepository) {
		this.productRepository = productRepository;
		this.typeRepository = typeRepository;
	}

	public List<Product> getProductsByCategory(String category) {
		Type typeFromRepo = typeRepository.findByCategory(category);
		return typeFromRepo.getProducts();
	}
	
	// нюанс в том, что передаем только id, остальные значения = null
	public void delete(Product product) { 
		try {
			Product productFromRepo = productRepository.findById(product.getId());
			Files.deleteIfExists((new File(uploadDir + productFromRepo.getPhotoUrl())).toPath());
			productRepository.delete(productFromRepo);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void create(Product product, String category, MultipartFile multipartFile) {
		// Укажите правильный путь к директории
		File directory = new File(uploadDir);
		// Создаем директорию, если она не существует
		if (!directory.exists()) {
			boolean created = directory.mkdirs();
			if (!created) {
				System.err.println("Не удалось создать директорию: " + uploadDir);
				return;
			}
		}
		// Сохраняем файл
		File imageFile = new File(uploadDir + multipartFile.getOriginalFilename());
		try {
			multipartFile.transferTo(imageFile.toPath());
			product.setPhotoUrl(multipartFile.getOriginalFilename());
		} catch (IllegalStateException e) {
			System.err.println("IllegalStateException: " + e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println("IOException: " + e.getMessage());
			e.printStackTrace();
		}
		// сохраняем в бд
		Type typeFromRepo = typeRepository.findByCategory(category);
		typeFromRepo.addProductToType(product);
		productRepository.save(product);
		typeRepository.save(typeFromRepo);
	}

	public void update(Product product, MultipartFile multipartFile) throws IOException {
		Product productFromRepo = productRepository.findById(product.getId());
		productFromRepo.setCost(product.getCost());
		productFromRepo.setDescription(product.getDescription());
		productFromRepo.setName(product.getName());
		// меняем фото
		if (!multipartFile.isEmpty()) 
		{
			Files.delete((new File(uploadDir + product.getPhotoUrl())).toPath());
			productFromRepo.setPhotoUrl(multipartFile.getOriginalFilename());
			multipartFile.transferTo((new File(uploadDir + multipartFile.getOriginalFilename()).toPath()));
		}
		productRepository.save(productFromRepo);
	}

	public Product getProductById(Integer id) {
		return productRepository.findById(id);
	}

}
