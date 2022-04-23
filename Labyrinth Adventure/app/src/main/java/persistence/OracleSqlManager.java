package persistence;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.logging.Logger;
import com.jcraft.jsch.*;
import com.mysql.cj.protocol.Resultset;

import java.util.logging.Level;

public class OracleSqlManager {
    java.sql.Connection connection = null;
    String user;

    private final static Logger LOGGER =
            Logger.getLogger(OracleSqlManager.class.getName());
    public OracleSqlManager(String username, String password) throws SQLException,JSchException{
       super();
            this.connect(username, password);
            this.user = username.toUpperCase(Locale.ROOT);
    }

    private ResultSet executeQuery(String query)
    {
        ResultSet rs = null;
        try {
            java.sql.Statement stmt = connection.createStatement();
            rs = stmt.executeQuery(query);
        }catch(SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }

    public void deleteMap(String mapData)
    {
        try {
            executeUpdate("DELETE FROM IDU27K.MAPS WHERE mapdata='"+mapData+ "' AND USER='" + this.user +"'");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private int executeUpdate(String query) throws SQLException
    {
        int ret = 0;
            java.sql.Statement stmt = connection.createStatement();
             ret = stmt.executeUpdate(query);
        return ret;
    }

    public void createMapsTable()
    {
        try {
            executeUpdate("CREATE TABLE idu27k.maps(\n" +
                    "\tmapData VARCHAR2(2048) NOT NULL,\n" +
                    "\tusername VARCHAR2(6) NOT NULL\n" +
                    ")");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createUsersTable()
    {
        try {
            executeUpdate("CREATE TABLE IDU27K.highscores(\n" +
                    "\tusername VARCHAR2(6),\n" +
                    "\tscore int\n" +
                    ")\n");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String[] getRandomMap()
    {
        String[] ret = new String[2];
        ResultSet rs = executeQuery("SELECT * FROM(\n" +
                "SELECT * FROM idu27k.maps \n" +
                "ORDER BY DBMS_RANDOM.RANDOM)\n" +
                "WHERE ROWNUM=1");
        try {
        if(!rs.next()) throw new SQLException("No map available!");
                ret[0] = rs.getString(1);
                ret[1] = rs.getString(2);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ret;
    }

    public int saveMap(String mapData,String alias)
    {
        try {
            return executeUpdate("INSERT INTO idu27k.maps\n" +
                    "VALUES('"+mapData+"','" + this.user + "','"+ alias + "')");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public String[][] getHighScores()
    {

        ResultSet rs = executeQuery("SELECT * FROM IDU27K.HIGHSCORES WHERE ROWNUM<=10 ORDER BY SCORE DESC");
            try {
                String[][] highScoreData = new String[10][3];
                int idx=0;
                while(rs.next())
                {
                    highScoreData[idx][0] = ""+ rs.getRow();
                    highScoreData[idx][1] = rs.getString(1);
                    highScoreData[idx++][2] = rs.getString(2);
                }
                return highScoreData;
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return null;
    }

    public String[][] getMyMaps()
    {
        int size = 0;
        ResultSet rscount = executeQuery("SELECT COUNT(*) FROM IDU27K.MAPS WHERE USERNAME='"+this.user+"'");
        try {
            if(!rscount.next()) return null;
            size = rscount.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        ResultSet rs = executeQuery("SELECT * FROM IDU27K.MAPS WHERE USERNAME='"+this.user+"'");
        String[][] ret = new String[size][3];
            try {
                int idx=0;
                while(rs.next())
                {
                    ret[idx][0] = rs.getString(1);
                    ret[idx][1] = rs.getString(2);
                    ret[idx++][2] = rs.getString(3);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return ret;
    }


    public int getHighScore()
    {
        ResultSet rs = executeQuery("SELECT score from IDU27K.HIGHSCORES WHERE USERNAME='" + this.user + "'");
        try {
            if(rs.next())
            {
                System.out.println(rs.getInt(1));
            return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int saveHighScore(int value)
    {
        try{
            return executeUpdate("INSERT INTO IDU27K.HIGHSCORES VALUES('"+ this.user + "',"+ value + ")");
            } catch (SQLException ex) {
            try {
                return executeUpdate("UPDATE (SELECT SCORE FROM HIGHSCORES WHERE USERNAME='"+this.user +"') SET SCORE="+value);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return 0;
    }

    public void connect(String username, String pw) throws SQLException, JSchException{
        int assigned_port;
        final int local_port=2345;
        final int remote_port=1521;
        final String remote_host="caesar.elte.hu";
            JSch jsch = new JSch();
            Session session = jsch.getSession("lkcsdvd", remote_host, 22);
            session.setPassword("szakdolgozatmiattipublikusjelszo1");
            java.util.Properties config = new java.util.Properties();
            config.put("StrictHostKeyChecking", "no");
            config.put("Compression", "yes");
            config.put("ConnectionAttempts","2");
            session.setConfig(config);
            session.connect();
            assigned_port = session.setPortForwardingL(local_port,
                    "aramis.inf.elte.hu", remote_port);
            System.out.println(assigned_port);
        if (assigned_port == 0) {
            LOGGER.log(Level.SEVERE, "Port forwarding failed !");
            return;
        }
        final String database_user=username;
        final String database_password=pw;
        String url = "jdbc:oracle:thin:"+ database_user+ "/" + database_password + "@//localhost:2345/aramis";
        connection = java.sql.DriverManager.getConnection(url);
        }
}


