package com.ixon.stock.dao;

import java.util.HashMap;
import java.util.List;

import com.ixon.stock.dto.BuyDTO;

public interface SimpleModelDAO {

	int insertTrading (BuyDTO param) throws Exception;
	int getQuantity(BuyDTO param) throws Exception;
	List<HashMap<String, Object>> getStockDaily(String day) throws Exception;
	
}
