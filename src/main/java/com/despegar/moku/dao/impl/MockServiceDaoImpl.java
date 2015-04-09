package com.despegar.moku.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.despegar.moku.dao.MockServiceDao;
import com.despegar.moku.model.MockService;

@Repository
public class MockServiceDaoImpl extends AbstractDaoImpl<MockService> implements MockServiceDao {

	public MockServiceDaoImpl() {
		super(MockService.class);
	}

	@Override
	public MockService findByName(String name) {

		List<?> resultList = this.getEntityManager().createQuery("from MockService where name = ?1").setParameter(1, name).getResultList();

		return resultList.size() == 0 ? null : (MockService) resultList.get(0);
	}

}
