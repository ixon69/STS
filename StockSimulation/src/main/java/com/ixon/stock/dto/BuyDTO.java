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
public class BuyDTO {

	private String model;
	private String stockcode;
	private String day;
	private int price;
	private int vol;
	private int fee;
	
	public BuyDTO(String model, String stockcode, String day) {
		this.model = model;
		this.stockcode = stockcode;
		this.day = day;
	}

}
