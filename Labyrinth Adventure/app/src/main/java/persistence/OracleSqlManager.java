package persistence;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Logger;
import com.jcraft.jsch.*;

import java.util.logging.Level;

public class OracleSqlManager {

    // Logger
    private final static Logger LOGGER =
            Logger.getLogger(OracleSqlManager.class.getName());

    public OracleSqlManager() {
       super();
        try {
            this.connect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void connect() throws Exception{

        //
        int assigned_port;
        final int local_port=64531;
        final int remote_port=1521;
        final String remote_host="caesar.elte.hu";

        try {
            JSch jsch = new JSch();
            Session session = jsch.getSession("lkcsdvd", remote_host, 22);
            session.setPassword("Barby990113");
            java.util.Properties config = new java.util.Properties();
            config.put("StrictHostKeyChecking", "no");
            config.put("Compression", "yes");
            config.put("ConnectionAttempts","2");
            session.setConfig(config);
            session.connect();
            assigned_port = session.setPortForwardingL(local_port,
                    "aramis.inf.elte.hu", remote_port);
            System.out.println(assigned_port);
        } catch (JSchException e) {
            LOGGER.log(Level.SEVERE, e.getMessage()); return;
        }
        if (assigned_port == 0) {
            LOGGER.log(Level.SEVERE, "Port forwarding failed !");
            return;
        }
        final String database_user="idu27k";
        final String database_password="almafa";
        String url = "jdbc:oracle:thin:"+ database_user+ "/" + database_password + "@//localhost:64531/aramis";
        java.sql.Connection connection = java.sql.DriverManager.getConnection(url);
                    Class.forName("oracle.jdbc.driver.OracleDriver");

                java.sql.Statement stmt = connection.createStatement();
                String Sql = "INSERT INTO YES(CUSTOMER_ID,CUSTOMER_NAME,CITY) VALUES(213,'MAMA','BUDAPEST')";
                ResultSet rs = null;
                    rs = stmt.executeQuery(Sql);
                   if(!rs.next()) return;
                    System.out.println(rs.getString(2));
                Thread.sleep(1000);
        }
}


