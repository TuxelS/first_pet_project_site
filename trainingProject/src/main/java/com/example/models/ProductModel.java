package com.example.models;

public class ProductModel {
	private Integer id;
	private String cost;
	private Integer fk_TypeOf_Id;
	private String text;
	private String photo_url;
	private String name;
	public ProductModel() {};
	
	public ProductModel(Integer id, String cost, Integer fk_TypeOf_Id, String text, String photo_url, String name)
	{
		this.id=id;
		this.cost=cost;
		this.fk_TypeOf_Id=fk_TypeOf_Id;
		this.text=text;
		this.photo_url=photo_url;
		this.name=name;
	}
	
	public String getPhoto_url() {
		return photo_url;
	}
	public void setPhoto_url(String photo_url) {
		this.photo_url = photo_url;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public Integer getFk_TypeOf_Id() {
		return fk_TypeOf_Id;
	}
	public void setFk_TypeOf_Id(Integer fk_TypeOf_Id) {
		this.fk_TypeOf_Id = fk_TypeOf_Id;
	}
	public String getCost() {
		return cost;
	}
	public void setCost(String cost) {
		this.cost = cost;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	
}
