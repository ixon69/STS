package kr.or.cmcnu.bucbot.jdbc.client.model;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class ClientRowMapper implements RowMapper
{
	public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
		Client client = new Client();
		client.setCntctel(rs.getString("CNTCTEL"));
		client.setDeptname(rs.getString("DEPTNAME"));
		return client;
	}
	
}
