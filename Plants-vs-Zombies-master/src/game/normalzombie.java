package game;

import java.awt.Graphics;
import java.awt.Rectangle;
import javax.swing.ImageIcon;

import zombie.zombie;

/*
 * Implements the normal zombie's movement (move), eating (eat), and dying (die) behavior.
 * Each action uses x, x_show, x_anime, x_action methods and a state value to manage its logic and animation.
 * x_show: draws the animation frame; x_anime: updates the animation frame; x_action: core logic for the state.
 * 12.30
 */
public class normalzombie extends zombie {
    int x, y, health, page;
    int state; // 1: move, 2: eat, 3: die, 4: clear
    SoundAndMusic a = new SoundAndMusic("music/eat.wav");

    public normalzombie(int x, int y) {
        this.y = y;
        this.x = x;
        move();
        health = 100;
    }
    public SoundAndMusic a() {
        return a;
    }

    public boolean meetwithplant(Plants[][] plants) {
        for (int h = 0; h < 6; h++) {
            for (int l = 0; l < 9; l++) {
                if (plants[h][l] != null && new Rectangle(x + 34, y + 81, 80, 100)
                        .intersects(plants[h][l].x - 10, plants[h][l].y + 81, 70, 70)) {
                    return true;
                }
            }
        }
        return false;
    }

    /*
     * Implements zombie move
     */
    public void move() {
        state = 1;
    }

    public void move_show(Graphics g) {
        g.drawImage((new ImageIcon("Image/zombie/Frame" + page + ".png")).getImage(), x, y, null);
    }

    public void move_anime() {
        if (page == 17) page = 0;
        else {
            page++;
        }
    }

    public void move_action(Plants[][] plants) {
        x -= 2;
        if (meetwithplant(plants)) {
            eat();
        }
        if (health <= 0) {
            die();
        }
    }

    /*
     * Implements zombie eat
     */
    public void eat() {
        state = 2;
        page = 0;
    }

    public void eat_anime() {
        if (page == 17) {
            page = 0;
            a.playSound("music/eat.wav");
        } else {
            page++;
        }
    }

    public void eat_show(Graphics g) {
        g.drawImage((new ImageIcon("Image/zombieeat/Frame" + page + ".png")).getImage(), x, y, null);
    }

    public void eat_action(Plants[][] plants) {
        for (int h = 0; h < 6; h++) {
            for (int l = 0; l < 9; l++) {
                if (plants[h][l] != null && new Rectangle(x + 34, y + 81, 80, 100)
                        .intersects(plants[h][l].x - 10, plants[h][l].y + 81, 70, 70)) {
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

    // ===========================ice===============================
    int ice = 0, iice = 0, iiice = 0, iiiice = 0;

    public void imove() {
        state = 10;
    }

    public void imove_show(Graphics g) {
        g.drawImage((new ImageIcon("Image/zombie/Frame" + page + ".png")).getImage(), x, y, null);
    }

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

    /*
     * Implements zombie eat (ice state)
     */
    public void ieat() {
        state = 20;
        page = 0;
    }

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

    public void ieat_show(Graphics g) {
        g.drawImage((new ImageIcon("Image/zombieeat/Frame" + page + ".png")).getImage(), x, y, null);
    }

    public void ieat_action(Plants[][] plants) {
        for (int h = 0; h < 6; h++) {
            for (int l = 0; l < 9; l++) {
                if (plants[h][l] != null && new Rectangle(x + 34, y + 81, 80, 100)
                        .intersects(plants[h][l].x - 10, plants[h][l].y + 81, 70, 70)) {
                    if (iiiice == 0) {
                        plants[h][l].health -= 2;
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

    /*
     * Implements zombie die
     */
    public void die() {
        state = 3;
        page = 0;
    }

    public void die_show(Graphics g) {
        g.drawImage((new ImageIcon("Image/zombiedie/Frame" + page + ".png")).getImage(), x, y, null);
        g.drawImage((new ImageIcon("Image/zombiehead/Frame" + page + ".png")).getImage(), x, y, null);
    }

    public void die_anime() {
        if (page == 11) {
            clear();
        } else {
            page++;
        }
    }

    /*
     * Clears zombie
     */
    public void clear() {
        state = 4;
    }

    /*
     * -----------------------------------Action Dispatcher----------------------------------------
     */
    public void show(Graphics g) {
        if (state == 1) move_show(g);
        if (state == 2) eat_show(g);
        if (state == 3) die_show(g);
        if (state == 10) imove_show(g);
        if (state == 20) ieat_show(g);
    }

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

    public int getx() {
        return x;
    }

    public int gety() {
        return y;
    }

    public void sethealth(int a) {
        health -= a;
    }

    public int getstate() {
        return state;
    }

    public void ice() {
        if (state == 1) state = 10;
        if (state == 2) state = 20;
    }
}
