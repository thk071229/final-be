package com.kh.maproot.dao;

import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class AccountLikeDao {
	
	@Autowired
	private SqlSession sqlSession;
	
	public void insert(String accountId, Long scheduleNo) {
		Map<String, Object> params = new HashMap<>();
		params.put("accountId", accountId);
		params.put("scheduleNo", scheduleNo);
		sqlSession.insert("accountLike.insert", params);
	}
	public boolean check(String accountId, Long scheduleNo) {
		Map<String, Object> params = new HashMap<>();
		params.put("accountId", accountId);
		params.put("scheduleNo", scheduleNo);
		int count = sqlSession.selectOne("accountLike.check", params);
		return count > 0;
	}
	public boolean delete(String accountId, Long scheduleNo) {
		Map<String, Object> params = new HashMap<>();
		params.put("accountId", accountId);
		params.put("scheduleNo", scheduleNo);
		return sqlSession.delete("accountLike.delete", params) > 0;
	}
	public int countLikes(Long scheduleNo) {
	    return sqlSession.selectOne("accountLike.countLikes", scheduleNo);
	}
	

}
