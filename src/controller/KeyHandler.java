package controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Handles the keys pressed. Used in the Controller
 */
public class KeyHandler implements KeyListener {

    private boolean upPressed, leftPressed, rightPressed, downPressed;
    private boolean xPressed, zPressed, spacePressed, controlPressed;

    private boolean upReleased;
    private boolean xReleased, zReleased, spaceReleased, controlReleased;

    public KeyHandler(){
        upReleased = xReleased = zReleased = spaceReleased = true;
    }

    /**
     * Necessary due to KeyListener implementation
     * @param e the event to be processed
     */
    @Override
    public void keyTyped(KeyEvent e) {}

    /**
     * Checks which key gets pressed and sets the corresponding "pressed" boolean to true
     * @param e the key event to be processed
     */
    @Override
    public void keyPressed(KeyEvent e) {

        switch (e.getKeyCode()){
            case KeyEvent.VK_UP -> upPressed = true;
            case KeyEvent.VK_LEFT -> leftPressed = true;
            case KeyEvent.VK_RIGHT -> rightPressed = true;
            case KeyEvent.VK_DOWN -> downPressed = true;
            case KeyEvent.VK_X -> xPressed = true;
            case KeyEvent.VK_Z -> zPressed = true;
            case KeyEvent.VK_SPACE -> spacePressed = true;
            case KeyEvent.VK_CONTROL -> controlPressed = true;
        }
        Controller.getInstance().handleMovement();
    }

    /**
     * Checks when a key is released and sets the corresponding "released" boolean to true and "pressed" boolean to false
     * @param e the event to be processed
     */
    @Override
    public void keyReleased(KeyEvent e) {

        switch (e.getKeyCode()){
            case KeyEvent.VK_UP -> {
                upReleased = true;
                upPressed = false;
            }
            case KeyEvent.VK_LEFT -> {
                leftPressed = false;
            }
            case KeyEvent.VK_RIGHT -> {
                rightPressed = false;
            }
            case KeyEvent.VK_DOWN -> {
                downPressed = false;
            }
            case KeyEvent.VK_X -> {
                xPressed = false;
                xReleased = true;
            }
            case KeyEvent.VK_Z -> {
                zPressed = false;
                zReleased = true;
            }
            case KeyEvent.VK_SPACE -> {
                spacePressed = false;
                spaceReleased = true;
            }
            case KeyEvent.VK_CONTROL -> {
                controlPressed = false;
                controlReleased = true;
            }
        };
        Controller.getInstance().handleMovement();
    }

    /**
     * @return whether the left arrow key is pressed
     */
    public boolean isLeftPressed() { return leftPressed; }

    /**
     * @return whether the right arrow key is pressed
     */
    public boolean isRightPressed() { return rightPressed; }

    /**
     * @return whether the up arrow key is pressed
     */
    public boolean isUpPressed() { return upPressed; }
    /**
     * @return whether the down arrow key is pressed
     */
    public boolean isDownPressed() { return downPressed; }

    /**
     * @return whether the left arrow key is pressed
     */
    public boolean isSpacePressed() { return spacePressed; }

    /**
     * @return whether the X key is pressed
     */
    public boolean isXPressed() { return xPressed; }

    /**
     * @return whether the Z key is pressed
     */
    public boolean isZPressed() { return zPressed; }

    /**
     * @return whether the ctrl key is pressed
     */
    public boolean isControlPressed() { return controlPressed; }

    /**
     * @return whether the Z key is released
     */
    public boolean isUpReleased() { return upReleased; }

    /**
     * @return whether the space key is released
     */
    public boolean isSpaceReleased() { return spaceReleased; }

    /**
     * @return whether the X key is released
     */
    public boolean isXReleased() { return xReleased; }

    /**
     * @return whether the Z key is released
     */
    public boolean isZReleased() { return zReleased; }

    /**
     * @return whether the ctrl key is released
     */
    public boolean isControlReleased() { return controlReleased; }

}
