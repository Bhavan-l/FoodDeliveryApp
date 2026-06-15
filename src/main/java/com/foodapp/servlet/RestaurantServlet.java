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

@WebServlet("/restaurants")
public class RestaurantServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req,
            HttpServletResponse resp)
            throws ServletException, IOException {

        resp.setContentType("text/html");

        PrintWriter out = resp.getWriter();

        out.println("<html>");
        out.println("<head>");
        out.println("<title>Restaurants</title>");

        out.println("<style>");
        out.println("body{font-family:Arial;background:#f5f5f5;margin:0;padding:20px;}");
        out.println(".title{text-align:center;color:#fc8019;}");
        out.println(".container{display:flex;flex-wrap:wrap;justify-content:center;gap:25px;}");
        out.println(".card{width:320px;background:white;border-radius:15px;overflow:hidden;box-shadow:0 4px 10px rgba(0,0,0,0.2);}");
        out.println(".card img{width:100%;height:220px;object-fit:cover;}");
        out.println(".content{padding:15px;}");
        out.println(".btn{background:#fc8019;color:white;padding:10px 15px;text-decoration:none;border-radius:5px;}");
        out.println("</style>");

        out.println("</head>");
        out.println("<body>");

        out.println("<h1 class='title'>🍽️ Restaurants</h1>");
        out.println("<div class='container'>");

        try {

            Connection con = DBConnection.getConnection();

            Statement stmt = con.createStatement();

            ResultSet rs =
                    stmt.executeQuery("SELECT * FROM restaurant");

            while(rs.next()) {

                out.println("<div class='card'>");

                out.println("<img src='images/"
                        + rs.getString("imageUrl")
                        + "' alt='Restaurant Image'>");

                out.println("<div class='content'>");

                out.println("<h2>"
                        + rs.getString("name")
                        + "</h2>");

                out.println("<p><b>📍 Address:</b> "
                        + rs.getString("address")
                        + "</p>");

                out.println("<p><b>🍽️ Cuisine:</b> "
                        + rs.getString("cuisineType")
                        + "</p>");

                out.println("<p><b>⭐ Rating:</b> "
                        + rs.getDouble("rating")
                        + "</p>");

                out.println("<p><b>⏱️ ETA:</b> "
                        + rs.getString("ETA")
                        + "</p>");

                out.println("<a class='btn' href='restaurantmenu?restaurantId="
                        + rs.getInt("restaurantId")
                        + "'>View Menu</a>");

                out.println("</div>");
                out.println("</div>");
            }

            rs.close();
            stmt.close();
            con.close();

        } catch(Exception e) {
            e.printStackTrace();
        }

        out.println("</div>");
        out.println("</body>");
        out.println("</html>");
    }
}