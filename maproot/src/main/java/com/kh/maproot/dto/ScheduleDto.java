package com.kh.maproot.dto;

import java.security.Timestamp;

import jakarta.validation.constraints.NegativeOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor @Builder
public class ScheduleDto {
	
    private int scheduleNo;         // NUMBER
    private String scheduleName;    // VARCHAR2(100)
    private int scheduleView;       // NUMBER
    private int scheduleLike;       // NUMBER     
    private String scheduleOwner;   // REFERENCES account(account_id)
    private String schedulePublic;  // CHAR(1) 'Y' or 'N'
    private String scheduleState;   // VARCHAR2(20) ('open','close','progress')
    private Timestamp scheduleWtime; // TIMESTAMP
    private Timestamp scheduleEtime; // TIMESTAMP


}
