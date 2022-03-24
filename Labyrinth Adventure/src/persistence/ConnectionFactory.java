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

/** SQL kapcsolódás */
public class ConnectionFactory {
     private static Connection conn;
     
     public ConnectionFactory(){}
     
    public static Connection  getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            conn = DriverManager.getConnection("jdbc:mysql://localhost/labyrinthadventure?" +    
                    "serverTimezone=UTC&user=root&password=xd");
        }
        catch (Exception e){
            System.out.println("" + e);
        }
        return conn;
    }
}
    
