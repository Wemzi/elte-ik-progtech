package game.view;

import persistence.OracleSqlManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class MapList extends JFrame {
    OracleSqlManager dbConnection;
    String[][] mapData;
    JTable myMapsTable;
    String[] columns = {"MAP DATA","USER","ALIAS"};
    JScrollPane sp;
    JToolBar toolBar;

    public JTable getMyMapsTable() {
        return myMapsTable;
    }

    /**
     * Constructs a JTable with the user's maps.
     * @param dbConnection the SQL connection where it can read the map data from.
     */
    public MapList(OracleSqlManager dbConnection)
    {
        mapData = dbConnection.getMyMaps();
        this.dbConnection = dbConnection;
        myMapsTable = new JTable(mapData,columns){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        myMapsTable.setBounds(30,40,200,300);
        toolBar = new JToolBar();
        toolBar.add(new AbstractAction("Delete Map") {
            @Override
            public void actionPerformed(ActionEvent e) {
                int rowNum = myMapsTable.getSelectedRow();
                String mapData = null;
                Object mapDataobj = myMapsTable.getValueAt(rowNum,0);
                if(mapDataobj instanceof String ){
                    mapData = (String)mapDataobj;
                    dbConnection.deleteMap(mapData);
                    String[] buttons = {"OK"};
                    JOptionPane.showOptionDialog(null,"Deletion of map is successful. You should see your changes the next time you open this window.","Map saving is successful",
                            JOptionPane.NO_OPTION,JOptionPane.OK_OPTION,null,buttons,buttons[0]);
                }


            }
        });
        add(toolBar, BorderLayout.NORTH);
        sp = new JScrollPane(myMapsTable);
        add(sp);
        setSize(300,400);
        setVisible(true);
    }
}
