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

/* ���� �ܼ��� �ùķ��̼� ��
 * ��� ETF �����Ͱ� �����ϴ� 20180830 ���� ����
 * 20180830 ������ �������� ETF�� ���� �ݾ��� 1/n �� ����� �������� ETF ���� (�̸� Trading ���̺� Insert)
 * 20180830 ���� ��¥ �����ͼ� ����
 * �����((���Ͻð�-��������)/��������) �������� �� ETF �ü����� ��
 * + �� ���Ͽ� �켱 ���رݾ׸�ŭ �ŵ�
 * - �� ���Ͽ� ������(Deposit) �ܾ��� ������ ���رݾ׸�ŭ �ż�
 * ���رݾ� : �� �ݾ� / 7(ETF) * 10%(ETF��ŷ�����)
 * �� �ݾ� : �ð� ���� ETF �Ѿ� + �������ܾ�
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
