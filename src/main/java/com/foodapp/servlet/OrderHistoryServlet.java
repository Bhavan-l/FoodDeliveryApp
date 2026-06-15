package com.foodapp.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import com.foodapp.util.DBConnection;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/orderhistory")
public class OrderHistoryServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req,
            HttpServletResponse resp)
            throws ServletException, IOException {

        resp.setContentType("text/html");

        PrintWriter out = resp.getWriter();

        out.println("<html>");
        out.println("<head>");

        out.println("<title>Order History</title>");

        out.println("<style>");

        out.println("body{font-family:Arial,sans-serif;background:#f5f5f5;margin:0;padding:30px;}");

        out.println("h1{text-align:center;color:#fc8019;}");

        out.println(".container{display:flex;flex-wrap:wrap;justify-content:center;gap:20px;}");

        out.println(".card{background:white;width:320px;padding:20px;border-radius:12px;box-shadow:0 0 15px lightgray;}");

        out.println(".card h2{color:#fc8019;margin-bottom:10px;}");

        out.println(".status{color:green;font-weight:bold;}");

        out.println(".btn{background:#fc8019;color:white;padding:10px 15px;text-decoration:none;border-radius:5px;display:inline-block;margin-top:10px;}");

        out.println(".home{text-align:center;margin-top:30px;}");

        out.println("</style>");

        out.println("</head>");
        out.println("<body>");

        out.println("<h1>📦 Order History</h1>");

        out.println("<div class='container'>");

        try {

            Connection con = DBConnection.getConnection();

            Statement stmt = con.createStatement();

            ResultSet rs = stmt.executeQuery("SELECT * FROM orders ORDER BY orderId DESC");

            while(rs.next()) {

                out.println("<div class='card'>");

                out.println("<h2>Order #" +
                        rs.getInt("orderId") +
                        "</h2>");

                out.println("<p><b>User ID:</b> "
                        + rs.getInt("userId")
                        + "</p>");

                out.println("<p><b>Restaurant ID:</b> "
                        + rs.getInt("restaurantId")
                        + "</p>");

                out.println("<p><b>Total Amount:</b> ₹"
                        + rs.getDouble("totalAmount")
                        + "</p>");

                out.println("<p><b>Status:</b> <span class='status'>"
                        + rs.getString("status")
                        + "</span></p>");

                out.println("<a class='btn' href='orderdetails?orderId="
                        + rs.getInt("orderId")
                        + "'>");

                out.println("View Details");

                out.println("</a>");

                out.println("</div>");
            }

            rs.close();
            stmt.close();
            con.close();

        } catch(Exception e) {
            e.printStackTrace();
        }

        out.println("</div>");

        out.println("<div class='home'>");

        out.println("<a class='btn' href='home.html'>🏠 Home</a>");

        out.println("</div>");

        out.println("</body>");
        out.println("</html>");
    }
}