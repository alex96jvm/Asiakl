package com.alex96jvm.asiakl.servlets;

import java.io.*;
import java.sql.SQLException;
import com.alex96jvm.asiakl.dao.UserDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet(name = "LoginServlet", value = "/login")
public class LoginServlet extends HttpServlet {
    private UserDAO userDAO;
    @Override
    public void init() throws ServletException {
        this.userDAO = new UserDAO();
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        try {
            int userId = userDAO.authenticate(username, password);
            if (userId != -1) {
                HttpSession session = request.getSession();
                session.setAttribute("userId", userId);
                response.sendRedirect(request.getContextPath() + "/game"); // Перенаправление на защищенный сервлет
            } else {
                request.setAttribute("error", "Неверный никнейм или пароль");
                request.getRequestDispatcher("index.jsp").forward(request, response);
            }
        } catch (SQLException e) {
            throw new ServletException("Database access error", e);
        }
    }

}