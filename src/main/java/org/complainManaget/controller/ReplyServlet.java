package org.complainManaget.controller;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.dbcp2.BasicDataSource;
import org.complainManaget.model.ReplyDto;
import org.complainManaget.model.ResponseDto;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/reply")
public class ReplyServlet extends HttpServlet {
    Connection connection;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletContext sc = getServletContext();
        BasicDataSource ds= (BasicDataSource) sc.getAttribute("dataSource");

        try {
            connection=ds.getConnection();
            Statement pstm=connection.createStatement();
            ResultSet data = pstm.executeQuery("Select * from reply");
            List<ReplyDto> ReplyDtos = new ArrayList<>();
            while(data.next()){
                ReplyDto ReplyDto = new ReplyDto();
                ReplyDto.setId(data.getInt(1));
                ReplyDto.setResponse(data.getString(2));
                ReplyDto.setReply(data.getString(3));
                ReplyDtos.add(ReplyDto);
            }
            req.setAttribute("data", ReplyDtos);
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
        req.getRequestDispatcher("./View/SendResponseManager.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getParameter("_method");
        if(path.equals("Post")){
            saveData(req, resp);
        } else if(path.equals("Put")){
            updateData(req, resp);
        } else if(path.equals("Delete")){
            deletData(req, resp);
        }

    }
    public void deletData(HttpServletRequest req,HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("Delete Data User Reply");
        String id = req.getParameter("id");
        System.out.println( id + " : ID");
        ServletContext sc = getServletContext();
        BasicDataSource ds= (BasicDataSource) sc.getAttribute("dataSource");

        try {
            connection=ds.getConnection();

            PreparedStatement pstm = connection.prepareStatement("DELETE FROM reply WHERE response_id = ?");
            pstm.setString(1, id);
            int executed = pstm.executeUpdate();
            if (executed > 0) {
                resp.setStatus(HttpServletResponse.SC_OK);
            }else{
                resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }

            PreparedStatement pstm2 = connection.prepareStatement ("DELETE FROM response WHERE id = ?");
            pstm2.setString(1,id);
            int executed2 = pstm2.executeUpdate();
            if (executed2 > 0) {
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
        req.getRequestDispatcher("View/RequstListAdmin.jsp").forward(req, resp);
    }
        public void saveData(HttpServletRequest req,HttpServletResponse resp) throws ServletException, IOException {
        String response = req.getParameter("id");
            String name = req.getParameter("name");
        String reply = req.getParameter("reply");

        ServletContext sc = getServletContext();
        BasicDataSource ds= (BasicDataSource) sc.getAttribute("dataSource");

        try {
            connection=ds.getConnection();
            PreparedStatement pstm=connection.prepareStatement
                    ("INSERT INTO reply (response_id,reply_text) VALUES (?,?)");
            pstm.setString(1,response);
            pstm.setString(2,reply);

            int executed = pstm.executeUpdate();
            if (executed > 0) {
                resp.setStatus(HttpServletResponse.SC_OK);
            }

            PreparedStatement pstm2 = connection.prepareStatement
                    ("UPDATE response SET state = ? WHERE id = ?");

            pstm2.setString(1,"Reply");
            pstm2.setString(2,response);
            int executed2 = pstm2.executeUpdate();
            if (executed2 > 0) {
                resp.setStatus(HttpServletResponse.SC_OK);
            }
            connection.close();
            RequestListServlet.getMessageSend(getEmail(name),reply,response);
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
    public void updateData(HttpServletRequest req,HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("Update Data User");
        String id = req.getParameter("id");
        String response = req.getParameter("message");
        System.out.println(id + ","+response+"put");
        ServletContext sc = getServletContext();
        BasicDataSource ds= (BasicDataSource) sc.getAttribute("dataSource");

        try {
            connection=ds.getConnection();
            PreparedStatement pstm = connection.prepareStatement
                    ("UPDATE response SET response = ? WHERE id = ?");

            pstm.setString(1,response);
            pstm.setString(2,id);
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

    public String getEmail(String name){
        ServletContext sc = getServletContext();
        BasicDataSource ds= (BasicDataSource) sc.getAttribute("dataSource");
        List<ResponseDto> responses = new ArrayList<>();
        String sql = "SELECT email FROM user WHERE name = ?";
        String email ="";

        try {
            Connection connection = ds.getConnection();
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setString(1, name);
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                email=rs.getString(1);
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
        return email;
    }


}
