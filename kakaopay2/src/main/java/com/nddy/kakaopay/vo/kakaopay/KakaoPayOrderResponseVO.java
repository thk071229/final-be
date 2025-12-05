package com.nddy.kakaopay.vo.kakaopay;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class KakaoPayOrderResponseVO {
	private String tid;
	private String cid;
	private String status;
	private String partnerOrderId;
	private String partnerUserId;
	private String paymentMethodType;
	private KakaoPayAmountVO amount;
	private KakaoPayAmountVO cancelAmount;
	private KakaoPayAmountVO cancelAvailableAmount;
	private String itemName;
	private String itemCode;
	private Integer quantity;
	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
	private LocalDateTime createdAt;
	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
	private LocalDateTime approvedAt;
	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
	private LocalDateTime canceledAt;
	private KakaoPaySelectedCardInfoVO selectedCardInfo;
	private List<KakaoPayPaymentActionDetailVO> paymentActionDetails;
}
