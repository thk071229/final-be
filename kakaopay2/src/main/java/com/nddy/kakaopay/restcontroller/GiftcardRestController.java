package com.nddy.kakaopay.restcontroller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.nddy.kakaopay.dao.GiftcardDao;
import com.nddy.kakaopay.dto.GiftcardDto;

import lombok.extern.slf4j.Slf4j;

@CrossOrigin
@RestController
@RequestMapping("/giftcard") @Slf4j
public class GiftcardRestController {
	@Autowired
	private GiftcardDao giftcardDao;
	
	@GetMapping("/")
	public List<GiftcardDto> list(){
		List<GiftcardDto> giftcardDto = giftcardDao.selectList();
		log.debug("ASdadasd");
		log.debug(giftcardDto.toString());
		return giftcardDto;
	}
	
	@GetMapping("/{giftcardNo}")
	public GiftcardDto detail(@PathVariable long giftcardNo){
		return giftcardDao.selectOne(giftcardNo);
	}
}