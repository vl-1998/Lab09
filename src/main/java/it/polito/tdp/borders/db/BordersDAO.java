package it.polito.tdp.borders.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import it.polito.tdp.borders.model.Border;
import it.polito.tdp.borders.model.Country;

public class BordersDAO {

	public void loadAllCountries(Map <Integer, Country> idMap) {

		String sql = "SELECT ccode, StateAbb, StateNme FROM country ORDER BY StateAbb";
		
		
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				if (!idMap.containsKey(rs.getInt("ccode"))){
					Country country = new Country (rs.getString("StateAbb"), rs.getInt("ccode"), rs.getString("StateNme"));
					idMap.put(country.getId(), country);
				}
				
			}	
			conn.close();

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
	}

	public List<Border> getCountryPairs(int anno, Map <Integer, Country> idMap) {

		String sql = "SELECT DISTINCT state1no, state2no, year " + 
				"FROM contiguity " + 
				"WHERE conttype=1 AND year<=? " + 
				"AND state1no>state2no";
		List <Border> confini = new ArrayList <Border>();
		
		try {
			
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, anno);
			ResultSet rs = st.executeQuery();
			
			while (rs.next()) {
				Border confine = new Border (idMap.get(rs.getInt("state1no")), idMap.get(rs.getInt("state2no")), rs.getInt("year"));
				confini.add(confine);
			}
			conn.close();
			
			
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
		return confini;
	}
	
	public List<Country> getCountry(int anno, Map <Integer, Country> idMap) {

		String sql = "SELECT DISTINCT state1no, state2no, year " + 
				"FROM contiguity " + 
				"WHERE conttype=1 AND year<=? " + 
				"AND state1no>state2no";
		List <Country> paesi = new ArrayList <Country>();
		
		try {
			
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, anno);
			ResultSet rs = st.executeQuery();
			
			while (rs.next()) {
				Country paese1 = new Country (idMap.get(rs.getInt("state1no")).getAbbr(),rs.getInt("state1no"), idMap.get(rs.getInt("state1no")).getName());
				Country paese2 = new Country (idMap.get(rs.getInt("state2no")).getAbbr(),rs.getInt("state2no"), idMap.get(rs.getInt("state2no")).getName());
				
				paesi.add(paese1);
				paesi.add(paese2);
			}
			conn.close();
			
			
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
		return paesi;
	}
}
