package org.complainManaget.controller;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.dbcp2.BasicDataSource;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@WebServlet("/userSignUp")
public class UserSignUpServlet extends HttpServlet {
    Connection connection;
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("Post");
        String name = req.getParameter("name");
        String email = req.getParameter("username");
        String phoneNumber = req.getParameter("phoneNumber");
        String nic = req.getParameter("nic");
        String role = req.getParameter("role");
        String password = req.getParameter("password");

        ServletContext sc = getServletContext();
        BasicDataSource ds= (BasicDataSource) sc.getAttribute("dataSource");
        boolean isSaved = false;;
        try {
            connection=ds.getConnection();
            PreparedStatement pstm=connection.prepareStatement
                    ("INSERT INTO user (name,email,phone_number,nic,role,password) VALUES (?,?,?,?,?,?)");
            pstm.setString(1,name);
            pstm.setString(2,email);
            pstm.setString(3,phoneNumber);
            pstm.setString(4,nic);
            pstm.setString(5,role);
            pstm.setString(6,password);

            int executed = pstm.executeUpdate();

            if (executed > 0) {
                req.setAttribute("isSaved", "true");
                isSaved =  true;
            }
            connection.close();
        } catch (Exception e) {
            try {
                connection.close();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            e.printStackTrace();
        }
        System.out.println(isSaved);
        if (isSaved){
            req.getRequestDispatcher("/login.jsp").forward(req, resp);
        }else {
            req.getRequestDispatcher("/View/signUp.jsp").forward(req, resp);
        }

    }
}
