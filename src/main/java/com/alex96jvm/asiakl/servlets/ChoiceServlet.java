package com.alex96jvm.asiakl.servlets;

import com.alex96jvm.asiakl.config.DatabaseConfig;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@WebServlet(urlPatterns = {"/choice"})
public class ChoiceServlet extends HttpServlet {

    private static final String UPDATE_AGGRESSION_SQL = "UPDATE players "+
                    "SET asiaklcontentid = asiaklcontent.aggressionid "+
                    "FROM asiaklcontent WHERE asiaklcontent.id = players.asiaklcontentid "+
                    "AND players.id = ?";

    private static final String UPDATE_PEACEFULNESS_SQL = "UPDATE players "+
                    "SET asiaklcontentid = asiaklcontent.peacefulnessid "+
                    "FROM asiaklcontent WHERE asiaklcontent.id = players.asiaklcontentid "+
                    "AND players.id = ?";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        int userId = (int) session.getAttribute("userId");
        String action = request.getParameter("action");
        String sql;

        if ("aggression".equals(action)) {
            sql = UPDATE_AGGRESSION_SQL;
        } else if ("peacefulness".equals(action)) {
            sql = UPDATE_PEACEFULNESS_SQL;
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid action parameter");
            return;
        }

        DataSource dataSource = DatabaseConfig.getDataSource();
        try (Connection conn = dataSource.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setInt(1, userId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new ServletException("Database access error", e);
        }
        response.sendRedirect(request.getContextPath() + "/game");
    }
}