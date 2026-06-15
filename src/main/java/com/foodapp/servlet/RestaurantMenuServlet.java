package com.foodapp.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.foodapp.util.DBConnection;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/restaurantmenu")
public class RestaurantMenuServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req,
            HttpServletResponse resp)
            throws ServletException, IOException {

        resp.setContentType("text/html");

        PrintWriter out = resp.getWriter();

        int restaurantId =
                Integer.parseInt(req.getParameter("restaurantId"));

        HttpSession session = req.getSession();
        session.setAttribute("restaurantId", restaurantId);

        out.println("<html>");
        out.println("<head>");
        out.println("<title>Menu</title>");

        out.println("<style>");
        out.println("body{font-family:Arial;background:#f5f5f5;margin:0;padding:20px;}");
        out.println("h1{text-align:center;color:#fc8019;}");
        out.println(".container{display:flex;flex-wrap:wrap;justify-content:center;gap:20px;}");
        out.println(".card{width:300px;background:white;border-radius:15px;overflow:hidden;box-shadow:0 4px 10px rgba(0,0,0,0.2);}");
        out.println(".card img{width:100%;height:220px;object-fit:cover;}");
        out.println(".content{padding:15px;}");
        out.println(".price{font-size:20px;font-weight:bold;color:#333;}");
        out.println(".btn{background:#fc8019;color:white;border:none;padding:10px 15px;border-radius:6px;cursor:pointer;font-weight:bold;}");
        out.println(".btn:hover{background:#e66a00;}");
        out.println("</style>");

        out.println("</head>");
        out.println("<body>");

        try {

            Connection con = DBConnection.getConnection();

            PreparedStatement ps =
                    con.prepareStatement(
                    "SELECT * FROM restaurant WHERE restaurantId=?");

            ps.setInt(1, restaurantId);

            ResultSet rsRestaurant = ps.executeQuery();

            if(rsRestaurant.next()) {

                out.println("<h1>"
                        + rsRestaurant.getString("name")
                        + "</h1>");

                out.println("<center><h3>"
                        + rsRestaurant.getString("cuisineType")
                        + "</h3></center>");
            }

            out.println("<div class='container'>");

            PreparedStatement pstmt =
                    con.prepareStatement(
                    "SELECT * FROM menu WHERE restaurantId=?");

            pstmt.setInt(1, restaurantId);

            ResultSet rs = pstmt.executeQuery();

            while(rs.next()) {

                out.println("<div class='card'>");

                out.println("<img src='images/"
                        + rs.getString("imageUrl")
                        + "' alt='Food Image'>");

                out.println("<div class='content'>");

                out.println("<h2>"
                        + rs.getString("name")
                        + "</h2>");

                out.println("<p>"
                        + rs.getString("description")
                        + "</p>");

                out.println("<p class='price'>₹"
                        + rs.getDouble("price")
                        + "</p>");

                out.println("<form action='cart' method='post'>");

                out.println("<input type='hidden' name='menuId' value='"
                        + rs.getInt("menuId")
                        + "'>");

                out.println("<input type='submit' class='btn' value='Add To Cart'>");

                out.println("</form>");

                out.println("</div>");
                out.println("</div>");
            }

            out.println("</div>");

            rs.close();
            pstmt.close();
            ps.close();
            con.close();

        } catch(Exception e) {
            e.printStackTrace();
        }

        out.println("<div style='text-align:center;margin:40px;'>");
        out.println("<a href='restaurants' "
                + "style='background:#fc8019;"
                + "color:white;"
                + "padding:15px 30px;"
                + "text-decoration:none;"
                + "border-radius:30px;"
                + "font-size:18px;"
                + "font-weight:bold;"
                + "display:inline-block;'>");
        out.println("🏠 Back To Restaurants");
        out.println("</a>");
        out.println("</div>");

        out.println("</body>");
        out.println("</html>");
    }
}