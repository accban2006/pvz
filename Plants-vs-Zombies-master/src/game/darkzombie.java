package game;

import java.awt.Graphics;
import java.awt.Rectangle;
import javax.swing.ImageIcon;

/**
 * Represents a dark zombie with animation and action logic.
 */
public class darkzombie extends zombie {
    int x, y, health, page;
    int state;
    SoundAndMusic a = new SoundAndMusic("music/eat.wav");

    /**
     * Constructor. Initializes position and health, starts moving.
     * @param x X position
     * @param y Y position
     */
    public darkzombie(int x, int y) {
        this.y = y;
        this.x = x;
        move();
        health = 600;
    }

    /**
     * Returns the SoundAndMusic instance for this zombie.
     * @return SoundAndMusic object
     */
    public SoundAndMusic a() {
        return a;
    }

    /**
     * Checks if this zombie meets (collides with) any plant.
     * @param plants Plant grid
     * @return true if collides, false otherwise
     */
    public boolean meetwithplant(Plants[][] plants) {
        for (int h = 0; h < 6; h++) {
            for (int l = 0; l < 9; l++) {
                if (plants[h][l] != null &&
                    new Rectangle(x + 34, y + 81, 80, 100).intersects(plants[h][l].x - 10, plants[h][l].y + 81, 70, 70)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Sets the zombie state to moving.
     */
    public void move() {
        state = 1;
    }

    /**
     * Draws the moving zombie frame.
     * @param g Graphics context
     */
    public void move_show(Graphics g) {
        g.drawImage((new ImageIcon("Image/darkzombie/Frame" + page + ".png")).getImage(), x, y, null);
    }

    /**
     * Updates the moving animation frame.
     */
    public void move_anime() {
        if (page == 17) page = 0;
        else page++;
    }

    /**
     * Moves the zombie and checks for plant collision or death.
     * @param plants Plant grid
     */
    public void move_action(Plants[][] plants) {
        x -= 1;
        if (meetwithplant(plants)) {
            eat();
        }
        if (health <= 0) {
            die();
        }
    }

    /**
     * Sets the zombie state to eating and resets animation.
     */
    public void eat() {
        state = 2;
        page = 0;
    }

    /**
     * Updates the eating animation frame and plays sound.
     */
    public void eat_anime() {
        if (page == 17) {
            page = 0;
            a.playSound("music/eat.wav");
        } else {
            page++;
        }
    }

    /**
     * Draws the eating zombie frame.
     * @param g Graphics context
     */
    public void eat_show(Graphics g) {
        g.drawImage((new ImageIcon("Image/darkzombieeat/Frame" + page + ".png")).getImage(), x, y, null);
    }

    /**
     * Reduces plant health if colliding, transitions state based on conditions.
     * @param plants Plant grid
     */
    public void eat_action(Plants[][] plants) {
        for (int h = 0; h < 6; h++) {
            for (int l = 0; l < 9; l++) {
                if (plants[h][l] != null &&
                    new Rectangle(x + 34, y + 81, 80, 100).intersects(plants[h][l].x - 10, plants[h][l].y + 81, 70, 70)) {
                    plants[h][l].health -= 2;
                }
            }
        }
        if (!meetwithplant(plants)) {
            move();
        }
        if (health <= 0) {
            die();
        }
    }

    /**
     * Sets the zombie state to dying and resets animation.
     */
    public void die() {
        state = 3;
        page = 0;
    }

    /**
     * Draws the dying zombie and head animation.
     * @param g Graphics context
     */
    public void die_show(Graphics g) {
        g.drawImage((new ImageIcon("Image/darkzombiedie/Frame" + page + ".png")).getImage(), x, y, null);
        g.drawImage((new ImageIcon("Image/darkzombiehead/Frame" + page + ".png")).getImage(), x, y, null);
    }

    /**
     * Updates dying animation. Calls clear() when done.
     */
    public void die_anime() {
        if (page == 11) {
            clear();
        } else {
            page++;
        }
    }

    /**
     * Marks this zombie as cleared (inactive).
     */
    public void clear() {
        state = 4;
    }

    // ============ Ice (slow) mode logic ==============
    int ice = 0, iice = 0, iiice = 0, iiiice = 0;

    /**
     * Sets the zombie state to moving while iced.
     */
    public void imove() {
        state = 10;
    }

    /**
     * Draws the iced moving zombie.
     * @param g Graphics context
     */
    public void imove_show(Graphics g) {
        g.drawImage((new ImageIcon("Image/darkzombie/Frame" + page + ".png")).getImage(), x, y, null);
    }

    /**
     * Updates iced moving animation, slower frame rate.
     */
    public void imove_anime() {
        if (page == 17) page = 0;
        else {
            if (ice == 0) {
                page++;
                ice++;
                ice %= 2;
            } else {
                ice++;
                ice %= 2;
            }
        }
    }

    /**
     * Moves the zombie slower, checks for plant collision or death.
     * @param plants Plant grid
     */
    public void imove_action(Plants[][] plants) {
        if (iice == 0) {
            x -= 1;
            iice++;
            iice %= 2;
        } else {
            iice++;
            iice %= 2;
        }
        if (meetwithplant(plants)) {
            ieat();
        }
        if (health <= 0) {
            die();
        }
    }

    /**
     * Sets the zombie state to eating while iced, resets animation.
     */
    public void ieat() {
        state = 20;
        page = 0;
    }

    /**
     * Updates iced eating animation and plays sound on loop.
     */
    public void ieat_anime() {
        if (page == 17) {
            page = 0;
            a.playSound("music/eat.wav");
        } else {
            if (iiice == 0) {
                iiice++;
                iiice %= 2;
                page++;
            } else {
                iiice++;
                iiice %= 2;
            }
        }
    }

    /**
     * Draws the iced eating zombie.
     * @param g Graphics context
     */
    public void ieat_show(Graphics g) {
        g.drawImage((new ImageIcon("Image/darkzombieeat/Frame" + page + ".png")).getImage(), x, y, null);
    }

    /**
     * Reduces plant health more slowly while iced, transitions state as needed.
     * @param plants Plant grid
     */
    public void ieat_action(Plants[][] plants) {
        for (int h = 0; h < 6; h++) {
            for (int l = 0; l < 9; l++) {
                if (plants[h][l] != null &&
                    new Rectangle(x + 34, y + 81, 80, 100).intersects(plants[h][l].x - 10, plants[h][l].y + 81, 70, 70)) {
                    if (iiiice == 0) {
                        plants[h][l].health--;
                        iiiice++;
                        iiiice %= 2;
                    } else {
                        iiiice++;
                        iiiice %= 2;
                    }
                }
            }
        }
        if (!meetwithplant(plants)) {
            imove();
        }
        if (health <= 0) {
            die();
        }
    }

    /**
     * Draws the zombie based on its current state.
     * @param g Graphics context
     */
    public void show(Graphics g) {
        if (state == 1) move_show(g);
        if (state == 2) eat_show(g);
        if (state == 3) die_show(g);
        if (state == 10) imove_show(g);
        if (state == 20) ieat_show(g);
    }

    /**
     * Updates the zombie's animation and logic based on its state.
     * @param a Plant grid
     */
    public void action(Plants[][] a) {
        if (state == 1) {
            move_anime();
            move_action(a);
        }
        if (state == 2) {
            eat_anime();
            eat_action(a);
        }
        if (state == 3) {
            die_anime();
        }
        if (state == 10) {
            imove_anime();
            imove_action(a);
        }
        if (state == 20) {
            ieat_anime();
            ieat_action(a);
        }
    }

    /**
     * @return X position of the zombie.
     */
    public int getx() {
        return x;
    }

    /**
     * @return Y position of the zombie.
     */
    public int gety() {
        return y;
    }

    /**
     * Reduces zombie health by a specified amount.
     * @param a Amount to subtract from health.
     */
    public void sethealth(int a) {
        health -= a;
    }

    /**
     * @return Current state of the zombie (moving, eating, etc).
     */
    public int getstate() {
        return state;
    }

    /**
     * Freezes the zombie (switches to ice states).
     */
    public void ice() {
        if (state == 1) state = 10;
        if (state == 2) state = 20;
    }
}
