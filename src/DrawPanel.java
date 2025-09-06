import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Point;
import java.util.ArrayList;
import java.awt.Font;

class DrawPanel extends JPanel implements MouseListener {

    private ArrayList<Card> hand;

    // Rectangle object represents a rectangle.
    private Rectangle button;
    private Rectangle button2;

    public DrawPanel() {
        button = new Rectangle(170, 50, 160, 26);
        button2 = new Rectangle(170, 381, 160, 26);
        this.addMouseListener(this);
        hand = Card.buildHand();
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int x = 145;
        int y = 0;
        for (int i = 0; i < hand.size(); i++) {
            if (i % 3 == 0){
                x = 145;
                y += 100;
            }

            Card c = hand.get(i);
            if (c.getHighlight()) {
                // draw the border rectangle around the card
                g.drawRect(x, y, c.getImage().getWidth(), c.getImage().getHeight());
            }
            // establish the location of the rectangle "hitbox"
            c.setRectangleLocation(x, y);

            g.drawImage(c.getImage(), x, y, null);
            x = x + c.getImage().getWidth() + 10;
        }

        // drawing the bottom button
        // with my favorite font (Courier New!!)
        g.setFont(new Font("Courier New", Font.BOLD, 20));
        g.drawString("RESTART", 208, 69);    // print statement for graphics
        g.drawRect((int)button.getX(), (int)button.getY(), (int)button.getWidth(), (int)button.getHeight());

        // drawing the bottom button
        // with my favorite font (Courier New!!)
        g.setFont(new Font("Courier New", Font.BOLD, 20));
        g.drawString("REPLACE CARDS", 172, 400);    // print statement for graphics
        g.drawRect((int)button2.getX(), (int)button2.getY(), (int)button2.getWidth(), (int)button2.getHeight());
    }

    public void mousePressed(MouseEvent e) {

        Point clicked = e.getPoint();

        // left click
        if (e.getButton() == 1) {
            // if "clicked" is inside the button rectangle (did you click the button?)
            if (button.contains(clicked)) {
                hand = Card.buildHand();
            }

            // go through each card and check if they were clicked on
            // if clicked, flip them
            for (int i = 0; i < hand.size(); i++) {
                Rectangle box = hand.get(i).getCardBox();
                if (box.contains(clicked)) {
                    hand.get(i).flipCard();
                }
            }
        }

        // right click
        if (e.getButton() == 3) {
            for (int i = 0; i < hand.size(); i++) {
                Rectangle box = hand.get(i).getCardBox();
                if (box.contains(clicked)) {
                    hand.get(i).flipHighlight();
                }
            }

            if (button2.contains(clicked)) {
                System.out.println("Poked");
                for (int i = 0; i < hand.size(); i++){
                    if (hand.get(i).getHighlight()){
                        hand = hand.get(i).replaceCard(i); // fix this line of code and the method!
                    }
                }
            }
        }
    }

    public void mouseReleased(MouseEvent e) { }
    public void mouseEntered(MouseEvent e) { }
    public void mouseExited(MouseEvent e) { }
    public void mouseClicked(MouseEvent e) { }
}