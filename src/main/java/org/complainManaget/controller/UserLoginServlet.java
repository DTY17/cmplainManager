package org.complainManaget.controller;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.dbcp2.BasicDataSource;

import java.io.IOException;
import java.sql.*;

@WebServlet("/userLogin")
public class UserLoginServlet extends HttpServlet {
    Connection connection;
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("username");
        String password = req.getParameter("password");
        String user = "";
        String role = "";

        ServletContext sc = getServletContext();
        BasicDataSource ds = (BasicDataSource) sc.getAttribute("dataSource");
        Boolean isCorrect = false;
        Connection connection = null;

        try {
            connection = ds.getConnection();
            PreparedStatement pstm = connection.prepareStatement("SELECT password FROM user WHERE email=?");
            pstm.setString(1, email);
            ResultSet rs = pstm.executeQuery();

            if (rs.next()) {
                String storedPassword = rs.getString("password");
                if (password.equals(storedPassword)) {
                    isCorrect = true;
                    PreparedStatement pstm2 = connection.prepareStatement("SELECT name,role FROM user WHERE email=?");
                    pstm2.setString(1, email);
                    ResultSet rs2 = pstm2.executeQuery();
                    while (rs2.next()){
                        user = rs2.getString("name");
                        role = rs2.getString("role");
                    }

                }
            } else {
                resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            }
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            throw new RuntimeException(e);
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                }
            }
        }
        System.out.println(email);

        if (isCorrect) {
            req.setAttribute("data", user);
            req.setAttribute("role", role);
        } else {
            req.setAttribute("error", "Invalid email or password");
        }
        req.getRequestDispatcher("/login.jsp").forward(req, resp);

    }
}
