package game;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.util.ArrayList;
import javax.swing.ImageIcon;

/**
 * Represents a bullet fired by a plant in the game.
 */
public class bullet {
    int x;
    int y;
    int state;

    /**
     * Default constructor (does nothing).
     */
    public bullet() {
    }

    /**
     * Constructs a bullet at position (x, y) and sets it to fly.
     * @param x The initial x position of the bullet.
     * @param y The initial y position of the bullet.
     */
    public bullet(int x, int y) {
        this.x = x;
        this.y = y;
        fly();
    }

    /**
     * Sets the bullet state to flying (state=1).
     */
    private void fly() {
        state = 1;
    }

    /**
     * Checks if this bullet collides with a zombie.
     * If so, plays the hit sound effect.
     * @param zb The zombie to check collision with.
     * @return true if the bullet hits the zombie, false otherwise.
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
     * Draws the bullet image on the screen if it is flying.
     * @param g The graphics context to draw on.
     */
    private void flyimage(Graphics g) {
        Image tu = (new ImageIcon("Image/bullet/bullet.png")).getImage();
        g.drawImage(tu, x + 58, y + 80, null);
    }

    /**
     * Moves the bullet, checks for collision with zombies, and clears it if needed.
     * @param zbList The list of zombies to check for collision.
     */
    private void flyaction(ArrayList<zombie> zbList) {
        x += 5;
        for (int x = 0; x < zbList.size(); x++) {
            if (a(zbList.get(x))) {
                zbList.get(x).sethealth(10);
                clear();
            }
        }
        if (x >= 800) {
            clear();
        }
    }

    /**
     * Clears the bullet (sets its state to 2, meaning inactive).
     */
    private void clear() {
        state = 2;
    }

    /**
     * Updates the bullet's action (movement and collision) if it is flying.
     * @param zbList The list of zombies to check for collision.
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