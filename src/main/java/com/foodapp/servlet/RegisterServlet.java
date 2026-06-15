package com.foodapp.servlet;

import java.io.IOException;

import com.foodapp.daoimpl.UserDAOImpl;
import com.foodapp.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req,
            HttpServletResponse resp)
            throws ServletException, IOException {

        String username = req.getParameter("username");
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String address = req.getParameter("address");

        // IMPORTANT FIX
        String phone = req.getParameter("phoneNumber");

        System.out.println("Username = " + username);
        System.out.println("Email = " + email);
        System.out.println("Phone = " + phone);

        User user = new User();

        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(password);
        user.setAddress(address);
        user.setPhoneNumber(phone);
        user.setRole("customer");

        UserDAOImpl userDAOImpl = new UserDAOImpl();

        userDAOImpl.addUser(user);

        resp.sendRedirect("login.html");
    }
}