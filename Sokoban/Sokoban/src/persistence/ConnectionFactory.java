/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistence;


import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;

        /**
 *
 * @author lkcsdvd
 */
public class ConnectionFactory {
     private static Connection conn;
     
     public ConnectionFactory(){}
     
    public static Connection  getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            conn = DriverManager.getConnection("jdbc:mysql://localhost/sqlexample?" +    
                    "serverTimezone=UTC&user=tanulo&password=asd123");
        }
        catch (Exception e){
            System.out.println("" + e);
        }
        return conn;
    }
}
    