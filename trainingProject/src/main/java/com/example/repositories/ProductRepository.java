package com.example.repositories;

import org.springframework.data.repository.Repository;
import com.example.entities.Product;

public interface ProductRepository extends Repository<Product, Integer> {

	void delete(Product product);

	Product findById(Integer id);

	void save(Product product);

}
