package com.foodapp.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

import com.foodapp.util.DBConnection;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/viewcart")
public class ViewCartServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req,
            HttpServletResponse resp)
            throws ServletException, IOException {

        resp.setContentType("text/html");

        HttpSession session = req.getSession();

        HashMap<Integer,Integer> cart =
                (HashMap<Integer,Integer>) session.getAttribute("cart");

        PrintWriter out = resp.getWriter();

        out.println("<html>");
        out.println("<head>");
        out.println("<title>My Cart</title>");

        out.println("<style>");

        out.println("body{font-family:Arial;background:#f5f5f5;margin:0;padding:20px;}");

        out.println("h1{text-align:center;color:#fc8019;}");

        out.println("table{width:90%;margin:auto;background:white;border-collapse:collapse;box-shadow:0 0 10px lightgray;}");

        out.println("th{background:#fc8019;color:white;padding:15px;}");

        out.println("td{padding:15px;text-align:center;border-bottom:1px solid #ddd;}");

        out.println(".btn{background:#fc8019;color:white;padding:12px 20px;text-decoration:none;border-radius:8px;font-weight:bold;}");

        out.println(".btn:hover{background:#e46f00;}");

        out.println(".center{text-align:center;margin-top:20px;}");

        out.println("</style>");

        out.println("</head>");
        out.println("<body>");

        out.println("<h1>🛒 My Cart</h1>");

        if(cart == null || cart.isEmpty()) {

            out.println("<h2 style='text-align:center;'>Cart is Empty</h2>");

            out.println("<div class='center'>");
            out.println("<a href='restaurants' class='btn'>🍽️ Browse Restaurants</a>");
            out.println("</div>");

        } else {

            double total = 0;

            out.println("<table>");

            out.println("<tr>");
            out.println("<th>Food Name</th>");
            out.println("<th>Price</th>");
            out.println("<th>Quantity</th>");
            out.println("<th>Subtotal</th>");
            out.println("<th>Action</th>");
            out.println("</tr>");

            try {

                Connection con = DBConnection.getConnection();

                for(Map.Entry<Integer,Integer> entry : cart.entrySet()) {

                    int menuId = entry.getKey();
                    int qty = entry.getValue();

                    PreparedStatement pstmt =
                            con.prepareStatement(
                            "SELECT * FROM menu WHERE menuId=?");

                    pstmt.setInt(1, menuId);

                    ResultSet rs = pstmt.executeQuery();

                    if(rs.next()) {

                        double price =
                                rs.getDouble("price");

                        double subtotal =
                                price * qty;

                        total += subtotal;

                        out.println("<tr>");

                        out.println("<td>"
                                + rs.getString("name")
                                + "</td>");

                        out.println("<td>₹"
                                + price
                                + "</td>");

                        out.println("<td>");

                        out.println("<a href='updatequantity?id="
                                + menuId
                                + "&action=minus'>➖</a>");

                        out.println(" "
                                + qty
                                + " ");

                        out.println("<a href='updatequantity?id="
                                + menuId
                                + "&action=add'>➕</a>");

                        out.println("</td>");

                        out.println("<td>₹"
                                + subtotal
                                + "</td>");

                        out.println("<td>");

                        out.println("<a href='remove?id="
                                + menuId
                                + "'>❌ Remove</a>");

                        out.println("</td>");

                        out.println("</tr>");
                    }

                    rs.close();
                    pstmt.close();
                }

                con.close();

            } catch(Exception e) {

                e.printStackTrace();
            }

            out.println("</table>");

            out.println("<h2 style='text-align:center;color:#333;'>Total Amount : ₹"
                    + total
                    + "</h2>");

            out.println("<div class='center'>");

            out.println("<a href='payment.html' class='btn'>");
            out.println("💳 Proceed To Checkout");
            out.println("</a>");

            out.println("</div>");

            out.println("<div class='center' style='margin-top:20px;'>");

            out.println("<a href='clearcart' class='btn'>");
            out.println("🗑️ Clear Cart");
            out.println("</a>");

            out.println("</div>");
        }

        out.println("<div class='center' style='margin-top:30px;'>");

        out.println("<a href='restaurants' class='btn'>");
        out.println("🍔 Continue Shopping");
        out.println("</a>");

        out.println("</div>");

        out.println("</body>");
        out.println("</html>");
    }
}