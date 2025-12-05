package com.nddy.kakaopay.dao;

import java.util.List;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.nddy.kakaopay.dto.GiftcardDto;

@Repository
public class GiftcardDao {
	@Autowired
	private SqlSession sqlSession;
	
	public List<GiftcardDto> selectList() {
		return sqlSession.selectList("giftcard.list");
	}
	public GiftcardDto selectOne(Long giftcardNo) {
		return sqlSession.selectOne("giftcard.list", giftcardNo);
	}
}