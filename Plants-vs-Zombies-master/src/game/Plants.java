package game;
import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;
import javax.swing.ImageIcon;

/**
 * Plants class for different plant types in the game.
 * Handles animation, health, state, and actions for Peashooter, Sunflower, Wall-nut, and Frost Bean.
 */
public class Plants {
    public int x;
    public int y;
    int page;
    int health;
    int timer;
    /**
     * 0 - Peashooter, 1 - Sunflower, 2 - Wall-nut, 3 - Frost Bean
     */
    int type;
    public int state; // 1: Move, 2: Remove, 3: Place

    /**
     * Default constructor.
     */
    public Plants() {
    }

    /**
     * Constructor for plant with position (used for Peashooter).
     * @param x X coordinate
     * @param y Y coordinate
     */
    public Plants(int x, int y) {
        health = 300;
        this.x = x;
        this.y = y;
        shooter_move();
    }

    /**
     * Constructor for plant with type and position.
     * Sets up initial state for the plant based on type.
     * @param type Plant type (0: Peashooter, 1: Sunflower, 2: Wall-nut, 3: Frost Bean)
     * @param x X coordinate
     * @param y Y coordinate
     */
    public Plants(int type, int x, int y) {
        this.type = type;
        this.x = x;
        this.y = y;
        if (type == 0) shooter_move();
        if (type == 1) sun_move();
        if (type == 2) nut_health();
        if (type == 3) ice_move();
    }

    /**
     * Constructor for plant with type, position, and state (used for placing plants).
     * @param type Plant type
     * @param x X coordinate
     * @param y Y coordinate
     * @param state Initial state
     */
    public Plants(int type, int x, int y, int state) {
        this.type = type;
        this.state = state;
        this.x = x;
        this.y = y;
        if (type == 0) shooter_put();
        if (type == 1) sun_put();
        if (type == 2) nut_put();
        if (type == 3) ice_put();
    }

    /**
     * Marks the plant for removal from the field (state = 2).
     */
    public void clear() {
        state = 2;
    }

    /**
     * Increments the Peashooter's firing timer.
     */
    public void shooter_timer() {
        timer++;
    }

    /**
     * Checks if Peashooter's firing timer is over (ready to fire).
     * @return true if timer > 17, false otherwise
     */
    public boolean shooter_timer_over() {
        return timer > 17;
    }

    /* ==== Peashooter methods ==== */

    /**
     * Initializes Peashooter attributes for move state.
     */
    public void shooter_move() {
        health = 300;
        state = 1;
    }

    /**
     * Draws Peashooter animation frame.
     * @param g Graphics context
     */
    public void shooter_move_show(Graphics g) {
        g.drawImage((new ImageIcon("Image/peashooter/Frame" + page + ".png")).getImage(), 34 + x, 81 + y, null);
    }

    /**
     * Updates Peashooter animation, handles firing, and checks health.
     * @param blList List of bullets to add to if firing
     */
    public void shooter_move_anime(ArrayList<bullet> blList) {
        if (page == 12) page = 0;
        else page++;
        if (health <= 0) clear();
        shooter_timer();
        if (shooter_timer_over()) {
            bullet newbullet = new bullet(x, y);
            blList.add(newbullet);
            timer = 0;
        }
    }

    /**
     * Sets Peashooter to placement state (state = 3).
     */
    public void shooter_put() {
        state = 3;
    }

    /**
     * Draws Peashooter while being placed on the field.
     * @param g Graphics context
     */
    public void shopter_put_show(Graphics g) {
        if (state == 3) {
            Image tu = (new ImageIcon("Image/peashooter/Frame0.png")).getImage();
            g.drawImage(tu, x, y, null);
            Image tu1 = (new ImageIcon("Image/plantput/pea.png")).getImage();
            g.drawImage(tu1, 34 + ((x - 34) / 80) * 80, 81 + ((y - 81) / 100) * 100, null);
        }
    }

    /* ==== Frost Bean methods ==== */

    /**
     * Initializes Frost Bean for move state.
     */
    public void ice_move() {
        health = 300;
        state = 1;
    }

    /**
     * Draws Frost Bean animation frame.
     * @param g Graphics context
     */
    public void ice_move_show(Graphics g) {
        g.drawImage((new ImageIcon("Image/icepeashooter/Frame" + page + ".png")).getImage(), 34 + x, 81 + y, null);
    }

    /**
     * Updates Frost Bean animation, handles ice bullet firing, and checks health.
     * @param ibList List of ice bullets to add to if firing
     */
    public void ice_move_anime(ArrayList<ice_bullet> ibList) {
        if (page == 14) page = 0;
        else page++;
        if (health <= 0) clear();
        shooter_timer();
        if (shooter_timer_over()) {
            ice_bullet newbullet = new ice_bullet(x, y);
            ibList.add(newbullet);
            timer = 0;
        }
    }

    /**
     * Sets Frost Bean to placement state (state = 3).
     */
    public void ice_put() {
        state = 3;
    }

    /**
     * Draws Frost Bean while being placed on the field.
     * @param g Graphics context
     */
    public void ice_put_show(Graphics g) {
        if (state == 3) {
            Image tu = (new ImageIcon("Image/icepeashooter/Frame0.png")).getImage();
            g.drawImage(tu, x, y, null);
            Image tu1 = (new ImageIcon("Image/plantput/ice.png")).getImage();
            g.drawImage(tu1, 34 + ((x - 34) / 80) * 80, 81 + ((y - 81) / 100) * 100, null);
        }
    }

    /* ==== Sunflower methods ==== */

    /**
     * Initializes Sunflower for move state.
     */
    public void sun_move() {
        health = 300;
        state = 1;
    }

    /**
     * Draws Sunflower animation frame.
     * @param g Graphics context
     */
    public void sun_move_show(Graphics g) {
        g.drawImage((new ImageIcon("Image/sunflower/Frame" + page + ".png")).getImage(), 34 + x, 81 + y, null);
    }

    /**
     * Updates Sunflower animation, handles sunlight generation, and checks health.
     * @param slList List of sunlight objects to add to if generating
     */
    public void sun_move_anime(ArrayList<Sun> slList) {
        if (page == 17) page = 0;
        else page++;
        if (health <= 0) clear();
        sun_timer();
        if (sun_timer_over()) {
            Sun newsun = new Sun(x, y);
            slList.add(newsun);
            timer = 0;
        }
    }

    /**
     * Sets Sunflower to placement state (state = 3).
     */
    public void sun_put() {
        state = 3;
    }

    /**
     * Draws Sunflower while being placed on the field.
     * @param g Graphics context
     */
    public void sun_put_show(Graphics g) {
        if (state == 3) {
            Image tu = (new ImageIcon("Image/sunflower/Frame0.png")).getImage();
            g.drawImage(tu, x, y, null);
            Image tu1 = (new ImageIcon("Image/plantput/sun.png")).getImage();
            g.drawImage(tu1, 34 + ((x - 34) / 80) * 80, 81 + ((y - 81) / 100) * 100, null);
        }
    }

    /**
     * Increments Sunflower's timer for sunlight generation.
     */
    public void sun_timer() {
        timer++;
    }

    /**
     * Checks if Sunflower's timer for sunlight is over (ready to produce).
     * @return true if timer > 83, false otherwise
     */
    public boolean sun_timer_over() {
        return timer > 83;
    }

    /* ==== Wall-nut methods ==== */
    /**
     * Initializes Wall-nut as intact (state = 1) and sets health.
     */
    public void nut_health() {
        state = 1;
        health = 1000;
    }

    /**
     * Draws Wall-nut in intact state.
     * @param g Graphics context
     */
    private void nut_health_show(Graphics g) {
        g.drawImage((new ImageIcon("Image/nut1/Frame" + page + ".png")).getImage(), 34 + x, 81 + y, null);
    }

    /**
     * Updates Wall-nut in intact state, transitions to hurt if health low.
     */
    private void nut_health_move() {
        if (page == 15) page = 0;
        else page++;
        if (health <= 600) {
            nut_hurt();
        }
    }

    /**
     * Sets Wall-nut to hurt state (state = 4).
     */
    private void nut_hurt() {
        state = 4;
        page = 0;
    }

    /**
     * Draws Wall-nut in hurt state.
     * @param g Graphics context
     */
    private void nut_hurt_show(Graphics g) {
        g.drawImage((new ImageIcon("Image/nut2/Frame" + page + ".png")).getImage(), 34 + x, 81 + y, null);
    }

    /**
     * Updates Wall-nut in hurt state, transitions to badly hurt if health low.
     */
    private void nut_hurt_move() {
        if (page == 10) page = 0;
        else page++;
        if (health <= 300) {
            nut_badlyhurt();
        }
    }

    /**
     * Sets Wall-nut to badly hurt state (state = 5).
     */
    private void nut_badlyhurt() {
        state = 5;
        page = 0;
    }

    /**
     * Draws Wall-nut in badly hurt state.
     * @param g Graphics context
     */
    private void nut_badlyhurt_show(Graphics g) {
        g.drawImage((new ImageIcon("Image/nut3/Frame" + page + ".png")).getImage(), 34 + x, 81 + y, null);
    }

    /**
     * Updates Wall-nut in badly hurt state, clears if health <= 0.
     */
    private void nut_badlyhurt_move() {
        if (page == 14) page = 0;
        else page++;
        if (health <= 0) {
            clear();
        }
    }

    /**
     * Sets Wall-nut to placement state (state = 3).
     */
    public void nut_put() {
        state = 3;
    }

    /**
     * Draws Wall-nut while being placed on the field.
     * @param g Graphics context
     */
    public void nut_put_show(Graphics g) {
        if (state == 3) {
            Image tu = (new ImageIcon("Image/nut1/Frame0.png")).getImage();
            g.drawImage(tu, x, y, null);
            Image tu1 = (new ImageIcon("Image/plantput/nut.png")).getImage();
            g.drawImage(tu1, 34 + ((x - 34) / 80) * 80, 81 + ((y - 81) / 100) * 100, null);
        }
    }

    /**
     * Draws Wall-nut based on its current health state.
     * @param g Graphics context
     */
    void nut_show(Graphics g) {
        if (state == 1) {
            nut_health_show(g);
        }
        if (state == 4) {
            nut_hurt_show(g);
        }
        if (state == 5) {
            nut_badlyhurt_show(g);
        }
    }

    /**
     * Updates Wall-nut's animation and state based on health.
     */
    public void nut_action() {
        if (state == 1) {
            nut_health_move();
        }
        if (state == 4) {
            nut_hurt_move();
        }
        if (state == 5) {
            nut_badlyhurt_move();
        }
    }

    /* ==== General plant state management ==== */

    /**
     * Makes plant disappear from field (state = 2).
     */
    public void put_disappear() {
        state = 2;
    }

    /**
     * Sets plant to placement state (state = 3).
     */
    public void put() {
        state = 3;
    }

    /**
     * Draws the plant based on its type.
     * @param g Graphics context
     */
    public void show(Graphics g) {
        if (type == 0) {
            shooter_move_show(g);
        }
        if (type == 1) {
            sun_move_show(g);
        }
        if (type == 2) {
            nut_show(g);
        }
        if (type == 3) {
            ice_move_show(g);
        }
    }

    /**
     * Updates the plant's animation and logic based on its type.
     * @param blList List of bullets
     * @param ibList List of ice bullets
     * @param slList List of sunlight
     */
    public void action(ArrayList<bullet> blList, ArrayList<ice_bullet> ibList, ArrayList<Sun> slList) {
        if (type == 0) {
            shooter_move_anime(blList);
        }
        if (type == 1) {
            sun_move_anime(slList);
        }
        if (type == 2) {
            nut_action();
        }
        if (type == 3) {
            ice_move_anime(ibList);
        }
    }
}