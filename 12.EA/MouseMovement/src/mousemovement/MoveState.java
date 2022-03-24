/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mousemovement;

import javax.swing.Timer;

/**
 *
 * @author Dobreff András
 */
class MoveState implements MouseMovementState {
    private MouseMovementFrame mouseMovement;
    
    public MoveState(MouseMovementFrame m) {
        this.mouseMovement = m;
    }

    @Override
    public void drag() {
        mouseMovement.setLabel("Dragging");
        mouseMovement.setDragging();
        mouseMovement.restartTimer();
    }

    @Override
    public void move() {
        mouseMovement.restartTimer();
    }

    @Override
    public void stop() {
        mouseMovement.setLabel("Staying");
        mouseMovement.setStaying();
    }
    
}
