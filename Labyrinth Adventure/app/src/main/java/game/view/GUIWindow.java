package game.view;

import game.model.Cell;
import game.model.LabyrinthBuilder;
import persistence.OracleSqlManager;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

public abstract class GUIWindow {
    protected JFrame frame;
    protected Labyrinth mainPanel;
    protected LabyrinthBuilder labyrinth;
    protected ArrayList<ArrayList<Cell>> cells = new ArrayList<ArrayList<Cell>>();
    protected JMenuBar bottomMenu;
    protected final JLabel gameStatLabel = new JLabel("");
    protected OracleSqlManager dbConnection;
    protected Timer refresher;
    protected JMenu menu;
    protected final int REFRESH_TIME_FOR_60FPS=15;
    protected final int SECONDINMS=1000;

    protected GUIWindow() throws IOException
    {
        ResourceLoader.initResources();
        frame = new JFrame("Labyrinth Adventure");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        bottomMenu=new JMenuBar();
        menu = new JMenu("Menu");
        bottomMenu.add(menu);
        frame.getContentPane().add(BorderLayout.SOUTH, bottomMenu);
    }
    public JFrame getFrame()
    {
        return frame;
    }
    public ArrayList<ArrayList<Cell>> getCells()
    {
        return cells;
    }
    public JPanel getLabyrinth()
    {
        return this.mainPanel;
    }
}
