/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package game;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.*;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author LUD1BP
 */
public class MainMenu {
    private JFrame frame;
    private LabyrinthBuilder labyrinth;
    private final JLabel gameStatLabel = new JLabel("");
    private static int score=0;
    //private Database data = new Database();
    private static int time = 0;
    private JButton topListButton = new JButton("Toplist");
    private JButton mapBuilderButton = new JButton("Map Builder");
    private JButton freePlayButton = new JButton("Free Play");
    private JPanel buttonPanel = new JPanel(new GridLayout(1,2,50,50));
    public MainMenu() {
        this.frame = new JFrame("Labyrinth Adventure");
        topListButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    ScriptEngineManager manager = new ScriptEngineManager();
                    ScriptEngine engine = manager.getEngineByName("JavaScript");
                    FirebaseOptions options = new FirebaseOptions.Builder()
                            .setCredentials(GoogleCredentials.fromStream(ResourceLoader.class.getClassLoader().getResource("app-secret.json").openStream()))
                            .setDatabaseUrl("https://labyrinth-adventure-9a289-default-rtdb.europe-west1.firebasedatabase.app")
                            .build();

                    FirebaseApp.initializeApp(options);
                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Maps/map1");
                    System.out.println(ref.getKey());


                   System.out.println(ref.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot snapshot) {
                            System.out.println("Megvaltozott az ertek! " + snapshot.getValue());
                        }

                        @Override
                        public void onCancelled(DatabaseError error) {

                        }
                    }));
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }

        });
        mapBuilderButton.addActionListener(new ActionListener()
        { @Override
            public void actionPerformed (ActionEvent e) 
            {
                try
                {
                    MapBuilder mapBuilderWindow = new MapBuilder();
                    mapBuilderWindow.buildMap();

                }
                catch(Exception m)
                {
                    m.printStackTrace();
                }

            }
        });
        freePlayButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    new AdventureGUI();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
        buttonPanel.add(topListButton);
        buttonPanel.add(mapBuilderButton);
        buttonPanel.add(freePlayButton);
        frame.add(buttonPanel);
        frame.pack();
        frame.setSize(1280,720);
        frame.setVisible(true);
    }

   
}
