package game.view;

import persistence.OracleSqlManager;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class MapList extends JFrame {
    OracleSqlManager dbConnection;
    String[][] mapData;
    MapTable myMapsTable;
    String[] columns = {"MAP DATA","USER","ALIAS"};
    JScrollPane sp;
    JToolBar toolBar;

    /**
     * Constructs a JTable with the user's maps.
     * @param dbConnection the SQL connection where it can read the map data from.
     */
    public MapList(OracleSqlManager dbConnection)
    {
        mapData = dbConnection.getMyMaps();
        this.dbConnection = dbConnection;
        myMapsTable = new MapTable(mapData, columns);
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
                    JOptionPane.showConfirmDialog(myMapsTable,"Your map has been deleted succesfully," +
                            " the data shall be refreshed the next time you open this menu.","Map deletion",JOptionPane.OK_OPTION);
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
