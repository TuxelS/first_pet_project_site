package com.example.repositories;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.entities.Product;
import com.example.entities.Type;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Transactional
@Repository
public class TypeDAO {

    @PersistenceContext
	private EntityManager entityManager;
	
	public List<Type> allTypes(){
		List<Type> types = null;
		
		try(Session session = entityManager.unwrap(Session.class)){
			types = session.createQuery("FROM Type",Type.class).getResultList();
			return types;		
		}
	}
	
	
	public void create(Type type) {
		try(Session session = entityManager.unwrap(Session.class)){
			session.persist(type);
		}
	}
	
	//надо чекнуть тут какой то проеб
	public void edit(Type type) {
		try(Session session = entityManager.unwrap(Session.class)){
			Type type1 = session.get(Type.class, type.getId());
			type1.setCategory(type.getCategory());
			type1.setName(type.getName());
		}
		
	}
	
	public Type getProductById(Integer id) {
		
		try(Session session = entityManager.unwrap(Session.class)){
			Type type1 = session.get(Type.class,id);
			return type1;		
		}
		
	}
	
	public void delete(Type type) {
		try(Session session = entityManager.unwrap(Session.class)){
			
			Type type1 = session.get(Type.class, type.getId());
			session.remove(type1);
			
			System.out.println("TypeDAO: [delete] successfully");
		}
		
	}
	

	

	

	
}
