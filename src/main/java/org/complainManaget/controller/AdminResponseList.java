package org.complainManaget.controller;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.dbcp2.BasicDataSource;
import org.complainManaget.model.ResponseDto;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/adminresp")
public class AdminResponseList extends HttpServlet {
    Connection connection;
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletContext sc = getServletContext();
        BasicDataSource ds= (BasicDataSource) sc.getAttribute("dataSource");

        try {
            connection=ds.getConnection();
            Statement pstm=connection.createStatement();
            ResultSet data = pstm.executeQuery("Select * from response");
            List<ResponseDto> responses = new ArrayList<>();
            while(data.next()){
                ResponseDto response = new ResponseDto();
                response.setId(data.getString(1));
                response.setName(data.getString(2));
                response.setDate(data.getString(3));
                response.setTime(data.getString(4));
                response.setDescription(data.getString(5));
                response.setState(data.getString(6));
                responses.add(response);
            }
            req.setAttribute("data", responses);
            connection.close();
        } catch (Exception e) {
            try {
                connection.close();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            throw new RuntimeException(e);
        }
        req.getRequestDispatcher("./View/RequstListAdmin.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        String response = req.getParameter("response");
        String reply = req.getParameter("reply");

        ServletContext sc = getServletContext();
        BasicDataSource ds= (BasicDataSource) sc.getAttribute("dataSource");

        try {
            connection=ds.getConnection();
            PreparedStatement pstm=connection.prepareStatement
                    ("INSERT INTO reply (id,respnse_id,reply) VALUES (?,?,?)");
            pstm.setString(1,id);
            pstm.setString(2,response);
            pstm.setString(3,reply);

            int executed = pstm.executeUpdate();
            if (executed > 0) {
                resp.setStatus(HttpServletResponse.SC_OK);
            }
            connection.close();
        } catch (Exception e) {
            try {
                connection.close();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            throw new RuntimeException(e);
        }
        req.getRequestDispatcher("View/RequstListAdmin.jsp").forward(req, resp);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");

        ServletContext sc = getServletContext();
        BasicDataSource ds= (BasicDataSource) sc.getAttribute("dataSource");

        try {
            connection=ds.getConnection();
            PreparedStatement pstm=connection.prepareStatement
                    ("delete from reply where id=?");
            pstm.setString(1,id);
            int executed = pstm.executeUpdate();
            if (executed > 0) {
                resp.setStatus(HttpServletResponse.SC_OK);
            }else{
                resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
            connection.close();
        } catch (Exception e) {
            try {
                connection.close();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        String response = req.getParameter("response");
        String reply = req.getParameter("reply");

        ServletContext sc = getServletContext();
        BasicDataSource ds= (BasicDataSource) sc.getAttribute("dataSource");

        try {
            connection=ds.getConnection();
            PreparedStatement pstm=connection.prepareStatement
                    ("UPDATE reply SET response_id = ?, reply_text = ? WHERE id = ?");

            pstm.setString(1,response);
            pstm.setString(2,reply);
            pstm.setString(3,id);
            int executed = pstm.executeUpdate();
            if (executed > 0) {
                resp.setStatus(HttpServletResponse.SC_OK);
            }else{
                resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
            connection.close();
        } catch (Exception e) {
            try {
                connection.close();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            throw new RuntimeException(e);
        }
    }

}
