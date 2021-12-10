package com.ixon.stock.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ixon.stock.dao.SimpleModelDAO;
import com.ixon.stock.dao.StockDailyDAO;
import com.ixon.stock.dto.StockDailyDTO;

import lombok.Getter;
import lombok.Setter;

/* 가장 단순한 시뮬레이션 모델
 * 모든 ETF 데이터가 존재하는 20180830 부터 시작
 * 20180830 종가를 기준으로 ETF별 투가 금액을 1/n 에 가까운 수량으로 ETF 구입 (미리 Trading 테이블에 Insert)
 * 20180830 이후 날짜 가져와서 루핑
 * 등락률((당일시가-전일종가)/전일종가) 높은순의 일 ETF 시세정보 겟
 * + 에 대하여 우선 기준금액만큼 매도
 * - 의 대하여 예수금(Deposit) 잔액이 있으면 기준금액만큼 매수
 * 기준금액 : 총 금액 / 7(ETF) * 10%(ETF당거래비율)
 * 총 금액 : 시가 기준 ETF 총액 + 예수금잔액
 */
@Getter
@Setter
@Service
public class SimpleModel {

	final private String model = "BEGIN001";

	private String day;
	private int asofvol;
	private List<StockDailyDTO> stockDaily;
	private List<StockDailyDTO> lastStockDaily;
	private List<Map<String, Object>> stockMap = new ArrayList<>();
	
	@Autowired
	private StockDailyDAO stockDailyDAO;
	@Autowired
	private SimpleModelDAO simpleModelDAO;
	
	public List<StockDailyDTO> getLastSD(String day) throws Exception {
		StockDailyDTO param = new StockDailyDTO("", day);
		List<StockDailyDTO> lastSD = stockDailyDAO.getLastSD(param);
		return lastSD;
	}
	
	public List<HashMap<String, Object>> getStockDaily(String day) throws Exception {
		List<HashMap<String, Object>> lastSD = simpleModelDAO.getStockDaily(day);
		return lastSD;
	}
	
	public String checkUpDown(String stockcode, String day) throws Exception {
		Map<String, Object> map = new HashMap<>();
		
		StockDailyDTO param = new StockDailyDTO("", day);
		this.stockDaily = stockDailyDAO.getStockDaily(param);
		this.lastStockDaily = stockDailyDAO.getLastSD(param);

		for(int i = 0; i < stockDaily.size(); i++) {
			map.put("stockcode", stockDaily.get(i).getStockcode());
			map.put("day", stockDaily.get(i).getDay());
			map.put("start", stockDaily.get(i).getStart());
			map.put("high", stockDaily.get(i).getHigh());
			map.put("low", stockDaily.get(i).getLow());
			map.put("last", stockDaily.get(i).getLast());
			map.put("vol", stockDaily.get(i).getVol());
			if (stockDaily.get(i).getStockcode() == lastStockDaily.get(i).getStockcode()) {
				map.put("laststockcode", lastStockDaily.get(i).getStockcode());
				map.put("lastday", lastStockDaily.get(i).getDay());
				map.put("laststart", lastStockDaily.get(i).getStart());
				map.put("lasthigh", lastStockDaily.get(i).getHigh());
				map.put("lastlow", lastStockDaily.get(i).getLow());
				map.put("lastlast", lastStockDaily.get(i).getLast());
				map.put("lastvol", lastStockDaily.get(i).getVol());
			}
			this.stockMap.add(map);
		}
		return stockMap.get(0).get("laststockcode").toString();
	}

}
