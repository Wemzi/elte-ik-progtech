package game;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JFrame;
import java.io.IOException;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Arrays;
import java.util.Random;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import persistence.*;
import javax.swing.Timer;

public class AdventureGUI extends MapBuilder {

    private Dragon Drake;
    private static int score=0;
    //private Database data = new Database();
    private static int time = 0;
    private Timer timer = new Timer(1000, new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                
                gameStatLabel.setText("Pontszám: " + score + " Idő: " + time++);
                menu.repaint();
            }
        });
    private JMenu menu;
    /** Grafikus UI konstruktora,, melyben meghívom a labirintusgenerálást, létrehozzuk az összes UI elemet, generáljuk a játékost és a sárkányt.*/
    public AdventureGUI() throws IOException
    {
        super();
        labyrinth = new LabyrinthBuilder(false);
        cells = labyrinth.getCells();
        mainPanel = new Labyrinth(this);
        System.out.println("cell size: " + cells.size());
        JMenuItem newGame = new JMenuItem("New Game");
        JMenuItem Help = new JMenuItem("Help");
        JMenuItem TopList = new JMenuItem("Toplist");
        menu = new JMenu();
        /** Toplista elindítója*/
        TopList.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                //new HighScoreWindow(data.getHighScores(),null);
            }
        });
        /** Kis help menüpont plusznak */
        Help.addActionListener(new ActionListener()
        { @Override
            public void actionPerformed (ActionEvent e) 
            {
               JOptionPane.showMessageDialog(null,"A jatek soran egy veletlenszeruen generalt labirintusban ebredsz, ehesen, vilagitas nelkul. \n"
                       + "A feladatod, hogy kijuss a labirintusbol, amiben egy sarkany is lapul. \n"
                       + "A sarkanytol legalabb egy blokknyi tavolsagot erdemes tartani, kulonben akarmelyik pillanatban felfalhat. \n"
                       + "A kezdo poziciod mindig a bal also sarok, a labirintus kijarata a jobb felso sarokban talalhato. \n"
                       + "Jo jatekot! ");                                     
            } 
        });
        newGame.addActionListener(new ActionListener(){
           @Override
           /** newgame indítás menüből */
        public void actionPerformed (ActionEvent e) 
        {
           //data.storeHighScore(cells.size(), score);
           score=0;
           time = 0;
           restartGame();
        }
        });
        /** A billentyűlenyomáshoz kapcsolt eseménykezelő, mely elmozdítja a játékost, és a sárkányt is, megvizsgálja, hogy vége van e a játéknak,majd ha nem, újrarajzolja a pályát. */
        frame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent ke) {
                super.keyPressed(ke); 
                int kk = ke.getKeyCode();
                boolean siker = false;
                /*if(Drake.getcoordX() == Steve.getcoordX() && Drake.getcoordY() ==  Steve.getcoordY())
                    {
                        JOptionPane.showMessageDialog(null, "A sarkany elpusztitotta Steve-t. Vesztettel. Kattints az OK gombra az uj jatekhoz.");
                        data.storeHighScore(cells.size(), score);
                        score =0;
                        time = 0;
                        restartGame();
                    }
                */  
                switch (kk){
                    case KeyEvent.VK_LEFT:
                    if(cells.get(Steve.getcoordY()).get(Steve.getcoordX()).getedgeLeft())
                    {
                        break;
                    }  
                    Steve.move(-1,0,cells.size(),cells.get(0).size()); break;
                    case KeyEvent.VK_RIGHT:
                    if(cells.get(Steve.getcoordY()).get(Steve.getcoordX()).getedgeRight())
                    {
                        break;
                    }  
                    Steve.move(+1,0,cells.size(),cells.get(0).size()); break;
                    case KeyEvent.VK_UP: 
                    if(cells.get(Steve.getcoordY()).get(Steve.getcoordX()).getedgeUp())
                    {
                        break;
                    }
                    Steve.move(0,+1,cells.size(),cells.get(0).size()); break;
                    case KeyEvent.VK_DOWN: 
                    if(cells.get(Steve.getcoordY()).get(Steve.getcoordX()).getedgeDown())
                    {
                        break;
                    }  
                    Steve.move(0,-1,cells.size(),cells.get(0).size()); break;
                }
                /*if(Drake.getcoordX() == Steve.getcoordX() && Drake.getcoordY() ==  Steve.getcoordY())
                    {
                        timer.stop();
                        JOptionPane.showMessageDialog(null, "A sarkany elpusztitotta Steve-t. Vesztettel. Kattints az OK gombra az uj jatekhoz.");
                        data.storeHighScore(cells.size(), score);
                        restartGame();
                        score = 0; 
                        time = 0;
                        return;
                    }  */
                if(Steve.getcoordX()==cells.get(0).size()-1 && Steve.getcoordY()==cells.size()-1)
                {
                    timer.stop();
                    JOptionPane.showMessageDialog(null, "Gratulalok, kijutottal a labirintusbol!, mar generaljuk is a kovetkezot!");
                    ++score;
                    
                    gameStatLabel.setText("Pontszám: " + score + "Idő: " + time);
                    restartGame();
                    return;
                    
                }
            }
        });
        menu.add(newGame);
        menu.add(Help);
        menu.add(TopList);
        timer.start();
        bottomMenu.add(menu);
        bottomMenu.add(gameStatLabel);
        gameStatLabel.setText("Pontszám: " + score);
        frame.getContentPane().add(BorderLayout.SOUTH, bottomMenu);
        frame.getContentPane().add(BorderLayout.CENTER, mainPanel);
        frame.setSize(1280,720);
        frame.setVisible(true);
        refresher.start();
    }

    public JFrame getFrame()
    {
        return frame;
    }

    public ArrayList<ArrayList<Cell>> getCells()
    {
        return cells;
    }

    public Player getPlayer()
    {
        return Steve;
    }

    public Dragon getDragon()
    {
        return Drake;
    }
    /** újraindító metódus */
    public void restartGame()
    {
        try
            {
          frame.dispose();
          Steve = null;
          labyrinth = null;
          mainPanel = null;
          cells = null;
          Drake = null; 
          bottomMenu = null;
          timer.stop();
          new AdventureGUI();
            }
            catch (IOException f)
            {
                System.out.println(f.getMessage());
            }
    }
    
}


