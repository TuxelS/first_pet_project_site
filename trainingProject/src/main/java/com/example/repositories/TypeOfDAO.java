package com.example.repositories;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.models.TypeOfModel;

@Repository
public class TypeOfDAO {

	private final JdbcTemplate jdbcTemplate;
	
	public TypeOfDAO(JdbcTemplate jdbcTemplate)
	{
		this.jdbcTemplate=jdbcTemplate;
	}
	
	public List<TypeOfModel> allCategories()
	{
		return jdbcTemplate.query("SELECT * FROM TypeOf", new TypeOfMapper());
	}
	
	public TypeOfModel getIdOfCategory(String category)
	{
		TypeOfModel typeOfModel = null;
		try {
			typeOfModel=jdbcTemplate.queryForObject("SELECT * FROM TypeOf WHERE tCategory = ?", new TypeOfMapper(), category);
		} catch (Exception e) {
			System.out.println("Ошибка в методе TypeOfDAO/getIdOfCategory");
			//System.exit(0);
		}
		
		return typeOfModel;
	
	}
	
	public Integer getMaxIdOfTypes()
	{
		return jdbcTemplate.queryForObject("SELECT MAX(tId) FROM TYPEOF", Integer.class);
	}

	public void create(TypeOfModel typeOfModel) {
		jdbcTemplate.update("INSERT INTO TypeOf VALUES(?,?,?)",(getMaxIdOfTypes()+1),typeOfModel.getCategory(),typeOfModel.getName());
	}

	public void editTypeOf(TypeOfModel typeOfModel) {
		jdbcTemplate.update("UPDATE TypeOf SET tCategory = ?, tName=? WHERE tId = ?",typeOfModel.getCategory(),typeOfModel.getName(),typeOfModel.getId());	
	}

	public void deleteTypeOf(TypeOfModel typeOfModel) {
		jdbcTemplate.update("DELETE FROM TypeOf WHERE tId = ?",typeOfModel.getId());	
	}
	
}
