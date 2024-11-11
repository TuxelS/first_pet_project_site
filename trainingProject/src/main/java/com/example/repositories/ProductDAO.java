package com.example.repositories;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.example.models.ProductModel;

@Repository
public class ProductDAO {
	private final JdbcTemplate jdbcTemplate;
	
	public ProductDAO(JdbcTemplate jdbcTemplate)
	{
		this.jdbcTemplate=jdbcTemplate;
	}

	public List<ProductModel> getListOfAllProducts() {
		return jdbcTemplate.query("SELECT * FROM Product", new ProductMapper());
		
	}
	
	public Integer maxId()
	{
		return jdbcTemplate.queryForObject("SELECT MAX(pId) FROM Product", Integer.class);
	}
	public void saveProductToDB(ProductModel productModel) {
	    jdbcTemplate.update("INSERT INTO Product VALUES(?,?,?,?,?,?)", 
	    	productModel.getId(),
	    	productModel.getCost(),
	    	productModel.getFk_TypeOf_Id(),
	    	productModel.getText(),
	    	productModel.getPhoto_url(),
	    	productModel.getName());
	}

	public void deleteProduct(ProductModel productModel) {
		jdbcTemplate.update("DELETE FROM Product WHERE pId=?", productModel.getId());
	}
	
	public String getPhoto_urlFromId(ProductModel productModel)
	{
		return jdbcTemplate.queryForObject("SELECT pPhoto_url FROM Product WHERE pId=?", String.class, productModel.getId());
	}
	
	public ProductModel getProductWithId(Integer id)
	{
		return jdbcTemplate.queryForObject("SELECT * FROM Product WHERE pId=?", new ProductMapper(), id);
	}

	public void editProduct(ProductModel oldProduct) {
		String sql = "UPDATE Product SET pName = ?, pPhoto_url = ?, pText = ?, pCost = ? WHERE pId = ?";
		jdbcTemplate.update(sql,oldProduct.getName(),oldProduct.getPhoto_url(),oldProduct.getText(),oldProduct.getCost(),oldProduct.getId());
		
	}
	
}
