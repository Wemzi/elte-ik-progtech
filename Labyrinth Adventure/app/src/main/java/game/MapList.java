package game;

import persistence.OracleSqlManager;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class MapList extends JFrame {
    OracleSqlManager dbConnection;
    String[][] mapData;
    JTable mapTable;
    String[] columns = {"MAP DATA","USER","ALIAS"};
    JScrollPane sp;
    public MapList(OracleSqlManager dbConnection)
    {
        mapData = dbConnection.getMyMaps();
        this.dbConnection = dbConnection;
        mapTable = new JTable(mapData,columns);
        mapTable.setModel(new DefaultTableModel());
        mapTable.setBounds(30,40,200,300);
        sp = new JScrollPane(mapTable);
        add(sp);
        setSize(300,400);
        setVisible(true);
    }

}
