package com.example.repositories;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.example.models.TypeOfModel;

public class TypeOfMapper implements RowMapper<TypeOfModel>{

	@Override
	public TypeOfModel mapRow(ResultSet rs, int rowNum) throws SQLException {
		TypeOfModel typeOfModel = new TypeOfModel();
		typeOfModel.setId(rs.getInt("tId"));
		typeOfModel.setCategory(rs.getString("tCategory"));
		typeOfModel.setName(rs.getString("tName"));
		return typeOfModel;
	}
	

}
