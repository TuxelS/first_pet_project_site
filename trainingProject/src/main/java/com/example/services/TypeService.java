package com.example.services;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.entities.Type;
import com.example.repositories.TypeRepository;

@Service
@Transactional
public class TypeService {

	private TypeRepository typeRepository;
	private String uploadDir = "src/main/resources/static/img/dishes/";

	public TypeService(TypeRepository typeRepository) {
		this.typeRepository = typeRepository;
	}

	public void create(Type type) {
		typeRepository.save(type);
	}

	public void update(Type type) {
		Type type1 = typeRepository.findById(type.getId());
		type1.setName(type.getName());
		type1.setCategory(type.getCategory());
		typeRepository.save(type1);
	}

	public void delete(Type type) { // здесь тоже нюанс, что в type не null только id
		Type type1 = typeRepository.findById(type.getId());
		type1.getProducts().stream().forEach(product -> {
			try {
				Files.deleteIfExists((new File(uploadDir + product.getPhotoUrl())).toPath());
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
		typeRepository.delete(type1);
	}

	public List<Type> allList() {
		return typeRepository.findAll();
	}

	public Type getTypeById(Integer id) {
		return typeRepository.findById(id);
	}

}
