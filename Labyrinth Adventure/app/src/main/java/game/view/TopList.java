package game.view;

import persistence.OracleSqlManager;

import javax.swing.*;
import java.util.ArrayList;

public class TopList extends JFrame  {
    OracleSqlManager dbConnection;
    String[][] highScoreData;
    JTable highScoreTable;
    String[] columns = {"RANK","USER","SCORE"};
    JScrollPane sp;

    /**
     * Constructs a new frame with a table in it, holding all the top scores from the users.
     * @param dbConnection the connection to the SQL server, where it grabs the data from.
     */
    public TopList(OracleSqlManager dbConnection)
    {
        highScoreData = dbConnection.getHighScores();
        this.dbConnection = dbConnection;
        highScoreTable = new JTable(highScoreData,columns){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        highScoreTable.setBounds(30,40,200,300);
        sp = new JScrollPane(highScoreTable);
        add(sp);
        setSize(300,400);
        setVisible(true);
    }
}
