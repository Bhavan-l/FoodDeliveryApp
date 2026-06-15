package com.foodapp.daoimpl;

import java.sql.Connection;

import com.foodapp.dao.UserDAO;
import com.foodapp.model.User;
import com.foodapp.util.DBConnection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserDAOImpl implements UserDAO {

	Connection con = DBConnection.getConnection();

	@Override
	public void addUser(User user) {

	    String INSERT_QUERY =
	            "INSERT INTO user(username,password,email,address,phoneNumber,role) VALUES(?,?,?,?,?,?)";

	    try {

	        PreparedStatement pstmt =
	                con.prepareStatement(INSERT_QUERY);

	        pstmt.setString(1, user.getUsername());
	        pstmt.setString(2, user.getPassword());
	        pstmt.setString(3, user.getEmail());
	        pstmt.setString(4, user.getAddress());
	        pstmt.setString(5, user.getPhoneNumber());
	        pstmt.setString(6, user.getRole());

	        pstmt.executeUpdate();

	        System.out.println("User Added Successfully");

	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	
	@Override
	public User getUser(String username) {

	    User user = null;

	    try {

	        String SELECT_QUERY =
	                "SELECT * FROM user WHERE username=?";

	        PreparedStatement pstmt =
	                con.prepareStatement(SELECT_QUERY);

	        pstmt.setString(1, username);

	        ResultSet rs = pstmt.executeQuery();

	        if(rs.next()) {

	            user = new User();

	            user.setUserId(rs.getInt("userId"));
	            user.setUsername(rs.getString("username"));
	            user.setPassword(rs.getString("password"));
	            user.setEmail(rs.getString("email"));
	            user.setAddress(rs.getString("address"));
	            user.setPhoneNumber(rs.getString("phoneNumber"));
	            user.setRole(rs.getString("role"));
	        }

	    } catch(Exception e) {
	        e.printStackTrace();
	    }

	    return user;
	}

	@Override
	public User getUserById(int userId) {

		return null;
	}

	@Override
	public User getUserByEmail(String email) {

	    User user = null;

	    try {

	        String SELECT_QUERY =
	                "SELECT * FROM user WHERE email=?";

	        PreparedStatement pstmt =
	                con.prepareStatement(SELECT_QUERY);

	        pstmt.setString(1, email);

	        ResultSet rs = pstmt.executeQuery();

	        if(rs.next()) {

	            user = new User();

	            user.setUserId(rs.getInt("userId"));
	            user.setUsername(rs.getString("username"));
	            user.setPassword(rs.getString("password"));
	            user.setEmail(rs.getString("email"));
	            user.setAddress(rs.getString("address"));
	            user.setPhoneNumber(rs.getString("phoneNumber"));
	            user.setRole(rs.getString("role"));
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return user;
	}

	@Override
	public void updateUser(User user) {

	}

	@Override
	public void deleteUser(int userId) {

	}
}