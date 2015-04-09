package com.despegar.moku.dao;

import java.util.List;

import com.despegar.moku.model.Model;

public interface AbstractDao<T extends Model> {

	void save(T model);
	
	T findById(Long id);
	
	List<T> findAll();
	
	void delete(T model);
	
}
