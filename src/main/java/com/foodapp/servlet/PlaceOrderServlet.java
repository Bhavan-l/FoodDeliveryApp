package com.foodapp.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import com.foodapp.util.DBConnection;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/placeorder")
public class PlaceOrderServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req,
            HttpServletResponse resp)
            throws ServletException, IOException {

        resp.setContentType("text/html");

        PrintWriter out =
                resp.getWriter();

        HttpSession session =
                req.getSession();

        HashMap<Integer,Integer> cart =
                (HashMap<Integer,Integer>)
                session.getAttribute("cart");

        if(cart == null ||
                cart.isEmpty()) {

            out.println(
                    "<h2>Cart Empty</h2>");

            return;
        }

        try {

            Connection con =
                    DBConnection.getConnection();

            double totalAmount = 0;

            for(Map.Entry<Integer,Integer> entry
                    : cart.entrySet()) {

                int menuId =
                        entry.getKey();

                int qty =
                        entry.getValue();

                PreparedStatement ps =
                        con.prepareStatement(
                                "SELECT * FROM menu WHERE menuId=?");

                ps.setInt(1, menuId);

                ResultSet rs =
                        ps.executeQuery();

                if(rs.next()) {

                    totalAmount +=
                            rs.getDouble("price")
                            * qty;
                }

                rs.close();
                ps.close();
            }

            String paymentType =
                    (String) session.getAttribute(
                            "paymentType");

            if(paymentType == null) {

                paymentType =
                        "Cash On Delivery";
            }

            String query =
                    "INSERT INTO orders(userId,restaurantId,totalAmount,address,paymentMethod,status) VALUES(?,?,?,?,?,?)";

            PreparedStatement orderStmt =
                    con.prepareStatement(
                            query,
                            Statement.RETURN_GENERATED_KEYS);

            orderStmt.setInt(1, 1);

            orderStmt.setInt(2,
                    (Integer)session.getAttribute(
                            "restaurantId"));

            orderStmt.setDouble(3,
                    totalAmount);

            orderStmt.setString(4,
                    "Bangalore");

            orderStmt.setString(5,
                    paymentType);

            orderStmt.setString(6,
                    "Placed");

            orderStmt.executeUpdate();

            ResultSet keys =
                    orderStmt.getGeneratedKeys();

            int orderId = 0;

            if(keys.next()) {

                orderId =
                        keys.getInt(1);
            }

            keys.close();

            for(Map.Entry<Integer,Integer> entry
                    : cart.entrySet()) {

                int menuId =
                        entry.getKey();

                int qty =
                        entry.getValue();

                PreparedStatement menuStmt =
                        con.prepareStatement(
                                "SELECT * FROM menu WHERE menuId=?");

                menuStmt.setInt(1,
                        menuId);

                ResultSet rs =
                        menuStmt.executeQuery();

                if(rs.next()) {

                    PreparedStatement itemStmt =
                            con.prepareStatement(
                                    "INSERT INTO orderitem(orderId,itemName,qty,totalPrice) VALUES(?,?,?,?)");

                    itemStmt.setInt(1,
                            orderId);

                    itemStmt.setString(2,
                            rs.getString("name"));

                    itemStmt.setInt(3,
                            qty);

                    itemStmt.setDouble(4,
                            rs.getDouble("price")
                            * qty);

                    itemStmt.executeUpdate();

                    itemStmt.close();
                }

                rs.close();
                menuStmt.close();
            }

            session.removeAttribute("cart");

            resp.sendRedirect(
                    "success.html");

            con.close();

        }

        catch(Exception e) {

            e.printStackTrace();
        }
    }
}