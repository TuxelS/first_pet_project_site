package com.example.entities;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "Types")
public class Type {
	// в планах добавить аннотацию валидации без инкапсулирования
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@Column(name = "category")
	private String category;

	@Column(name = "name")
	private String name;
	
	// lazy - чтобы getProducts только по запросу искали в БД
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY) 
													
	// ссылается на поле в target таблице
	@JoinColumn(name = "type_id", nullable = false) 
	private List<Product> products;

	public Type() {
	}

	public Type(String category, String name) {
		this.category = category;
		this.name = name;
	}

	public void addProductToType(Product product) {
		if (products == null)
			products = new ArrayList<>();
		products.add(product);
	}

	// геттеры и сеттеры используются для thymeleaf
	public List<Product> getProducts() {
		return products;
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

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

	@Override
	public String toString() {
		String message = String.format("Type [id=%d, category=%s, name=%s]", id, category, name);
		return message;
	}
}
