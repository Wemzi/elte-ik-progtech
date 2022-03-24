/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package turnstilewithswithcase;

/**
 *
 * @author Dobreff András
 */
public class Turnstile {
    
    public enum State {LOCKED, UNLOCKED};
    public enum Event {PASS, COIN};
    
    private State currentState = State.LOCKED;
    
    public String getCurrentStateName(){
        return currentState.toString();
    }
    
    private void lockAction(){
        System.out.println("Locking...");
    }
    
    private void unlockAction(){
        System.out.println("Unlocking...");
    }
    
    private void thankYouAction(){
        System.out.println("Thank you! :)");
    }
    
    private void alarmAction(){
        System.out.println("ALARM!!");
    }
    
    public void handleEvent(Event e){
        switch(currentState){
            case LOCKED:
                switch(e){
                    case PASS:
                        alarmAction();
                        currentState = State.LOCKED;
                        break;
                    case COIN:
                        unlockAction();
                        currentState = State.UNLOCKED;
                        break;
                }
                break;
            case UNLOCKED:
                switch(e){
                    case PASS:
                        lockAction();
                        currentState = State.LOCKED;
                        break;
                    case COIN:
                        thankYouAction();
                        currentState = State.UNLOCKED;
                        break;
                }
                break;
        }
    }
}
