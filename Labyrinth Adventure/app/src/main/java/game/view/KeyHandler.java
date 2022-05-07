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
        if(code == KeyEvent.VK_UP || code == KeyEvent.VK_W) upPressed = true;
        if(code == KeyEvent.VK_DOWN || code == KeyEvent.VK_S) downPressed = true;
        if(code == KeyEvent.VK_LEFT|| code == KeyEvent.VK_A) leftPressed = true;
        if(code == KeyEvent.VK_RIGHT|| code == KeyEvent.VK_D) rightPressed = true;
    }

    /**
     * sets boolean values according to which key is currently not pressed.
     * @param e input from the keyboard.
     */
    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        if(code == KeyEvent.VK_UP || code == KeyEvent.VK_W) upPressed       = false;
        if(code == KeyEvent.VK_DOWN || code == KeyEvent.VK_S) downPressed   = false;
        if(code == KeyEvent.VK_LEFT|| code == KeyEvent.VK_A) leftPressed    = false;
        if(code == KeyEvent.VK_RIGHT|| code == KeyEvent.VK_D) rightPressed  = false;
    }
}
