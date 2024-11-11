package com.example.repositories;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.example.models.ProductModel;

public class ProductMapper implements RowMapper<ProductModel>{

	@Override
	public ProductModel mapRow(ResultSet rs, int rowNum) throws SQLException {
		ProductModel productModel = new ProductModel();
		productModel.setId(rs.getInt("pId"));
		productModel.setCost(rs.getString("pCost"));
		productModel.setFk_TypeOf_Id(rs.getInt("pTypeOf_id"));
		productModel.setText(rs.getString("pText"));
		productModel.setPhoto_url(rs.getString("pPhoto_url"));
		productModel.setName(rs.getString("pName"));
		return productModel;
	}

}
