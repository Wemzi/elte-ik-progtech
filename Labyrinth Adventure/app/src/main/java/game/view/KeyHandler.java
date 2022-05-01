package game.view;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyHandler extends KeyAdapter {
    public boolean upPressed,downPressed,leftPressed,rightPressed;

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    /**
     * sets boolean values according to which key is currently pressed.
     * @param e input from the keyboard.
     */
    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        if(code == KeyEvent.VK_UP) upPressed = true;
        if(code == KeyEvent.VK_DOWN) downPressed = true;
        if(code == KeyEvent.VK_LEFT) leftPressed = true;
        if(code == KeyEvent.VK_RIGHT) rightPressed = true;
    }

    /**
     * sets boolean values according to which key is currently not pressed.
     * @param e input from the keyboard.
     */
    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        if(code == KeyEvent.VK_UP) upPressed = false;
        if(code == KeyEvent.VK_DOWN) downPressed = false;
        if(code == KeyEvent.VK_LEFT) leftPressed = false;
        if(code == KeyEvent.VK_RIGHT) rightPressed = false;

    }
}
