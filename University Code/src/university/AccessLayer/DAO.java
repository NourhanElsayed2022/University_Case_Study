/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package university.AccessLayer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Software
 */
public class DAO {
    private static Connection con;
    private static final String JDBC_URL = "jdbc:oracle:thin:@localhost:1521:XE";
    private static final String USERNAME = "UNIVERSITY";
    private static final String PASSWORD = "123";
    
    
    public DAO(){//make the constructor private for the enforce just there is one oebject from this class to apply the signleton design pattern
        try {
             
            con = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
        } catch (SQLException ex) {
            System.out.println("Can't connect");
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, "Error establishing database connection", ex);
            System.exit(1);
        } 
    }
     public static Connection getConnection() {
        return con;
    }
    
}
