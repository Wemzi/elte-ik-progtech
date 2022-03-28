package game;

import persistence.ConnectionFactory;

import java.io.IOException;
public class Main {

    public static void main(String[] args) throws IOException{
        ConnectionFactory.getConnection();
        Thread game = new Thread(()->{
        MainMenu myMenu = new MainMenu();

    });
        game.start();
    }
}
