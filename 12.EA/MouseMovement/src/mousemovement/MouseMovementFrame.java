/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mousemovement;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.Timer;
import javax.swing.WindowConstants;

/**
 *
 * @author Dobreff András
 */
class MouseMovementFrame extends JFrame implements MouseMotionListener, ActionListener{

    private final MouseMovementState moveState;
    private final MouseMovementState dragState;
    private final MouseMovementState stayState;
    
    
    private JLabel label = new JLabel("Staying");
    private MouseMovementState currentState;
    private Timer timer;
       
    public MouseMovementFrame() {
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.addMouseMotionListener(this);
        this.setSize(400, 300);
        this.add(this.label, BorderLayout.NORTH);
        this.timer = new Timer(500, this);
        this.timer.setRepeats(false);
        
        this.moveState = new MoveState(this);
        this.dragState = new DragState(this);
        this.stayState = new StayState(this);
        
        this.currentState = this.stayState;
    }
    
    public void setMoving(){
        this.currentState = moveState;
    }
    
    public void setStaying(){
        this.currentState = stayState;
    }
    
    public void setDragging(){
        this.currentState = dragState;
    }
    
    public void setLabel(String text){
        this.label.setText(text);
    }
    
    public void restartTimer(){
        this.timer.restart();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
       this.currentState.drag();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
       this.currentState.move();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.currentState.stop();
    }    
}
