package game;
import java.awt.*;
import javax.swing.ImageIcon;

/**
 * Represents the "die" field or screen in the game.
 */
public class diefield {
    plantsvszombies i;

    /**
     * Constructor. Associates this diefield with the main game instance.
     * @param i Reference to the main plantsvszombies instance.
     */
    public diefield(plantsvszombies i) {
        this.i = i;
    }

    /**
     * Draws the "die" background image on the screen.
     * @param g The graphics context to draw on.
     */
    void draw(Graphics g) {
        Image tu = (new ImageIcon("Image/Background/die.png")).getImage();
        g.drawImage(tu, 0, 0, null);
    }

    /**
     * Handles a mouse click on the diefield.
     * If the click is within the 1000x1000 area, resets the diefield and
     * creates a new preparepanel.
     * @param mx Mouse X coordinate.
     * @param my Mouse Y coordinate.
     */
    public void mouseclick(int mx, int my) {
        if(new Rectangle(0,0,1000,1000).contains(mx, my)) {
            i.pvz_die = null;
            i.pvz_pre = new preparepanel(i);
        }
    }
}