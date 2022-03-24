/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package turnstilewithswithcase;

import java.util.Scanner;

/**
 *
 * @author Dobreff András
 */
public class TurnstileWithSwithCase {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Turnstile turnstile = new Turnstile();
        System.out.println("Current state: "+turnstile.getCurrentStateName());
        
        String input = "";
        Scanner sc = new Scanner(System.in);
        
        while(!input.equals("Q")){
            System.out.print("Enter next event: ");
            input = sc.nextLine();
            
            if(input.equals("PASS")){
                turnstile.handleEvent(Turnstile.Event.PASS);
                System.out.println("Current state: "+turnstile.getCurrentStateName());
            }else if(input.equals("COIN")){
                turnstile.handleEvent(Turnstile.Event.COIN);
                System.out.println("Current state: "+turnstile.getCurrentStateName());
            }
        }
    }
    
}
