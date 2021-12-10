package com.ixon.stock.controller;

import java.util.List;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ixon.stock.dao.StockDailyDAO;
import com.ixon.stock.dto.StockDailyDTO;

@RestController
@MapperScan(basePackages="com.ixon.stock.dao")
public class StockDailyController {

	@Autowired
	private StockDailyDAO stockDailyDAO;
	
	@RequestMapping("/stockdaily")
	public List<StockDailyDTO> stockdaily(@RequestParam(value="stockcode", defaultValue = "")String stockcode 
			                              ,@RequestParam(value="day", defaultValue = "")String day) throws Exception {
		final StockDailyDTO param = new StockDailyDTO(stockcode, day);
		final List<StockDailyDTO> stockDaily = stockDailyDAO.getStockDaily(param);
		return stockDaily;
	}

	@RequestMapping("/lastsd")
	public List<StockDailyDTO> lastsd(@RequestParam(value="stockcode", defaultValue = "")String stockcode 
			                              ,@RequestParam(value="day", defaultValue = "")String day) throws Exception {
		final StockDailyDTO param = new StockDailyDTO(stockcode, day);
		final List<StockDailyDTO> lastSD = stockDailyDAO.getLastSD(param);
		return lastSD;
	}

	@RequestMapping("/stockdays")
	public List<String> stockdays(@RequestParam(value="day", defaultValue = "")String day) throws Exception {
		final List<String> days = stockDailyDAO.getDays(day);
		return days;
	}

}
