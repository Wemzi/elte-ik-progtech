/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package turnstilewithtransitiontable;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Dobreff András
 */
class TurnstileStateMachine{

    private Turnstile.State currentState;
    
    private List<Transition> transitions = new ArrayList<>();
    
    public TurnstileStateMachine(Turnstile.State state) {
        this.currentState = state;
    }
    
    public void addTransiton(Transition transition){
        this.transitions.add(transition);
    }
    
    public void  handleEvent(Turnstile.Event e){
        Turnstile.State oldstate = this.currentState;
        for(Transition transition : this.transitions){
            if(transition.state.equals(oldstate)
                    && transition.event.equals(e)){
                transition.action.execute();
               this.currentState =  transition.nextState;
            }
        }
        
    }

    String getCurrentState() {
        return this.currentState.toString();
    }
}
