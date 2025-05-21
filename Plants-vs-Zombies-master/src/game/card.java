package game;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import javax.swing.ImageIcon;

/**
 * Represents a plant card in the shop.
 */
public class card {
    int canbuyornot;   // 0: can buy, 1: can't buy
    int cardnumber;    // Card's sequence number in the shop
    String cardname;   // Name of the card (used for image loading)

    /**
     * Constructor.
     * @param number  The position/index of this card.
     * @param name    The card's name (used for image file).
     */
    public card(int number, String name) {
        this.cardname = name;
        this.cardnumber = number;
        canbuyornot = 0;
    }

    /**
     * Draw the card in the shop as available (can buy).
     * @param g The graphics context to draw on.
     */
    public void showinshop_canbuy(Graphics g) {
        Image canbuy_card = (new ImageIcon("Image/card/" + cardname + "0.png")).getImage();
        g.drawImage(canbuy_card, 95 + 54 * cardnumber, 8, null);
    }

    /**
     * Draw the card in the shop as unavailable (cannot buy).
     * @param g The graphics context to draw on.
     */
    public void showinshop_cannotbuy(Graphics g) {
        Image canbuy_card = (new ImageIcon("Image/card/" + cardname + "1.png")).getImage();
        g.drawImage(canbuy_card, 95 + 54 * cardnumber, 8, null);
    }

    /**
     * Check if the mouse click is on this card's area.
     * @param mbx X coordinate of the mouse click.
     * @param mby Y coordinate of the mouse click.
     * @return true if the card was pressed, false otherwise.
     */
   public boolean if_pressed(int mbx,int mby) {
		if (new Rectangle(95+54*cardnumber,8,50,70).contains(mbx,mby)) {
			return true;
		}
		return false;
	}

}