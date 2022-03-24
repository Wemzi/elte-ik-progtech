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
class DragState implements MouseMovementState {
    
    private MouseMovementFrame mouseMovement;
    
    public DragState(MouseMovementFrame m) {
        this.mouseMovement = m;
    }

    @Override
    public void drag() {
        mouseMovement.restartTimer();
    }

    @Override
    public void move() {
        mouseMovement.setLabel("Moving");
        mouseMovement.setMoving();
        mouseMovement.restartTimer();
    }

    @Override
    public void stop() {
        mouseMovement.setLabel("Staying");
        mouseMovement.setStaying();
    }
    
}
