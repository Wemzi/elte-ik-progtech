package sqlexample_mysql5;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class SqlExample {
    public static void main(String[] args) {
        try {
            Connection conn = ConnectionFactory.getConnection();
            try (Statement stmt = conn.createStatement()) {
                ResultSet rs = stmt.executeQuery("SELECT * FROM person;");
                while (rs.next()){
                    int id = rs.getInt("id");
                    String name = rs.getString("name");
                    System.out.println("ID=" + id + ", NAME=" + name);
                }
            }
        }
        catch (Exception e){
            System.out.println("" + e);
        } 
    }
}
