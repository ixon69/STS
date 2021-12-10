package com.ixon.stock.dao;

import com.ixon.stock.dto.DepositDTO;

public interface DepositDAO {
	
	int insertDeposit (DepositDTO param) throws Exception;
}
