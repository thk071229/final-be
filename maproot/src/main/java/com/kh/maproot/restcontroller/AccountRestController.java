package com.kh.maproot.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kh.maproot.dao.AccountDao;
import com.kh.maproot.dto.AccountDto;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "회원 관리 컨트롤러")
@CrossOrigin
@RestController
@RequestMapping("/account")
public class AccountRestController {

	@Autowired
	private AccountDao accountDao;
	
	@Operation(
		deprecated = false
		,description = "회원 가입을 위한 등록 기능"
		,responses = {
				@ApiResponse(responseCode = "200"),
				@ApiResponse(responseCode = "400"),
				@ApiResponse(responseCode = "500")
		}
	)
	// 회원가입
	@PostMapping("/")
	public void insert(AccountDto accountDto) {
		accountDao.insert(accountDto);
	}
}
