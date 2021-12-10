package com.ixon.stock.dao;

import java.util.List;

import com.ixon.stock.dto.StockDailyDTO;

public interface StockDailyDAO {

	List<StockDailyDTO> getStockDaily(StockDailyDTO param) throws Exception;
	List<StockDailyDTO> getLastSD(StockDailyDTO param) throws Exception;
	List<String> getDays(String param) throws Exception;
	
}
