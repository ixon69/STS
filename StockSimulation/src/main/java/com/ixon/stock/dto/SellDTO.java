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
public class SellDTO {

	private int buyid;
	private String day;
	private int price;
	private int vol;
	private int fee;
	
}
