package game;
import java.awt.*;
import javax.swing.ImageIcon;

/**
 * The preparation panel shown before starting the actual game.
 * Handles background music and starting new games with different difficulties.
 */
public class preparepanel {
    plantsvszombies i;
    SoundAndMusic BGM = new SoundAndMusic("music/gaming.wav");

    /**
     * Constructor. Starts the preparation background music.
     * @param i Reference to the main game class.
     */
    public preparepanel(plantsvszombies i) {
        this.i = i;
        BGM.StartPlay_BGM();
    }

    /**
     * Draws the preparation panel background image.
     * @param g Graphics context.
     */
    void preparepanel(Graphics g) {
        Image tu = (new ImageIcon("Image/start.png")).getImage();
        g.drawImage(tu, 0, 0, null);
    }

    /**
     * Handles mouse click events on the preparation panel.
     * Starts a new game with different difficulty based on which area is clicked,
     * or exits the game if the quit area is clicked.
     * @param mx Mouse X coordinate.
     * @param my Mouse Y coordinate.
     */
    public void mouseclick(int mx, int my) {
        // Easy mode button
        if (new Rectangle(266, 612, 112, 38).contains(mx, my)) {
            BGM.StopPlay_BGM();
            SoundAndMusic a = new SoundAndMusic("music/laugh.wav");
            a.playSound("music/laugh.wav");
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            i.pvz_pre = null;
            i.pvz_game = new gamefield(i);
            i.pvz_game.newGame(10, 5);
        }
        // Normal mode button
        if (new Rectangle(381, 612, 91, 38).contains(mx, my)) {
            BGM.StopPlay_BGM();
            SoundAndMusic a = new SoundAndMusic("music/laugh.wav");
            a.playSound("music/laugh.wav");
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            i.pvz_pre = null;
            i.pvz_game = new gamefield(i);
            i.pvz_game.newGame(20, 7);
        }
        // Hard mode button
        if (new Rectangle(477, 612, 112, 38).contains(mx, my)) {
            BGM.StopPlay_BGM();
            SoundAndMusic a = new SoundAndMusic("music/laugh.wav");
            a.playSound("music/laugh.wav");
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            i.pvz_pre = null;
            i.pvz_game = new gamefield(i);
            i.pvz_game.newGame(30, 10);
        }
        // Quit button
        if (new Rectangle(785, 0, 50, 50).contains(mx, my)) {
            System.exit(0);
        }
    }
}