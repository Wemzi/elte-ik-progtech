/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mousemovement;

/**
 *
 * @author Dobreff András
 */
class StayState implements MouseMovementState {
    
    private MouseMovementFrame mouseMovement;
    
    public StayState(MouseMovementFrame m) {
        this.mouseMovement = m;
    }

    @Override
    public void drag() {
        mouseMovement.setLabel("Dragging");
        mouseMovement.setDragging();
    }

    @Override
    public void move() {
        mouseMovement.setLabel("Moving ");
        mouseMovement.setMoving();
    }

    @Override
    public void stop() {
        System.err.println("Stop event on staying state - nothing to do");
    }
    
}
