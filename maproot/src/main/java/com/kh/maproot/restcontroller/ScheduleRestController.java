package com.kh.maproot.restcontroller;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kh.maproot.dao.ScheduleDao;
import com.kh.maproot.dao.ScheduleMemberDao;
import com.kh.maproot.dao.ScheduleTagDao;
import com.kh.maproot.dao.ScheduleUnitDao;
import com.kh.maproot.dao.ShareLinkDao;
import com.kh.maproot.dao.TagDao;
import com.kh.maproot.dto.ScheduleDto;
import com.kh.maproot.dto.ScheduleMemberDto;
import com.kh.maproot.dto.ScheduleTagDto;
import com.kh.maproot.dto.ScheduleUnitDto;
import com.kh.maproot.dto.ShareLinkDto;
import com.kh.maproot.dto.TagDto;
import com.kh.maproot.schedule.vo.ScheduleCreateRequestVO;
import com.kh.maproot.schedule.vo.ScheduleListResponseVO;
import com.kh.maproot.service.TokenService;

@CrossOrigin
@RestController
@RequestMapping("/schedule")
public class ScheduleRestController {
	
	@Autowired
	private TagDao tagDao;
	@Autowired
	private ScheduleDao scheduleDao;
	@Autowired
	private ScheduleTagDao scheduleTagDao;
	@Autowired
	private ScheduleMemberDao scheduleMemberDao;
	@Autowired
	private ScheduleUnitDao scheduleUnitDao;
	@Autowired
	private TokenService tokenService;
	@Autowired
	private ShareLinkDao shareLinkDao;
	
	@GetMapping("/tagList")
	public List<TagDto> tagList() {
		return tagDao.selectAll();
	}
	
	@PostMapping("/insert")
	public ScheduleDto insert(@RequestBody ScheduleCreateRequestVO scheduleVO) {
		
		//일정 등록
		ScheduleDto scheduleDto = ScheduleDto.builder()
						.scheduleName(scheduleVO.getScheduleName())
						.scheduleOwner(scheduleVO.getScheduleOwner())
						.scheduleWtime(Timestamp.valueOf(LocalDateTime.now()))
						.scheduleStartDate(scheduleVO.getScheduleStartDate())
						.scheduleEndDate(scheduleVO.getScheduleEndDate())
						.build();
		
		int sequence = scheduleDao.insert(scheduleDto);
		
		//태그 등록
		for(String tagName : scheduleVO.getTagNoList()) {
			ScheduleTagDto scheduleTagDto = ScheduleTagDto.builder()
					.scheduleNo(sequence)
					.tagName(tagName)
					.build();			
			
			scheduleTagDao.insert(scheduleTagDto);
		}
		
		//맴버 테이블에도 추가
		
		ScheduleMemberDto scheduleMemberDto = ScheduleMemberDto.builder()
				.ScheduleNo(sequence)
				.accountId("testuser1")
				.scheduleMemberNickname("테스트유저1")
				.scheduleMemberRole("member")
				.scheduleMemberNotify("Y")
			.build();
		
		scheduleMemberDao.insert(scheduleMemberDto);
		
		return scheduleDto;
	}
	
	@GetMapping("/list/{accountId}")
	public List<ScheduleListResponseVO> list(
			@PathVariable String accountId
			) {
		System.out.println("데이터확인"+accountId);
		//회원에 따른 일정 찾기 (스케쥴 맴버 테이블에서 검색)
		List<ScheduleMemberDto> list = scheduleMemberDao.selectByAccountId(accountId);
		
		//일정 내용 찾기 (찾은 dto의 pk로 검색)

		List<ScheduleListResponseVO> voList = new ArrayList<>(); 
		
		for(ScheduleMemberDto scheduleMemberDto : list) {
			
			int scheduleNo = scheduleMemberDto.getScheduleNo();
			
			ScheduleDto findScheduleDto = scheduleDao.selectByScheduleNo(scheduleNo);
			ScheduleUnitDto unitFirst = scheduleUnitDao.selectFirstUnit(scheduleNo);
			Integer unitCount = scheduleUnitDao.selectUnitCount(scheduleNo);
			Integer memberCount = scheduleMemberDao.selectMemberCount(scheduleNo);
			
			ScheduleListResponseVO scheduleListResponseVO = ScheduleListResponseVO.builder()
					.scheduleNo(scheduleMemberDto.getScheduleNo())
					.scheduleName(findScheduleDto.getScheduleName())
					.scheduleState(findScheduleDto.getScheduleState())
					.schedulePublic(findScheduleDto.getSchedulePublic())
					.scheduleOwner(findScheduleDto.getScheduleOwner())
					.scheduleStartDate(findScheduleDto.getScheduleStartDate())
					.scheduleEndDate(findScheduleDto.getScheduleEndDate())
					.unitFirst(unitFirst)
					.unitCount(unitCount)
					.memberCount(memberCount)
					.scheduleImage("공란")
					.build();
			
			voList.add(scheduleListResponseVO);
			
		}
		
		//검색된 일정들 VO로 전송
		
		return voList;
	}
	
	@GetMapping("/memberList/{scheduleNo}")
	public List<ScheduleMemberDto> selectMemberList(@PathVariable int scheduleNo) {
		return scheduleMemberDao.selectByScheduleNo(scheduleNo);
	}
	
	//일정 공유
	@GetMapping("/share/{scheduleNo}")
	public String shareSchedule(@PathVariable int scheduleNo) {
//	랜덤한 shareKey 생성 (UUID, SecureRandom 등)
	String shareKey = UUID.randomUUID().toString();
	System.out.println("shareKey : "+shareKey);
//	만료 시간 계산 (예: +7일)
	LocalDateTime expireTime = LocalDateTime.now().plusDays(7);
	System.out.println("expireTime : " + expireTime);
		
//	share_link 테이블에 insert
	ShareLinkDto shareLinkDto = ShareLinkDto.builder()
			.shareKey(shareKey)
			.targetScheduleNo(scheduleNo)
			.expireTime(expireTime)
			.build();
			
	shareLinkDao.insert(shareLinkDto);
		
//	생성된 shareKey 반환
		return shareKey;
	}


}
