package com.foodapp.servlet;

import java.io.IOException;
import java.util.HashMap;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/remove")
public class RemoveFromCartServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req,
            HttpServletResponse resp)
            throws ServletException, IOException {

        int menuId =
                Integer.parseInt(req.getParameter("id"));

        HttpSession session = req.getSession();

        HashMap<Integer,Integer> cart =
                (HashMap<Integer,Integer>) session.getAttribute("cart");

        if(cart != null) {
            cart.remove(menuId);
        }

        resp.sendRedirect("viewcart");
    }
}