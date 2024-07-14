package com.alex96jvm.asiakl.servlets;
import java.io.IOException;
import java.sql.*;
import com.alex96jvm.asiakl.config.DatabaseConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import javax.sql.DataSource;

@WebServlet(name = "GameServlet", value = "/game")
public class GameServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String sql = "SELECT * FROM asiaklcontent " +
                "JOIN players ON asiaklcontent.id = players.asiaklcontentid " +
                "WHERE players.id = ?";
        String backgroundimage = "";
        String additionaleffect = "";
        String content = "";
        String aggression = "";
        String peacefulness = "";
        String rightpicture = "";

        HttpSession session = request.getSession(false);
        int userId = (int) session.getAttribute("userId");
        DataSource dataSource = DatabaseConfig.getDataSource();
        try (Connection conn = dataSource.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            try (ResultSet resultSet = stmt.executeQuery()) {
                if (resultSet.next()) {
                    backgroundimage = "images/"+resultSet.getString("backgroundimage");
                    additionaleffect = "images/"+resultSet.getString("additionaleffect");
                    content = resultSet.getString("content");
                    aggression = resultSet.getString("aggression");
                    peacefulness = resultSet.getString("peacefulness");
                    int id = resultSet.getInt("id");
                    rightpicture = (id == 1) ? "images/label.png" : "images/asiakl.png";
                }
            }
        } catch (SQLException e) {
            throw new ServletException("Database access error", e);
        }
        request.setAttribute("backgroundimage", backgroundimage);
        request.setAttribute("additionaleffect", additionaleffect);
        request.setAttribute("content", content);
        request.setAttribute("aggression", aggression);
        request.setAttribute("peacefulness", peacefulness);
        request.setAttribute("rightpicture", rightpicture);

        request.getRequestDispatcher("index.jsp").forward(request, response);
    }
}