/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package amoba;

import java.util.ArrayList;


/**
 *
 * @author lkcsd
 */
public class Player {
    private final ArrayList<XOButton> ownButtons = new ArrayList<>();
    private static boolean isXturn = true;
    
    public ArrayList<XOButton> getownButtons()
    {
        return ownButtons;
    }
    
    public void addButton(XOButton e)
    {
        ownButtons.add(e);
    }
    
    public static boolean isXTurn()
    {
        isXturn = !isXturn;
        return !isXturn;
    }
    
}