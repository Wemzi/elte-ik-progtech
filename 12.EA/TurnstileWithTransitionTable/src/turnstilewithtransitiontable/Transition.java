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
public class Transition {
    public final Turnstile.Event event;
    public final Action action;
    public final Turnstile.State nextState;
    public final Turnstile.State state;

    public Transition(Turnstile.State state, Turnstile.Event event, Action action, Turnstile.State nextState) {
        this.state = state;
        this.event = event;
        this.action = action;
        this.nextState = nextState;
    }
}
