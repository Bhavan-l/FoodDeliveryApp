package com.foodapp.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.foodapp.model.User;
import com.foodapp.util.DBConnection;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/profile")
public class ProfileServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req,
            HttpServletResponse resp)
            throws ServletException, IOException {

        resp.setContentType("text/html");

        PrintWriter out = resp.getWriter();

        HttpSession session = req.getSession();

        User loggedUser =
                (User) session.getAttribute("loggedUser");

        if(loggedUser == null) {

            resp.sendRedirect("login.html");
            return;
        }

        try {

            Connection con = DBConnection.getConnection();

            String query =
                    "SELECT * FROM user WHERE userId=?";

            PreparedStatement pstmt =
                    con.prepareStatement(query);

            pstmt.setInt(1, loggedUser.getUserId());

            ResultSet rs = pstmt.executeQuery();

            out.println("<html>");
            out.println("<head>");

            out.println("<title>My Profile</title>");

            out.println("<style>");

            out.println("body{");
            out.println("font-family:Arial;");
            out.println("background:#f8f8f8;");
            out.println("margin:0;");
            out.println("}");

            out.println(".header{");
            out.println("background:#fc8019;");
            out.println("color:white;");
            out.println("padding:20px;");
            out.println("text-align:center;");
            out.println("}");

            out.println(".card{");
            out.println("width:500px;");
            out.println("margin:40px auto;");
            out.println("background:white;");
            out.println("padding:30px;");
            out.println("border-radius:15px;");
            out.println("box-shadow:0 4px 15px rgba(0,0,0,0.2);");
            out.println("}");

            out.println(".row{");
            out.println("margin-bottom:15px;");
            out.println("font-size:18px;");
            out.println("}");

            out.println(".label{");
            out.println("font-weight:bold;");
            out.println("color:#fc8019;");
            out.println("}");

            out.println(".btn{");
            out.println("display:inline-block;");
            out.println("padding:12px 20px;");
            out.println("background:#fc8019;");
            out.println("color:white;");
            out.println("text-decoration:none;");
            out.println("border-radius:8px;");
            out.println("margin-top:20px;");
            out.println("}");

            out.println("</style>");

            out.println("</head>");
            out.println("<body>");

            out.println("<div class='header'>");
            out.println("<h1>👤 My Profile</h1>");
            out.println("</div>");

            if(rs.next()) {

                out.println("<div class='card'>");

                out.println("<div class='row'><span class='label'>User ID:</span> "
                        + rs.getInt("userId")
                        + "</div>");

                out.println("<div class='row'><span class='label'>Username:</span> "
                        + rs.getString("username")
                        + "</div>");

                out.println("<div class='row'><span class='label'>Email:</span> "
                        + rs.getString("email")
                        + "</div>");

                out.println("<div class='row'><span class='label'>Address:</span> "
                        + rs.getString("address")
                        + "</div>");

                out.println("<div class='row'><span class='label'>Phone:</span> "
                        + rs.getString("phoneNumber")
                        + "</div>");

                out.println("<div class='row'><span class='label'>Role:</span> "
                        + rs.getString("role")
                        + "</div>");

                out.println("<a class='btn' href='home.html'>🏠 Home Page</a>");

                out.println("</div>");
            }

            rs.close();
            pstmt.close();
            con.close();

            out.println("</body>");
            out.println("</html>");

        } catch(Exception e) {

            e.printStackTrace();
        }
    }
}