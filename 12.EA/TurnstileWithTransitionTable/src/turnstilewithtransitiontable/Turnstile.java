/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package turnstilewithtransitiontable;

/**
 *
 * @author Dobreff András
 */
public class Turnstile {

    public enum State {LOCKED, UNLOCKED};
    public enum Event {COIN, PASS};
    
    private TurnstileStateMachine statemachine = new TurnstileStateMachine(State.LOCKED);

    public Turnstile() {
        initStateMachine();
    }

    private void initStateMachine() {
        statemachine.addTransiton(new Transition(State.LOCKED, Event.COIN,
                () -> {unlockAction();}, State.UNLOCKED));
        statemachine.addTransiton(new Transition(State.LOCKED, Event.PASS,
                () -> {alarmAction();}, State.LOCKED));
        statemachine.addTransiton(new Transition(State.UNLOCKED, Event.COIN,
                () -> {thankYouAction();}, State.UNLOCKED));
        statemachine.addTransiton(new Transition(State.UNLOCKED, Event.PASS,
                () -> {lockAction();}, State.LOCKED));
    }
    
    public void handleEvent(Event e){
        statemachine.handleEvent(e);
    }
    
    public String getCurrentStateName(){
        return statemachine.getCurrentState();
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
}

