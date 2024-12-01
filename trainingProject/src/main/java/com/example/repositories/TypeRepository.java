package com.example.repositories;

import java.util.List;
import org.springframework.data.repository.Repository;
import com.example.entities.Type;

public interface TypeRepository extends Repository<Type, Integer> {
	List<Type> findAll();// замена allTypes

	void save(Type type); // create

	void delete(Type type);// delete

	Type findById(Integer id);

	Type findByCategory(String category);
}
