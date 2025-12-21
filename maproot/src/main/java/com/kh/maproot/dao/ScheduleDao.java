package com.kh.maproot.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kh.maproot.dto.AccountDto;
import com.kh.maproot.dto.ScheduleDto;
import com.kh.maproot.error.NeedPermissionException;
import com.kh.maproot.error.TargetNotfoundException;
import com.kh.maproot.vo.PageVO;
import com.kh.maproot.vo.ScheduleSearchVO;
import com.kh.maproot.vo.TokenVO;

@Repository
public class ScheduleDao {
	
	@Autowired
	private SqlSession sqlSession;
	
	public Long insert(ScheduleDto scheduleDto) {
		Long sequence = sqlSession.selectOne("schedule.sequence");
		scheduleDto.setScheduleNo(sequence);
		sqlSession.insert("schedule.insert", scheduleDto);
		return sequence;
	}
	
	public ScheduleDto selectByScheduleNo(Long scheduleNo) {
		ScheduleDto scheduleDto = sqlSession.selectOne("schedule.selectByScheduleNo", scheduleNo);
		if(scheduleDto == null) throw new TargetNotfoundException();
		return scheduleDto;
	}
	public ScheduleDto selectByScheduleNo(ScheduleDto scheduleDto) {
		return selectByScheduleNo(scheduleDto.getScheduleNo());
	}


	public ScheduleDto updateUnit(ScheduleDto scheduleDto) {
		sqlSession.update("schedule.updateUnit", scheduleDto);
		 return selectByScheduleNo(scheduleDto.getScheduleNo());
	}
	
	// 일정 대표이미지 
	public void connect(Long scheduleNo, Long attachmentNo) {
		Map<String, Object> params = new HashMap<>();
		params.put("scheduleNo", scheduleNo);
		params.put("attachmentNo", attachmentNo);
		sqlSession.insert("schedule.connect", params);
	}
	public Long findAttach(Long scheduleNo) {
		return sqlSession.selectOne("schedule.findAttach", scheduleNo);
	}
	
	// 공개된 일정 리스트
	public List<ScheduleDto> selectAllList(){
		return sqlSession.selectList("schedule.selectAllList");
	}
	// 관리자용 일정 리스트
	public List<ScheduleDto> selectAllListForAdmin(TokenVO tokenVO){
		if(!tokenVO.getLoginLevel().equals("관리자")) throw new NeedPermissionException();	
		return sqlSession.selectList("schedule.selectAllListForAdmin");
	}
	
	// 1. 리스트 조회
	public List<ScheduleDto> selectListForSearch(ScheduleSearchVO searchVO, PageVO pageVO) {
	    Map<String, Object> params = new HashMap<>();
	    params.put("search", searchVO);
	    params.put("page", pageVO);
	    
	    // 이제 XML에서 #{search.keyword}, #{page.begin} 등으로 접근 가능합니다.
	    return sqlSession.selectList("schedule.selectListForSearch", params);
	}

	// 2. 카운트 조회
	public int countForSearch(ScheduleSearchVO searchVO, TokenVO tokenVO) {
	    // 권한 체크
	    if (tokenVO == null || !tokenVO.getLoginLevel().equals("관리자")) {
	        throw new NeedPermissionException();
	    }
	    
	    // 카운트도 검색 조건(keyword 등)에 영향을 받으므로 searchVO를 넘겨야 합니다.
	    Map<String, Object> params = new HashMap<>();
	    params.put("search", searchVO);
	    
	    return sqlSession.selectOne("schedule.countForSearch", params);
	}
	

}
