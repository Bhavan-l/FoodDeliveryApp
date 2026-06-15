package com.foodapp.servlet;

import java.io.IOException;

import com.foodapp.daoimpl.UserDAOImpl;
import com.foodapp.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req,
            HttpServletResponse resp)
            throws ServletException, IOException {

        String email = req.getParameter("email");
        String password = req.getParameter("password");

        UserDAOImpl dao = new UserDAOImpl();

        User user = dao.getUserByEmail(email);

        if(user != null &&
                user.getPassword().equals(password)) {

            HttpSession session = req.getSession();

            session.setAttribute("loggedUser", user);

            resp.sendRedirect("home.html");

        } else {

            resp.setContentType("text/html");

            resp.getWriter().println(
                    "<h2 style='color:red;text-align:center;'>Invalid Email Or Password</h2>");
        }
    }
}