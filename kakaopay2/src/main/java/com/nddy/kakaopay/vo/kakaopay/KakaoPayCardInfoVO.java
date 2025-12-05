package com.nddy.kakaopay.vo.kakaopay;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class KakaoPayCardInfoVO {
	private String kakaopayPurchaseCorp;
	private String kakaopayPurchaseCorpCode;
	private String kakaopayIssuerCorp;
	private String kakaopayIssuerCorpCode;
	private String bin;
	private String cardType;
	private String installMonth;
	private String approvedId;
	private String cardMid;
	private String interestFreeInstall;
	private String cardItemCode;
}






