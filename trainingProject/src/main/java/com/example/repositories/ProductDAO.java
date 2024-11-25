package com.example.repositories;

import java.util.List;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.entities.Product;
import com.example.entities.Type;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;


@Transactional
@Repository
public class ProductDAO {
	
    @PersistenceContext
	private EntityManager entityManager;
	
	public List<Product> getDefiniteProducts(String category)
	{
		List<Product> products = null;
		
		try(Session session = entityManager.unwrap(Session.class)){
			Type type = session.createQuery("from Type where category = :category",Type.class)
					.setParameter("category", category)
					.getSingleResult();
			products = type.getProducts();
			return products;		
		}
	}

	
	public void deleteProduct(Product product) {
		try(Session session = entityManager.unwrap(Session.class)){
			Product product1 = session.get(Product.class, product.getId());
			session.remove(product1);
		}
		
	}

	
	public Product getProductById(Integer id) {
		
		try(Session session = entityManager.unwrap(Session.class)){
			Product product1 = session.get(Product.class,id);
			return product1;		
		}
		
	}


	public void editProduct(Product product) {
		try(Session session = entityManager.unwrap(Session.class)){
			Product product1 = session.get(Product.class, product.getId());
			product1.setCost(product.getCost());
			product1.setDescription(product.getDescription());
			product1.setName(product.getName());
			product1.setPhotoUrl(product.getPhotoUrl());
		}
		
	}

	
	public void saveProduct(Product product, String definiteCategory) {
		try(Session session = entityManager.unwrap(Session.class)){
			Type type = session.createQuery("from Type where category = :category",Type.class)
					.setParameter("category", definiteCategory)
					.getSingleResult();
			type.addProductToType(product);
			session.persist(type);
		}
	}
	
	
	
	
}
