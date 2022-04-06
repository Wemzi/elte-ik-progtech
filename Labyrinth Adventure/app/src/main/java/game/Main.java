package game;

import persistence.OracleSqlManager;

import java.io.IOException;
public class Main {

    public static void main(String[] args) throws IOException{
        Thread game = new Thread(()->{
        MainMenu myMenu = new MainMenu();
    });
        game.start();
    }
}
