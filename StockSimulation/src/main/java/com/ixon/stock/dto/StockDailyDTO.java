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
public class StockDailyDTO {
	
	private String stockcode;
	private String day;
	private int start;
	private int high;
	private int low;
	private int last;
	private int vol;
	
	public StockDailyDTO(String stockcode, String day) {
		this.stockcode = stockcode;
		this.day = day;
	}

}
