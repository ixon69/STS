package com.ixon.stock.controller;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ixon.stock.dao.BuyDAO;
import com.ixon.stock.dto.BuyDTO;

@RestController
@MapperScan(basePackages="com.ixon.stock.dao")
public class BuyController {

	@Autowired
	private BuyDAO buyDAO;
	
	@RequestMapping("/insertBuy")
	public int insertBuy(@RequestParam(value="model", defaultValue = "")String model 
			                              ,@RequestParam(value="stockcode", defaultValue = "")String stockcode
			                              ,@RequestParam(value="day", defaultValue = "")String day
			                              ,@RequestParam(value="price", defaultValue = "0")int price
			                              ,@RequestParam(value="vol", defaultValue = "0")int vol
			                              ,@RequestParam(value="fee", defaultValue = "0")int fee) throws Exception {
		final BuyDTO param = new BuyDTO(model, stockcode, day, price, vol, fee);
		final int result = buyDAO.insertBuy(param);
		return result;
	}
	
	@RequestMapping("/trquantity")
	public int trquantity(@RequestParam(value="model", defaultValue = "")String model 
			                              ,@RequestParam(value="stockcode", defaultValue = "")String stockcode
			                              ,@RequestParam(value="day", defaultValue = "")String day) throws Exception {
		final BuyDTO param = new BuyDTO(model, stockcode, day);
		final int qtr = buyDAO.getQuantity(param);
		return qtr;
	}
}
