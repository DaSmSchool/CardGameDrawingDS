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
    private Rectangle button;
    private Rectangle replaceCard;
    private Rectangle count;

    public DrawPanel() {
        button = new Rectangle(147+15, 250, 150, 26);
        replaceCard = new Rectangle(147+15, 300, 150, 26);
        count = new Rectangle(147+15, 350, 100, 26);
        this.addMouseListener(this);
        hand = Card.buildHand();
    }

    public void drawButton(Graphics g, Rectangle button, String text, int size) {
        g.setFont(new Font("Courier New", Font.BOLD, size));
        g.drawString(text, button.x + 3, button.y+20);
        g.drawRect((int)button.getX(), (int)button.getY(), (int)button.getWidth(), (int)button.getHeight());
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int xStartPos = 60;
        int yStartPos = 10;
        int x = xStartPos;
        int y = yStartPos;
        x += hand.get(0).getImage().getWidth() + 10;
        for (int i = 0; i < hand.size(); i++) {

            Card c = hand.get(i);

            if (i % 3 == 0 && i!=0) {
                x = xStartPos + hand.get(0).getImage().getWidth() + 10;
                y += c.getImage().getHeight() + 10;
            }

            if (c.getHighlight()) {
                g.drawRect(x, y, c.getImage().getWidth(), c.getImage().getHeight());
            }
            c.setRectangleLocation(x, y);
            g.drawImage(c.getImage(), x, y, null);
            x = x + c.getImage().getWidth() + 10;
        }
        drawButton(g, button, "RESTART GAME", 20);
        drawButton(g, replaceCard, "REPLACE SELECTED", 20);
        drawButton(g, count, "CARDS LEFT: " + Card.getDeck().size(), 20);
    }

    public void mousePressed(MouseEvent e) {

        Point clicked = e.getPoint();

        if (e.getButton() == 1) {
            if (button.contains(clicked)) {
                hand = Card.buildHand();
            }

            if (replaceCard.contains(clicked)) {
                for (int i = 0; i < hand.size(); i++) {
                    Card currCard = hand.get(i);
                    if (currCard.getHighlight()) {
                        hand.set(i, currCard.getAvailableDeckCard());
                        currCard = hand.get(i);
                        currCard.readImage();
                        if (currCard.getHighlight()) {
                            currCard.flipHighlight();
                        }
                    }
                }
            }

            for (int i = 0; i < hand.size(); i++) {
                Card currCard = hand.get(i);
                Rectangle box = currCard.getCardBox();
                if (box.contains(clicked)) {
//                    if (currCard.getHighlight()) {
//                        hand.set(i, currCard.getAvailableDeckCard());
//                        currCard.readImage();
//                    } else {
                        currCard.flipHighlight();
//                    }
                }
            }

//            flipping logic
//            for (int i = 0; i < hand.size(); i++) {
//                Rectangle box = hand.get(i).getCardBox();
//                if (box.contains(clicked)) {
//                    hand.get(i).flipCard();
//                }
//            }

        }

//        if (e.getButton() == 3) {
//            for (int i = 0; i < hand.size(); i++) {
//                Card currCard = hand.get(i);
//                Rectangle box = currCard.getCardBox();
//                if (box.contains(clicked)) {
//                    if (currCard.getHighlight()) {
//                        hand.set(i, currCard.getAvailableDeckCard());
//                        currCard.readImage();
//                    } else {
//                        currCard.flipHighlight();
//                    }
//                }
//            }
//        }


    }

    public void mouseReleased(MouseEvent e) { }
    public void mouseEntered(MouseEvent e) { }
    public void mouseExited(MouseEvent e) { }
    public void mouseClicked(MouseEvent e) { }
}