package game.view;

import javax.swing.*;

public class MapTable extends JTable {
    @Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }

    public MapTable(Object[][] rowData, Object[] columnNames) {
        super(rowData, columnNames);
    }
}
