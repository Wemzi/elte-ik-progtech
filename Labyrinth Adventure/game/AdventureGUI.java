package game;

import java.awt.BorderLayout;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Graphics2D;
import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

public class AdventureGUI {

    private JFrame frame;
    private Player Steve;
    private LabyrinthBuilder labyrinth;
    private Labyrinth mainPanel;
    private ArrayList<ArrayList<Cell>> cells;
    private Dragon Drake;

    public AdventureGUI() throws IOException
    {
        frame = new JFrame("Labyrinth Adventure");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        labyrinth = new LabyrinthBuilder();
        cells = labyrinth.getCells();
        
        
        Steve = new Player();
        Drake = new Dragon(cells.get(0).size(),cells.size());
        mainPanel = new Labyrinth(this);
        frame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent ke) {
                super.keyPressed(ke); 
                int kk = ke.getKeyCode();
                boolean siker = false;
                while(!siker)
                {
                int rand = new Random().nextInt(5);
                if(rand==1 && !cells.get(Drake.getcoordY()).get(Drake.getcoordX()).getedgeRight())
                {
                    Drake.randomMove(rand);
                    siker=true;
                }
                else if (rand == 2 && !cells.get(Drake.getcoordY()).get(Drake.getcoordX()).getedgeUp() )
                {
                    Drake.randomMove(rand);
                    siker=true;
                }
                else if(rand == 3 && !cells.get(Drake.getcoordY()).get(Drake.getcoordX()).getedgeDown() )
                {
                    Drake.randomMove(rand);
                    siker=true;
                }
                else if(rand == 4 && !cells.get(Drake.getcoordY()).get(Drake.getcoordX()).getedgeLeft())
                {
                    Drake.randomMove(rand);
                    siker=true;
                }
                }
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
                frame.repaint();
            }
        });
        frame.getContentPane().add(BorderLayout.CENTER, mainPanel);
        frame.setVisible(true);
        frame.setSize(630,630);
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
    
}

class Labyrinth extends JPanel{
    private ArrayList<ArrayList<Cell>> cells;
    private AdventureGUI board;

    public Labyrinth(AdventureGUI board)
    {
        this.board=board;
        cells = board.getCells();
    }

    @Override
    protected void paintComponent(Graphics g)
    {
        int picsize = 63 ;
        Graphics2D gr = (Graphics2D)g;
        for(int idx=cells.size()-1; idx>=0;idx--)
        {
            for (int jdx=0; jdx<cells.size(); jdx++)
            {
                Cell cell = cells.get(idx).get(jdx);
                Image img = null;
                try
                {
                if(board.getDragon().getcoordY() == idx && board.getDragon().getcoordX() == jdx )
                {
                    img = cell.selectDragonImage();
                  
                }
                else if(board.getPlayer().getcoordY() == idx && board.getPlayer().getcoordX() == jdx )
                {
                    img = cell.selectPlayerImage();
                }
                else
                {
                    img = cell.selectImage();
                }
                
                }
                catch(IOException e)
                {
                    System.out.println("Error loading file");
                }
                gr.drawImage(img, jdx * picsize,(board.getFrame().getHeight()-(idx+2)*(picsize)), picsize, picsize, null);
            
            }
        }
    }
    
}
