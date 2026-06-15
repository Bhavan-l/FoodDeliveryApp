package com.foodapp.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import com.foodapp.util.DBConnection;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/menu")
public class MenuServlet extends HttpServlet {

    protected void doGet(HttpServletRequest req,
            HttpServletResponse resp)
            throws IOException {

        resp.setContentType("text/html");

        PrintWriter out = resp.getWriter();

        out.println("<table border='1'>");

        out.println("<tr>");
        out.println("<th>Food Name</th>");
        out.println("<th>Description</th>");
        out.println("<th>Price</th>");
        out.println("<th>Action</th>");
        out.println("</tr>");

        try {

            Connection con = DBConnection.getConnection();

            Statement stmt = con.createStatement();

            ResultSet rs = stmt.executeQuery("SELECT * FROM menu");

            while(rs.next()) {

                out.println("<tr>");

                out.println("<td>" + rs.getString("name") + "</td>");

                out.println("<td>" + rs.getString("description") + "</td>");

                out.println("<td>" + rs.getDouble("price") + "</td>");

                out.println("<td>");
                out.println("<form action='cart' method='post'>");
                out.println("<input type='hidden' name='menuId' value='" + rs.getInt("menuId") + "'>");
                out.println("<input type='submit' value='Add to Cart'>");
                out.println("</form>");
                out.println("</td>");

                out.println("</tr>");
            }

            out.println("</table>");

        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}