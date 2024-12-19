package com.example.repositories;

import org.springframework.data.repository.Repository;
import com.example.entities.Product;
import java.util.List;


public interface ProductRepository extends Repository<Product, Integer> {

	void delete(Product product);

	Product findById(Integer id);

	void save(Product product);
	
	List<Product> findByPhotoUrl(String photoUrl);

}
