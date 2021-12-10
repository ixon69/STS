package com.ixon.stock.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DepositDTO {

	private String model;
	private String day;
	private String stockcode;
	private int tramount;
	
}
