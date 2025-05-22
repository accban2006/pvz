package game;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * Main game class for Plants vs Zombies.
 * Handles the main window, game state, panels, and event loops.
 */
public class plantsvszombies {
	
	int runstate=0; // 0: running, 1: paused
	gamefield pvz_game;         // Main game field
	preparepanel pvz_pre;       // Preparation panel before game starts
	diefield pvz_die;           // Panel shown when player loses
	winfield pvz_win;           // Panel shown when player wins
	mainPanel panel=null;       // Main JFrame panel
	mainTimer timer=null;       // Main timer thread
	int mx, my;                 // Mouse coordinates

	/**
	 * Entry point. Starts the game.
	 */
	public static void main(String[] args) {
		new plantsvszombies();
	}

	/**
	 * Starts a new game by showing the preparation panel.
	 */
	void newGame() {
		pvz_pre = new preparepanel(this);
	}

	/**
	 * Handles mouse click events and delegates to the appropriate panel.
	 * @param e The MouseEvent object.
	 */
	public void mouseclick(MouseEvent e) {
		if(pvz_game != null) pvz_game.mouseclick(mx, my);
		if(pvz_pre  != null) pvz_pre.mouseclick(mx, my);
	    if(pvz_die  != null) pvz_die.mouseclick(mx, my);
		if(pvz_win  != null) pvz_win.mouseclick(mx, my);
	}

	/**
	 * Handles mouse release event (currently empty).
	 * @param e The MouseEvent object.
	 */
	void mouserelease(MouseEvent e) {}

	/**
	 * Handles mouse movement event and updates dragging for plants in game.
	 * @param e The MouseEvent object.
	 */
	void mousemove(MouseEvent e) {
		if(pvz_game != null) {
			pvz_game.mouse_move(mx, my);
		}
	}

	/**
	 * Handles mouse drag event (currently empty).
	 * @param e The MouseEvent object.
	 */
	void mousedrag(MouseEvent e) {}

	/**
	 * Draws the current active panel (game, preparation, win, or die).
	 * @param g The Graphics context.
	 */
	void panel(Graphics g) {
		if(pvz_game != null) {
			pvz_game.gamepanel(g);
		}
		if(pvz_pre != null) {
			pvz_pre.preparepanel(g);
		}
		if(pvz_die != null) {
			pvz_die.draw(g);
		}
		if(pvz_win != null) {
			pvz_win.winfield(g);
		}
	}

	/**
	 * Calls the timer logic for the active game panel.
	 * @throws Exception If the game logic throws.
	 */
	void timer() throws Exception {
		if(pvz_game != null) {
			pvz_game.timer();
		}
	}

	/**
	 * Constructor. Initializes the main game window, panel, and timer.
	 */
	plantsvszombies() {
		newGame();
		panel = new mainPanel();
		panel.setSize(860, 678+35);
		panel.setVisible(true);
		timer = new mainTimer(59);
	}

	/**
	 * The main game window/frame.
	 */
	class mainPanel extends JFrame {
		mainPanelListener  a = null;
		window b = null;

		/**
		 * Constructs the mainPanel JFrame, adds a drawing window and window listener.
		 */
		mainPanel() {
			b = new window();
			this.add(b);
			a = new mainPanelListener();
			this.addWindowListener(a);
			this.repaint();
		}
	}

	/**
	 * Handles window closing event to exit the game.
	 */
	class mainPanelListener extends WindowAdapter {
		public void windowClosing(WindowEvent e) {
			System.exit(0);
		}
	}

	/**
	 * The main drawing and mouse input panel.
	 */
	class window extends JPanel {
		mouseListener a = null;

		/**
		 * Constructs the window panel and adds mouse listeners.
		 */
		window() {
			a = new mouseListener();
			this.addMouseListener(a);
			this.addMouseMotionListener(a);
		}

		/**
		 * Mouse and motion event adapter for handling mouse actions.
		 */
		class mouseListener extends MouseAdapter {
			public void mousePressed(MouseEvent e) {
				mx = e.getX();
				my = e.getY();
				mouseclick(e);
				panel.repaint();
			}
			public void mouseDragged(MouseEvent e) {
				mx = e.getX();
				my = e.getY();
				mousedrag(e);
				panel.repaint();
			}
			public void mouseMoved(MouseEvent e) {
				mx = e.getX();
				my = e.getY();
				mousemove(e);
				panel.repaint();
			}
			public void mouseReleased(MouseEvent e) {
				mx = e.getX();
				my = e.getY();
				mouserelease(e);
				panel.repaint();
			}
			public void mouseClicked(MouseEvent e) {
				// Not used
			}
		}

		/**
		 * Paints the current game panel.
		 * @param g The Graphics context.
		 */
		public void paint(Graphics g) {
			panel(g);
		}
	}

	/**
	 * Main timer thread for the game loop and panel refresh.
	 */
	class mainTimer implements Runnable {
		Thread xc = null;
		long a;

		/**
		 * Constructs and starts the timer thread.
		 * @param jianGe Time interval in milliseconds.
		 */
		mainTimer(long jianGe) {
			this.a = jianGe;
			xc = new Thread(this);
			xc.start();
		}

		/**
		 * The main timer loop that updates game and repaints panel.
		 */
		public void run() {
			while(true) {
				System.out.print("");
				while (runstate == 0) {
					try {
						Thread.sleep(a);
						if (this == timer) {
							try {
								timer();
							} catch (Exception e) {
								e.printStackTrace();
							}
							panel.repaint();
						}
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
}