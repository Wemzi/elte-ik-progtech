/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package Labyrinth.Adventure;

import com.jcraft.jsch.JSchException;
import game.IncorrectMapException;
import game.Main;
import game.model.Cell;
import game.model.Dragon;
import game.model.Labyrinth;
import game.model.Player;
import game.view.*;
import org.junit.Before;
import org.junit.Test;
import persistence.OracleSqlManager;

import java.awt.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class AppTest {

    @Before public void loadResources()
    {
        try {
            ResourceLoader.initResources();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test(timeout=2000) public void connectWithSQLIsSuccessful()
    {
        OracleSqlManager sqlManager = null;
        try {
            sqlManager = new OracleSqlManager("idu27k","almafa");
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (JSchException e) {
            e.printStackTrace();
        }
        assert sqlManager != null;
    }



    @Test public void buildLabyrinthWithAldousBroder()
    {
        try {
            for(int idx=0; idx<100;idx++)
            {
                Labyrinth labyrinth  = new Labyrinth(true,"");
                assert true == new Dragon(labyrinth.getStartingCell(),labyrinth.getCells(),0).doTremauxPathFinding();
            }
        } catch ( IncorrectMapException e ) {
            e.printStackTrace();
        }
    }


    @Test public void EmptyLabyrinthCantPass()
    {
        try {
            Labyrinth labyrinth = new Labyrinth(false,"");
            assert false == new Dragon(labyrinth.getCells().get(0).get(0),labyrinth.getCells(),0).doTremauxPathFinding();
        } catch ( IncorrectMapException e ) {
            e.printStackTrace();
        }
    }


   @Test public void IncorrectStringThrowsException()
    {
            assertThrows(IncorrectMapException.class, () -> new Labyrinth(false,"1010 0110 1010"));
    }


    @Test public void TestMapBuilderCells()
    {
        try {
            Labyrinth labyrinth = new Labyrinth(false,"");
            Player Steve = new Player();
            for(ArrayList<Cell> cellRow : labyrinth.getCells())
            {
                for(Cell currentCell : cellRow)
                {
                    assert currentCell.isBrick();
                }
            }
            assert false == new Dragon(labyrinth.getCells().get(0).get(0),labyrinth.getCells(),0).doTremauxPathFinding();
        } catch ( IncorrectMapException e ) {
            e.printStackTrace();
        }
    }

    @Test public void TestSteveInitPosition()
    {
        try {
            AdventureGUI window = new AdventureGUI();
            Player Steve = window.getPlayer();
            window.setVisible(false);
            LabyrinthPanel panel = new LabyrinthPanel(
            window, false);
            panel.setSize(new Dimension(1920,1080));
            assert Steve.getCoordX() == window.getLabyrinth().getStartingCell().getrowIdx() && Steve.getCoordY() == window.getLabyrinth().getStartingCell().getcolIdx();
        } catch ( IncorrectMapException e ) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test public void mapListInitialization
    {
        OracleSqlManager sqlManager = null;
        try {
            sqlManager = new OracleSqlManager("idu27k","almafa");
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (JSchException e) {
            e.printStackTrace();
        }
        MapList mapList = new MapList(sqlManager);
        assert
    }

    @Test(timeout = 5000) public void topListInitialization
    {
        OracleSqlManager sqlManager = null;
        try {
            sqlManager = new OracleSqlManager("idu27k","almafa");
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (JSchException e) {
            e.printStackTrace();
        }
        TopList topList = new TopList(sqlManager);
        assert
    }
}
