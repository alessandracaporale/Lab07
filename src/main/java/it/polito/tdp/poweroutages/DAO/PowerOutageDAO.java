package it.polito.tdp.poweroutages.DAO;

import java.sql.Connection;
import it.polito.tdp.poweroutages.model.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


public class PowerOutageDAO {
	
	public List<Nerc> getNercList() {

		String sql = "SELECT id, value FROM nerc";
		List<Nerc> nercList = new ArrayList<>();

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				Nerc n = new Nerc(res.getInt("id"), res.getString("value"));
				nercList.add(n);
			}

			conn.close();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		return nercList;
	}
	
	public List<PowerOutage> getPObyNerc (Nerc nerc) {
		String sql ="SELECT id, event_type_id, tag_id, area_id, nerc_id, responsible_id, customers_affected, date_event_began, date_event_finished, demand_loss "
				+ "FROM poweroutages "
				+ "WHERE nerc_id = ?";
		List<PowerOutage> list = new ArrayList<>();
		
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, nerc.getId());
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				PowerOutage po = new PowerOutage(rs.getInt("id"), rs.getInt("event_type_id"), rs.getInt("tag_id"), rs.getInt("area_id"), rs.getInt("nerc_id"), rs.getInt("responsible_id"), rs.getInt("customers_affected"), rs.getTimestamp("date_event_began").toLocalDateTime(), rs.getTimestamp("date_event_finished").toLocalDateTime(), rs.getInt("demand_loss"));
				list.add(po);
			}
			
			conn.close();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return list;
				
	}
	

}
