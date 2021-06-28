package it.polito.tdp.yelp.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import it.polito.tdp.yelp.model.Adiacenza;
import it.polito.tdp.yelp.model.Business;
import it.polito.tdp.yelp.model.Review;
import it.polito.tdp.yelp.model.User;

public class YelpDao {

	public void getAllBusiness(Map<String,Business> idMap){
		String sql = "SELECT * FROM Business";
		List<Business> result = new ArrayList<Business>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				Business business = new Business(res.getString("business_id"), 
						res.getString("full_address"),
						res.getString("active"),
						res.getString("categories"),
						res.getString("city"),
						res.getInt("review_count"),
						res.getString("business_name"),
						res.getString("neighborhoods"),
						res.getDouble("latitude"),
						res.getDouble("longitude"),
						res.getString("state"),
						res.getDouble("stars"));
				result.add(business);
				idMap.put(res.getString("business_id"),business);
			}
			res.close();
			st.close();
			conn.close();
			return;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return ;
		}
	}
	
	public List<Review> getAllReviews(){
		String sql = "SELECT * FROM Reviews";
		List<Review> result = new ArrayList<Review>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				Review review = new Review(res.getString("review_id"), 
						res.getString("business_id"),
						res.getString("user_id"),
						res.getDouble("stars"),
						res.getDate("review_date").toLocalDate(),
						res.getInt("votes_funny"),
						res.getInt("votes_useful"),
						res.getInt("votes_cool"),
						res.getString("review_text"));
				result.add(review);
			}
			res.close();
			st.close();
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<User> getAllUsers(){
		String sql = "SELECT * FROM Users";
		List<User> result = new ArrayList<User>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				User user = new User(res.getString("user_id"),
						res.getInt("votes_funny"),
						res.getInt("votes_useful"),
						res.getInt("votes_cool"),
						res.getString("name"),
						res.getDouble("average_stars"),
						res.getInt("review_count"));
				
				result.add(user);
			}
			res.close();
			st.close();
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	public List<String> getCitta(){
		String sql = "SELECT DISTINCT(business.city) AS cit "
				+ "FROM business "
				+ "ORDER BY business.city ASC ";
			
		List<String> result = new ArrayList<String>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				
				result.add(res.getString("cit"));
			}
			res.close();
			st.close();
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		
	}
	
	
}
	public List<Business> getVertici(Map<String,Business> idMap,Integer anno,String citta){
		
		String sql = "SELECT DISTINCT(r.business_id) AS id "
				+ "FROM reviews r,business b "
				+ "WHERE YEAR(r.review_date)=? AND b.business_id=r.business_id AND b.city=? ";
			
		List<Business> result = new ArrayList<>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, anno);
			st.setString(2, citta);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				
				result.add(idMap.get(res.getString("id")));
			}
			res.close();
			st.close();
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		
	}
		
	}
	public List<Adiacenza> getArchi(Map<String,Business> idMap, Integer anno,String citta){
		String sql = "SELECT r1.business_id AS b1,r2.business_id AS b2, (AVG(r1.stars)-AVG(r2.stars)) AS peso "
				+ "FROM reviews r1,reviews r2, business b1, business b2 "
				+ "WHERE r1.business_id>r2.business_id AND YEAR(r1.review_date) =? AND YEAR(r1.review_date)=YEAR(r2.review_date) "
				+ "AND b1.business_id=r1.business_id AND b2.business_id=r2.business_id AND b1.city=? AND b2.city=b1.city "
				+ "GROUP BY r1.business_id, r2.business_id ";
			
		List<Adiacenza> result = new ArrayList<>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, anno);
			st.setString(2, citta);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				Double peso=res.getDouble("peso");
				if(peso>0) {
					result.add(new Adiacenza(idMap.get(res.getString("b2")),idMap.get(res.getString("b1")), peso));
				}
				if(peso<0) {
					Double tmp=-1*peso;
					result.add(new Adiacenza(idMap.get(res.getString("b1")),idMap.get(res.getString("b2")), tmp));
				}
			}
			res.close();
			st.close();
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		
	}
}
}
