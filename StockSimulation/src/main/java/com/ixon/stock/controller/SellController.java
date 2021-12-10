package com.ixon.stock.controller;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ixon.stock.dao.SellDAO;
import com.ixon.stock.dto.SellDTO;

@RestController
@MapperScan(basePackages="com.ixon.stock.dao")
public class SellController {

	@Autowired
	private SellDAO sellDAO;
	
	@RequestMapping("/insertSell")
	public int insertTrading(@RequestParam(value="buyid", defaultValue = "")int buyid 
			                              ,@RequestParam(value="day", defaultValue = "")String day
			                              ,@RequestParam(value="price", defaultValue = "0")int price
			                              ,@RequestParam(value="vol", defaultValue = "0")int vol
			                              ,@RequestParam(value="fee", defaultValue = "0")int fee) throws Exception {
		final SellDTO param = new SellDTO(buyid, day, price, vol, fee);
		final int result = sellDAO.insertSell(param);
		return result;
	}
	
}
