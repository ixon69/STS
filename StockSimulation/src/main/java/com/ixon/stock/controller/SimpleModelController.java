package com.ixon.stock.controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ixon.stock.dto.StockDailyDTO;
import com.ixon.stock.service.SimpleModel;

@RestController
public class SimpleModelController {

	@Autowired
	private SimpleModel simpleModel;

	@RequestMapping("/modellastsd")
	public List<StockDailyDTO> modellastsd(@RequestParam(value="stockcode", defaultValue = "")String stockcode 
			                              ,@RequestParam(value="day", defaultValue = "")String day) throws Exception {
		return simpleModel.getLastSD(day);
	}
	
	@RequestMapping("/modelstockdaily")
	public List<HashMap<String, Object>> modelstockdaily(@RequestParam(value="stockcode", defaultValue = "")String stockcode 
			                              ,@RequestParam(value="day", defaultValue = "")String day) throws Exception {
		return simpleModel.getStockDaily(day);
	}
	
	@RequestMapping("/runtest")
	public String runtest(@RequestParam(value="stockcode", defaultValue = "")String stockcode 
			                              ,@RequestParam(value="day", defaultValue = "")String day) throws Exception {
		return simpleModel.checkUpDown(stockcode, day);
	}
}
