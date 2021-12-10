package com.ixon.stock.dao;

import com.ixon.stock.dto.BuyDTO;

public interface BuyDAO {

	int insertBuy (BuyDTO param) throws Exception;
	int getQuantity(BuyDTO param) throws Exception;
	
}
