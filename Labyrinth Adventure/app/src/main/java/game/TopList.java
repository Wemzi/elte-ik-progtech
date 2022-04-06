package game;

import persistence.OracleSqlManager;

import javax.swing.*;
import java.util.ArrayList;

public class TopList extends JFrame  {
    OracleSqlManager dbConnection;
    String[][] highScoreData;
    JTable highScoreTable;
    String[] columns = {"RANK","USER","SCORE"};
    JScrollPane sp;
    public TopList(OracleSqlManager dbConnection)
    {
        highScoreData = dbConnection.getHighScores();
        this.dbConnection = dbConnection;
        highScoreTable = new JTable(highScoreData,columns);
        highScoreTable.setBounds(30,40,200,300);
        sp = new JScrollPane(highScoreTable);
        add(sp);
        setSize(300,400);
        setVisible(true);
    }
}
