package game;

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
    JTable mapTable;
    String[] columns = {"MAP DATA","USER","ALIAS"};
    JScrollPane sp;
    JToolBar tb;
    DeleteRowFromTableAction deleteAction;
    public MapList(OracleSqlManager dbConnection)
    {
        mapData = dbConnection.getMyMaps();
        this.dbConnection = dbConnection;

        mapTable = new JTable(mapData,columns);
        DefaultTableModel model = (DefaultTableModel) mapTable.getModel();
        deleteAction = new DeleteRowFromTableAction(mapTable,model);
        mapTable.setBounds(30,40,200,300);
        tb = new JToolBar();
        tb.add(deleteAction);
        add(tb, BorderLayout.NORTH);
        sp = new JScrollPane(mapTable);
        add(sp);
        setSize(300,400);
        setVisible(true);
    }

    public abstract class AbstractTableAction<T extends JTable, M extends TableModel> extends AbstractAction {

        private T table;
        private M model;

        public AbstractTableAction(T table, M model) {
            this.table = table;
            this.model = model;
        }

        public T getTable() {
            return table;
        }

        public M getModel() {
            return model;
        }

    }

    public class DeleteRowFromTableAction extends AbstractTableAction<JTable, DefaultTableModel> {

        public DeleteRowFromTableAction(JTable table, DefaultTableModel model) {
            super(table, model);
            putValue(NAME, "Delete selected rows");
            putValue(SHORT_DESCRIPTION, "Delete selected rows");
            table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
                @Override
                public void valueChanged(ListSelectionEvent e) {
                    setEnabled(getTable().getSelectedRowCount() > 0);
                }
            });
            setEnabled(getTable().getSelectedRowCount() > 0);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("...");
            JTable table = getTable();
            if (table.getSelectedRowCount() > 0) {
                List<Vector> selectedRows = new ArrayList<>(25);
                DefaultTableModel model = getModel();
                Vector rowData = model.getDataVector();
                for (int row : table.getSelectedRows()) {
                    int modelRow = table.convertRowIndexToModel(row);
                    Vector rowValue = (Vector) rowData.get(modelRow);
                    selectedRows.add(rowValue);
                }
                for (Vector rowValue : selectedRows) {
                    int rowIndex = rowData.indexOf(rowValue);
                    model.removeRow(rowIndex);
                }
            }
        }
    }
}
