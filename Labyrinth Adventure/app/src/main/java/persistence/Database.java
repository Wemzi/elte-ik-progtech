package persistence;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author lkcsdvd
 */

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

public class Database {
    private final String tableName = "HighScore";
    private final Connection conn;
    private final HashMap<Integer, Integer> highScores;
    
    public Database(){
        Connection c = null;
        try {
            c = ConnectionFactory.getConnection();
        } catch (Exception ex) { System.out.println("No connection");}
        this.conn = c;
        highScores = new HashMap<>();
        loadHighScores();
    }
    
    public boolean storeHighScore(int labSize, int newScore){
        return mergeHighScores(labSize, newScore, newScore > 0);
    }
    
    public ArrayList<HighScore> getHighScores(){
        ArrayList<HighScore> scores = new ArrayList<>();
        for (Integer labSize : highScores.keySet()){
            HighScore h = new HighScore(labSize, highScores.get(labSize));
            scores.add(h);
            System.out.println(h);
        }
        return scores;
    }
    
    private void loadHighScores(){
        try (Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery("SELECT * FROM " + tableName);
            while (rs.next()){
                int score = rs.getInt("Score");
                int labSize = rs.getInt("LabSize");
                mergeHighScores(labSize, score, false);
            }
        } catch (Exception e){ System.out.println("loadHighScores error");}
    }
    
    private boolean mergeHighScores(int labSize, int score, boolean store){
        System.out.println("Merge: " + labSize + "-" + score + "(" + store + ")");
        boolean doUpdate = true;
            highScores.put(labSize, score);
            if (store) return storeToDatabase(labSize, score) > 0;
            return true;
        }
    
    
    private int storeToDatabase(int labSize, int score){
        try (Statement stmt = conn.createStatement()){
            String s = "INSERT INTO " + tableName + 
                    " (LabSize,Score) " + 
                    "VALUES('" + labSize+ "'," + score + 
                    ")";
            return stmt.executeUpdate(s);
        } catch (Exception e){
            System.out.println("storeToDatabase error");
        }
        return 0;
    }
    
}