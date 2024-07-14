package com.alex96jvm.asiakl.servlets;
import java.io.IOException;
import java.sql.*;
import com.alex96jvm.asiakl.config.DatabaseConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import javax.sql.DataSource;

@WebServlet(name = "ReturnServlet", value = "/return")
public class ReturnServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        DataSource dataSource = DatabaseConfig.getDataSource();
        try (Connection conn = dataSource.getConnection()) {
            try (Statement stmt = conn.createStatement()) {
                stmt.executeUpdate("UPDATE players SET asiaklcontentid=1 WHERE id=1;"
                );
            }
        } catch (SQLException e) {
            throw new ServletException("Database access error", e);
        }
        response.sendRedirect(request.getContextPath() + "/game");
    }
}