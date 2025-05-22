package game;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.util.ArrayList;
import javax.swing.ImageIcon;

/**
 * Represents an ice bullet fired by a plant.
 * Handles flying, collision with zombies, and rendering.
 */
public class ice_bullet {
    int x;
    int y;
    int state; // 1: flying, 2: disappeared

    /**
     * Default constructor (does nothing).
     */
    public ice_bullet() {
    }

    /**
     * Constructor: Creates an ice bullet at the given (x, y) and sets it to fly.
     * @param x Initial x position.
     * @param y Initial y position.
     */
    public ice_bullet(int x, int y) {
        this.x = x;
        this.y = y;
        fly();
    }

    /**
     * Sets the bullet state to flying.
     */
    private void fly() {
        state = 1;
    }

    /**
     * Checks if the bullet hits a zombie. If so, plays hit sound.
     * @param zb The zombie to check collision with.
     * @return true if bullet hits the zombie, false otherwise.
     */
    private boolean a(zombie zb) {
        if (new Rectangle(x, y, 30, 30).intersects(zb.getx(), zb.gety(), 80, 100)) {
            SoundAndMusic a = new SoundAndMusic("music/peng.wav");
            a.playSound("music/peng.wav");
            return true;
        }
        return false;
    }

    /**
     * Draws the ice bullet image at its current position.
     * @param g The graphics context to draw on.
     */
    private void flyimage(Graphics g) {
        Image tu = (new ImageIcon("Image/bullet/icebullet.png")).getImage();
        g.drawImage(tu, x + 58, y + 80, null);
    }

    /**
     * Handles the bullet's movement, checks for collision with zombies,
     * applies freezing and damage effect, and removes bullet if needed.
     * @param zbList List of zombies.
     */
    private void flyaction(ArrayList<zombie> zbList) {
        x += 5;
        for (int x = 0; x < zbList.size(); x++) {
            if (a(zbList.get(x))) {
                zbList.get(x).sethealth(5);
                zbList.get(x).ice();
                clear();
            }
        }
        if (x >= 800) {
            clear();
        }
    }

    /**
     * Sets the bullet state to disappeared (inactive).
     */
    private void clear() {
        state = 2;
    }

    /**
     * Updates the bullet's action (moves and checks for collisions) if flying.
     * @param zbList List of zombies.
     */
    public void Action(ArrayList<zombie> zbList) {
        if (state == 1) flyaction(zbList);
    }

    /**
     * Draws the bullet if it is flying.
     * @param g The graphics context to draw on.
     */
    public void image(Graphics g) {
        if (state == 1) flyimage(g);
    }
}