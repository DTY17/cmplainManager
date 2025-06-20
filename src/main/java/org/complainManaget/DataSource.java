package org.complainManaget;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import org.apache.commons.dbcp2.BasicDataSource;

@WebListener
public class DataSource implements ServletContextListener{

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        BasicDataSource dataSource = new BasicDataSource();
        try{
            dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
            dataSource.setUrl("jdbc:mysql://localhost:3306/responsemanagement");
            dataSource.setUsername("root");
            dataSource.setPassword("Ijse@1234");
            dataSource.setInitialSize(5);
            dataSource.setMaxTotal(5);
            ServletContext context = sce.getServletContext();
            context.setAttribute("dataSource",dataSource);
            System.out.println("Connection Complete");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        try{
            ServletContext context = sce.getServletContext();
            BasicDataSource dataSource = (BasicDataSource) context.getAttribute("dataSource");
            dataSource.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}