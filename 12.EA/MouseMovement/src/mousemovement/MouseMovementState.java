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
interface MouseMovementState {
    public void drag();
    public void move();
    public void stop();
}
