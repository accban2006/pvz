package game;
import java.awt.*;
import java.util.*;

import plant.Plants;
import plant.Sun;
import plant.bullet;
import plant.ice_bullet;
import zombie.zombie;

/**
 * Handles the main game logic, object lists, and user interactions for the Plants vs. Zombies field.
 */
public class gamefield {

    plantsvszombies maingame;

    ArrayList<Sun> slList;           // List of sunlight objects
    ArrayList<zombie> zbList;        // List of zombies
    ArrayList<bullet> blList;        // List of regular bullets
    ArrayList<ice_bullet> ibList;    // List of ice bullets
    ArrayList<card> cdList;          // List of plant cards

    background background;           // Game background
    Plants[][] plants = new Plants[6][9]; // Plant grid (6 rows, 9 columns)
    Plants peashooter, sunflower, nut, ice_peashooter; // References to plants for dragging

    int sunlight_value = 50;          // Sunlight counter
    int zombie_die_number;            // Number of zombies defeated
    int raw_zombie_number;            // Initial number of zombies
    int zombies_number;               // Number of zombies yet to spawn
    int zombie_random_number;         // Randomness for zombie spawn
    SoundAndMusic BGM;                // Background music manager

    /**
     * Constructor. Connects this field to the main game instance.
     * @param maingame Reference to the main plantsvszombies game class.
     */
    public gamefield(plantsvszombies maingame) {
        this.maingame = maingame;
    }

    /**
     * Initializes a new game session with a set number of zombies and randomness.
     * Sets up all lists, plant cards, background, and starts BGM.
     * @param zombie_number Number of zombies for this session.
     * @param zombie_random_number Randomness parameter for zombie spawn.
     */
    void newGame(int zombie_number, int zombie_random_number) {
        this.zombies_number = zombie_number;
        this.zombie_random_number = zombie_random_number;
        raw_zombie_number = zombie_number;
        zombie_die_number = 0;
        slList = new ArrayList<Sun>();
        zbList = new ArrayList<zombie>();
        blList = new ArrayList<bullet>();
        cdList = new ArrayList<card>();
        ibList = new ArrayList<ice_bullet>();

        cdList.add(new card(0, "sunflower"));
        cdList.add(new card(1, "peashooter"));
        cdList.add(new card(2, "nut"));
        cdList.add(new card(3, "iceshooter"));

        background = new background();

        peashooter = new Plants();
        sunflower = new Plants();
        nut = new Plants();
        ice_peashooter = new Plants();

        plants = new Plants[6][9];

        sunlight_value = 50;
        BGM = new SoundAndMusic("music/BGM.wav");
        BGM.StartPlay_BGM();
    }

    /**
     * Handles mouse click logic: selecting cards, planting, collecting sun, pausing, and quitting.
     * @param x Mouse X coordinate.
     * @param y Mouse Y coordinate.
     */
    void mouseclick(int x, int y) {
        // Handle plant card clicks and planting logic
        for (int a = 0; a < cdList.size(); a++) {
            // Nut card logic
            if (cdList.get(a).cardname.equals("nut") && cdList.get(a).canbuyornot == 1) {
                int ah = (y - 81) / 100;
                int al = (x - 34) / 80;
                Plants newnut = new Plants(2, al * 80, ah * 100);
                if (plants[ah][al] == null) {
                    plants[ah][al] = newnut;
                    sunlight_value -= 50;
                    cdList.get(a).canbuyornot = 0;
                    nut.clear();
                }
            }
            if (cdList.get(a).cardname.equals("nut") && cdList.get(a).if_pressed(x, y) && sunlight_value >= 50) {
                cdList.get(a).canbuyornot++;
                cdList.get(a).canbuyornot %= 2;
                if (nut == null) nut = new Plants(2, x, y, 3);
                else nut.nut_put();
            }
            // Ice shooter card logic
            if (cdList.get(a).cardname.equals("iceshooter") && cdList.get(a).canbuyornot == 1) {
                int ah = (y - 81) / 100;
                int al = (x - 34) / 80;
                Plants newice = new Plants(3, al * 80, ah * 100);
                if (plants[ah][al] == null) {
                    plants[ah][al] = newice;
                    sunlight_value -= 150;
                    cdList.get(a).canbuyornot = 0;
                    ice_peashooter.clear();
                }
            }
            if (cdList.get(a).cardname.equals("iceshooter") && cdList.get(a).if_pressed(x, y) && sunlight_value >= 150) {
                cdList.get(a).canbuyornot++;
                cdList.get(a).canbuyornot %= 2;
                if (ice_peashooter == null) ice_peashooter = new Plants(3, x, y, 3);
                else ice_peashooter.ice_put();
            }
            // Sunflower card logic
            if (cdList.get(a).cardname.equals("sunflower") && cdList.get(a).canbuyornot == 1) {
                int ah = (y - 81) / 100;
                int al = (x - 34) / 80;
                Plants newsun = new Plants(1, al * 80, ah * 100);
                if (plants[ah][al] == null) {
                    plants[ah][al] = newsun;
                    sunlight_value -= 25;
                    cdList.get(a).canbuyornot = 0;
                    sunflower.clear();
                }
            }
            if (cdList.get(a).cardname.equals("sunflower") && cdList.get(a).if_pressed(x, y) && sunlight_value >= 25) {
                cdList.get(a).canbuyornot++;
                cdList.get(a).canbuyornot %= 2;
                if (sunflower == null) sunflower = new Plants(1, x, y, 3);
                else sunflower.sun_put();
            }
            // Peashooter card logic
            if (cdList.get(a).cardname.equals("peashooter") && cdList.get(a).canbuyornot == 1) {
                int ah = (y - 81) / 100;
                int al = (x - 34) / 80;
                Plants newpes = new Plants(0, al * 80, ah * 100);
                if (plants[ah][al] == null) {
                    plants[ah][al] = newpes;
                    sunlight_value -= 100;
                    cdList.get(a).canbuyornot = 0;
                    peashooter.clear();
                }
            }
            if (cdList.get(a).cardname.equals("peashooter") && cdList.get(a).if_pressed(x, y) && sunlight_value >= 100) {
                cdList.get(a).canbuyornot++;
                cdList.get(a).canbuyornot %= 2;
                if (sunflower == null) peashooter = new Plants(0, x, y, 3);
                else peashooter.shooter_put();
            }
        }
        // Collect sunlight by clicking on it
        for (int g = 0; g < slList.size(); g++) {
            if (slList.get(g).ifclicked(x, y)) {
                slList.remove(g);
                sunlight_value += 25;
            }
        }
        // Quit game button
        if (new Rectangle(785, 0, 50, 50).contains(x, y)) {
            System.exit(0);
        }
        // Pause game button
        if (new Rectangle(710, 0, 50, 50).contains(x, y)) {
            maingame.runstate = 1;
        }
        // Resume game button
        if (new Rectangle(625, 0, 50, 50).contains(x, y)) {
            maingame.runstate = 0;
        }
    }

    /**
     * Updates the position of plant objects when dragging with the mouse.
     * @param mx Mouse X coordinate.
     * @param my Mouse Y coordinate.
     */
    public void mouse_move(int mx, int my) {
        if (peashooter != null && peashooter.state == 3) {
            peashooter.x = mx;
            peashooter.y = my;
        }
        if (ice_peashooter != null && ice_peashooter.state == 3) {
            ice_peashooter.x = mx;
            ice_peashooter.y = my;
        }
        if (nut != null && nut.state == 3) {
            nut.x = mx;
            nut.y = my;
        }
        if (sunflower != null && sunflower.state == 3) {
            sunflower.x = mx;
            sunflower.y = my;
        }
    }

    /**
     * Draws the entire game panel: background, cards, plants, zombies, bullets, sunlight, etc.
     * @param g The graphics context to draw on.
     */
    public void gamepanel(Graphics g) {
        background.runbackground(g);

        // Draw plant cards depending on sunlight
        if (sunlight_value >= 25) cdList.get(0).showinshop_canbuy(g);
        else cdList.get(0).showinshop_cannotbuy(g);

        if (sunlight_value >= 100) cdList.get(1).showinshop_canbuy(g);
        else cdList.get(1).showinshop_cannotbuy(g);

        if (sunlight_value >= 50) cdList.get(2).showinshop_canbuy(g);
        else cdList.get(2).showinshop_cannotbuy(g);

        if (sunlight_value >= 150) cdList.get(3).showinshop_canbuy(g);
        else cdList.get(3).showinshop_cannotbuy(g);

        // Draw all plants on the field
        for (int h = 0; h < 6; h++) {
            for (int l = 0; l < 9; l++) {
                if (plants[h][l] != null) {
                    plants[h][l].show(g);
                }
            }
        }
        // Draw dragged plants
        if (peashooter != null) peashooter.shopter_put_show(g);
        if (sunflower != null) sunflower.sun_put_show(g);
        if (nut != null) nut.nut_put_show(g);
        if (ice_peashooter != null) ice_peashooter.ice_put_show(g);

        // Draw all zombies
        for (int a = 0; a < zbList.size(); a++) {
            zbList.get(a).show(g);
        }
        // Draw all bullets
        for (int a = 0; a < blList.size(); a++) {
            blList.get(a).image(g);
        }
        // Draw all ice bullets
        for (int a = 0; a < ibList.size(); a++) {
            ibList.get(a).image(g);
        }
        // Draw all sunlight
        for (int ge = 0; ge <= slList.size() - 1; ge = ge + 1) {
            slList.get(ge).paintComponent(g);
        }
        // Draw sunlight value
        g.setFont(new Font("����", 0, 15));
        g.drawString("" + sunlight_value, 40, 80);
    }

    /**
     * Main game timer logic: spawns zombies, updates objects, handles win/lose, and sunlight drops.
     * Should be called periodically (e.g., in a timer loop).
     * @throws Exception For thread sleep and sound errors.
     */
    public void timer() throws Exception {
        // Randomly spawn different types of zombies
        if (new Random().nextInt(2400) < zombie_random_number && zombies_number > 0) {
            int x = 800;
            int y = 100 * new Random().nextInt(6);
            zombie newzombie = new normalzombie(x, y);
            zbList.add(newzombie);
            zombies_number--;
        }
        if (new Random().nextInt(2400) < zombie_random_number && zombies_number > 0) {
            int x = 800;
            int y = 100 * new Random().nextInt(6);
            zombie newzombie = new redzombie(x, y);
            zbList.add(newzombie);
            zombies_number--;
        }
        if (new Random().nextInt(2400) < zombie_random_number && zombies_number > 0) {
            int x = 800;
            int y = 100 * new Random().nextInt(6);
            zombie newzombie = new yellowzombie(x, y);
            zbList.add(newzombie);
            zombies_number--;
        }
        if (new Random().nextInt(2400) < zombie_random_number && zombies_number > 0) {
            int x = 800;
            int y = 100 * new Random().nextInt(6);
            zombie newzombie = new darkzombie(x, y);
            zbList.add(newzombie);
            zombies_number--;
        }

        // Update all bullets
        for (int x = 0; x <= blList.size() - 1; x++) {
            blList.get(x).Action(zbList);
        }
        // Update all ice bullets
        for (int x = 0; x <= ibList.size() - 1; x++) {
            ibList.get(x).Action(zbList);
        }

        // Update all plants and remove dead ones
        for (int h = 0; h < 6; h++) {
            for (int l = 0; l < 9; l++) {
                if (plants[h][l] != null) {
                    plants[h][l].action(blList, ibList, slList);
                    if (plants[h][l].state == 2)
                        plants[h][l] = null;
                }
            }
        }
        // Update all zombies and remove dead ones
        for (int x = 0; x <= zbList.size() - 1; x++) {
            zbList.get(x).action(plants);
            if (zbList.get(x).getstate() == 4) {
                zombie_die_number++;
                zbList.remove(x);
            }
        }
        // Check if any zombie reached the left end (lose condition)
        for (int x = 0; x <= zbList.size() - 1; x++) {
            if (zbList.get(x).getx() < -200) {
                BGM.StopPlay_BGM();
                Thread.sleep(200);
                SoundAndMusic a = new SoundAndMusic("music/laugh.wav");
                a.playSound("music/laugh.wav");
                maingame.pvz_game = null;
                maingame.pvz_die = new diefield(maingame);
            }
        }
        // Check if all zombies are dead (win condition)
        if (zombie_die_number == raw_zombie_number) {
            BGM.StopPlay_BGM();
            SoundAndMusic a = new SoundAndMusic("music/winner.wav");
            a.playSound("music/winner.wav");
            Thread.sleep(1000);
            maingame.pvz_game = null;
            maingame.pvz_win = new winfield(maingame);
        }
        // Randomly drop new sunlight
        if (new Random().nextInt(600) < 7) {
            Sun sl = new Sun();
            slList.add(sl);
        }
        // Update all sunlight objects and remove collected ones
        for (int ge = 0; ge <= slList.size() - 1; ge = ge + 1) {
            slList.get(ge).drop();
            if (slList.get(ge).state == 2) {
                slList.remove(ge);
            }
        }
    }
}