package com.example.models;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;

public class TypeOfModel {
	@Min(value=1,message = "Minimal value is 1")
	private Integer id;
	
	@NotEmpty(message = "Category shouldn't be empty")
	private String category;
	
	private String name;
	
	
	public TypeOfModel() {}
	public TypeOfModel(Integer id, String category, String name)
	{
		this.id=id;
		this.category=category;
		this.name=name;
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
	
}
