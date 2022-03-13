package game;

import java.io.IOException;
public class Main {

    public static void main(String[] args) throws IOException{
        Thread game = new Thread(()->{
        //new AdventureGUI();
        MainMenu myMenu = new MainMenu();
    });
        game.start();
    }
}
