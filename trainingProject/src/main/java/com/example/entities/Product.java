package com.example.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Products")
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@Column(name = "cost")
	private String cost;

	@Column(name = "description")
	private String description;

	@Column(name = "photo_url")
	private String photoUrl;

	@Column(name = "name")
	private String name;

	public Product() {
	}

	public Product(String cost, String description, String photoUrl, String name) {
		this.cost = cost;
		this.description = description;
		this.photoUrl = photoUrl;
		this.name = name;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) { // используется только для формы thymeleaf в hidden формате
		this.id = id;
	}

	public String getCost() {
		return cost;
	}

	public void setCost(String cost) {
		this.cost = cost;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPhotoUrl() {
		return photoUrl;
	}

	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		String message = String.format("Product [id=%d, cost=%s, description=%s, photoUrl=%s, name=%s] ", id, cost,
				description, photoUrl, name);
		return message;
	}

}
