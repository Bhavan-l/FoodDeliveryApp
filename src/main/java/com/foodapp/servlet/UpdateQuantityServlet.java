package com.foodapp.servlet;

import java.io.IOException;
import java.util.HashMap;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/updatequantity")
public class UpdateQuantityServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req,
            HttpServletResponse resp)
            throws ServletException, IOException {

        int menuId =
                Integer.parseInt(req.getParameter("id"));

        String action =
                req.getParameter("action");

        HttpSession session = req.getSession();

        HashMap<Integer,Integer> cart =
                (HashMap<Integer,Integer>) session.getAttribute("cart");

        if(cart != null) {

            int qty = cart.getOrDefault(menuId, 0);

            if("add".equals(action)) {
                cart.put(menuId, qty + 1);
            }
            else if("minus".equals(action)) {

                if(qty > 1) {
                    cart.put(menuId, qty - 1);
                }
                else {
                    cart.remove(menuId);
                }
            }
        }

        resp.sendRedirect("viewcart");
    }
}