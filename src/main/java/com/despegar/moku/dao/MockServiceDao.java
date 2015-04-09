package com.despegar.moku.dao;

import com.despegar.moku.model.MockService;

public interface MockServiceDao extends AbstractDao<MockService> {

	MockService findByName(String name);
	
}
