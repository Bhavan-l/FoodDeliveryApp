package com.foodapp.servlet;

import java.io.IOException;
import java.util.HashMap;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/cart")
public class CartServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req,
            HttpServletResponse resp)
            throws ServletException, IOException {

        int menuId =
                Integer.parseInt(req.getParameter("menuId"));

        HttpSession session = req.getSession();

        HashMap<Integer,Integer> cart =
                (HashMap<Integer,Integer>) session.getAttribute("cart");

        if(cart == null) {
            cart = new HashMap<>();
        }

        if(cart.containsKey(menuId)) {
            cart.put(menuId, cart.get(menuId) + 1);
        } else {
            cart.put(menuId, 1);
        }

        session.setAttribute("cart", cart);

        resp.sendRedirect("viewcart");
    }
}