package org.complainManaget.controller;

import java.util.Properties;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.dbcp2.BasicDataSource;
import org.complainManaget.model.ActiveData;
import org.complainManaget.model.ResponseDto;

import java.io.IOException;
import java.sql.*;
import java.util.*;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.sql.SQLException;

@WebServlet("/response")
public class RequestListServlet extends HttpServlet {
    Connection connection;



    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("Get Data User");
        ServletContext sc = getServletContext();
        BasicDataSource ds= (BasicDataSource) sc.getAttribute("dataSource");
        List<ResponseDto> responses = new ArrayList<>();
        String sql = "SELECT id, name, date, time, response, state FROM response WHERE name = ?";

        try {
           Connection connection = ds.getConnection();
           PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setString(1, ActiveData.getInstance().getUser());
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                ResponseDto response = new ResponseDto();
                response.setId(rs.getString("id"));
                response.setName(rs.getString("name"));
                response.setDate(rs.getString("date"));
                response.setTime(rs.getString("time"));
                response.setDescription(rs.getString("response"));
                response.setState(rs.getString("state"));
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
            e.printStackTrace();
        }
        req.getRequestDispatcher("./View/requestList.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String Path = req.getParameter("_method");
        if(Path.equals("Post")){
            saveData(req,resp);
        }else if(Path.equals("Put")){
            updateData(req,resp);
        }else if(Path.equals("Delete")){
            deletData(req,resp);
        }
    }

    public void saveData(HttpServletRequest req,HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String date = req.getParameter("date");
        String time = req.getParameter("time");
        String description = req.getParameter("message");

        ServletContext sc = getServletContext();
        BasicDataSource ds= (BasicDataSource) sc.getAttribute("dataSource");

        try {
            connection=ds.getConnection();
            PreparedStatement pstm=connection.prepareStatement
                    ("INSERT INTO response (name,date,time,response,state) VALUES (?,?,?,?,?)");
            pstm.setString(1,name);
            pstm.setString(2,date);
            pstm.setString(3,time);
            pstm.setString(4,description);
            pstm.setString(5,"Not Reply");

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
        req.getRequestDispatcher("View/requestList.jsp").forward(req, resp);
    }

    public void deletData(HttpServletRequest req,HttpServletResponse resp) throws ServletException, IOException {
        String roleDetails ="Not Reply";
        System.out.println("Delete Data User");
        String id = req.getParameter("id");
        ServletContext sc = getServletContext();
        BasicDataSource ds= (BasicDataSource) sc.getAttribute("dataSource");

        try {
            connection=ds.getConnection();

            PreparedStatement pstm2 = connection.prepareStatement
                    ("DELETE FROM response WHERE id = ? AND state = ?");
            pstm2.setString(1,id);
            pstm2.setString(2,roleDetails);
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
        req.getRequestDispatcher("View/requestList.jsp").forward(req, resp);
    }

    public void updateData(HttpServletRequest req,HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("Update Data User Response");
        String id = req.getParameter("id");
        System.out.println( id + " : ID");
        String response = req.getParameter("message");
        ServletContext sc = getServletContext();
        BasicDataSource ds= (BasicDataSource) sc.getAttribute("dataSource");

        try {
            connection = ds.getConnection();
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
        req.getRequestDispatcher("View/requestList.jsp").forward(req, resp);
    }

    public static boolean getMessageSend(String email, String reply, String response) throws SQLException {
        final String userName = "dinanthemika7@gmail.com";
        final String password = "nklrcalfmqsuczjw";
        Properties prop = new Properties();
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "465");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.ssl.enable", "true");
        prop.put("mail.smtp.socketFactory.port", "465");
        prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        prop.put("mail.smtp.socketFactory.fallback", "false");
        prop.put("mail.smtp.ssl.protocols", "TLSv1.2");

        Session session = Session.getInstance(prop, new javax.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(userName, password);
            }
        });
        System.out.println(email);

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(userName));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(email));
            message.setSubject("Test Email with Reset Code");

            String htmlContent = "<html><head><style>"
                    + "body { font-family: Arial, sans-serif; color: #333; line-height: 1.6; margin: 0; padding: 20px; }"
                    + ".email-container { max-width: 600px; margin: 0 auto; background: #f9f9f9; padding: 20px; border-radius: 8px; }"
                    + ".header { color: #2c3e50; border-bottom: 1px solid #eee; padding-bottom: 10px; }"
                    + ".complaint-section { background: #fff; padding: 15px; margin: 15px 0; border-left: 3px solid #e74c3c; }"
                    + ".response-section { background: #e8f5e9; padding: 15px; margin: 15px 0; border-left: 3px solid #2ecc71; }"
                    + ".complaint-id { font-weight: bold; color: #e74c3c; }"
                    + ".footer { margin-top: 20px; font-size: 12px; color: #7f8c8d; text-align: center; }"
                    + "</style></head><body>"
                    + "<div class='email-container'>"
                    + "<div class='header'><h2>Complaint Response</h2></div>"

                    + "<div class='complaint-section'>"
                    + "<h3>Complaint Reference: <span class='complaint-id'>#" + email + "</span></h3>"
                    + "<p>" + response + "</p>"
                    + "</div>"

                    + "<div class='response-section'>"
                    + "<h3>Our Response</h3>"
                    + "<p>" + reply + "</p>"
                    + "</div>"

                    + "<div class='footer'>"
                    + "<p>This is an automated response. Please do not reply directly to this email.</p>"
                    + "<p>&copy; " + java.time.Year.now().getValue() + " Your Company Name. All rights reserved.</p>"
                    + "</div>"
                    + "</div></body></html>";

            message.setContent(htmlContent, "text/html; charset=utf-8");

            Transport.send(message);
            System.setProperty("javax.mail.debug", "true");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }
}
