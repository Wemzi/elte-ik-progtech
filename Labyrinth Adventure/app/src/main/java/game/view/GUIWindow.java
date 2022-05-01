package game.view;

import game.model.Cell;
import game.model.LabyrinthBuilder;
import persistence.OracleSqlManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

public abstract class GUIWindow extends JFrame {
    protected LabyrinthPanel mainPanel;
    protected LabyrinthBuilder labyrinth;
    protected ArrayList<ArrayList<Cell>> cells = new ArrayList<ArrayList<Cell>>();
    protected JMenuBar bottomMenu;
    protected final JLabel gameStatLabel = new JLabel("");
    protected OracleSqlManager dbConnection;
    protected final JMenuItem backToMainMenu = new JMenuItem("Back to main menu");
    protected ActionListener backToMainMenuAction = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            int response = JOptionPane.showConfirmDialog(null,"Are you sure you want to quit? Any unsaved progress will be lost.","Confirmation", JOptionPane.OK_CANCEL_OPTION);
            if(response == JOptionPane.OK_OPTION)
            {
                dispose();
            }
        }
    };
    protected Timer refresher;
    protected JMenu menu;
    protected final int SPRITE_UPDATE_FREQUENCY=100;
    protected final int REFRESH_TIME_FOR_60FPS=15;
    protected final int SECONDINMS=1000;

    /**
     * Sets some basic values which are essential for all windows opened.
     * @throws IOException if it couldn't load the resources.
     */
    protected GUIWindow() throws IOException
    {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Labyrinth Adventure");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        bottomMenu=new JMenuBar();
        menu = new JMenu("Menu");
        backToMainMenu.addActionListener(backToMainMenuAction);
        menu.add(backToMainMenu);
        bottomMenu.add(menu);
        getContentPane().add(BorderLayout.SOUTH, bottomMenu);
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
