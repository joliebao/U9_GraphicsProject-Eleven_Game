import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.awt.Rectangle;

public class Card {
    private String suit;
    private String value;
    private String imageFileName;
    private String backImageFileName;
    private boolean show;
    private BufferedImage image;
    private Rectangle cardBox;
    private boolean highlight;
    private static ArrayList<Card> deck = new ArrayList<Card>();
    private static int deckCount = 52;

    public Card(String suit, String value) {
        this.suit = suit;
        this.value = value;
        // image file is determined by the constructor's instance variable
        this.imageFileName = "images/card_"+suit+"_"+value+".png";
        this.show = true;
        this.backImageFileName = "images/card_back.png";

        // Which image should I be showing for the card?
        // Either the front image or the back image.
        this.image = readImage();
        this.cardBox = new Rectangle(-100, -100, image.getWidth(), image.getHeight());
        this.highlight = false;
    }

    public Rectangle getCardBox() {
        return cardBox;
    }

    public String getSuit() {
        return suit;
    }

    public void setRectangleLocation(int x, int y) {
        cardBox.setLocation(x, y);
    }

    public String getValue() {
        return value;
    }

    public String getImageFileName() {
        return imageFileName;
    }

    public String toString() {
        return suit + " " + value;
    }

    // When the card is left-clicked, the image changes
    // if true, show front, else, show back
    public void flipCard() {
        show = !show;
        this.image = readImage();
    }

    public void flipHighlight() {
        highlight = !highlight;
    }

    public boolean getHighlight() {
        return highlight;
    }

    public BufferedImage getImage() {
        return image;
    }

    // a BufferedImage object is an object that represents an image file to be
    // drawn on the screen (opens images like opening read files)
    public BufferedImage readImage() {
        try {
            BufferedImage image;
            if (show) {
                image = ImageIO.read(new File(imageFileName));
            }
            else {
                image = ImageIO.read(new File(backImageFileName));
            }
            return image;
        }
        catch (IOException e) {
            System.out.println(e);
            return null;
        }
    }

    public static ArrayList<Card> buildDeck() {
        ArrayList<Card> deck = new ArrayList<Card>();
        String[] suits = {"clubs", "diamonds", "hearts", "spades"};
        String[] values = {"02", "03", "04", "05", "06", "07", "08", "09", "10", "A", "J", "K", "Q"};
        for (String s : suits) {
            for (String v : values) {
                Card c = new Card(s, v);
                deck.add(c);
            }
        }
        return deck;
    }

    public static ArrayList<Card> buildHand() {
        ArrayList<Card> deck = Card.buildDeck();
        ArrayList<Card> hand = new ArrayList<Card>();
        for (int i = 0; i < 9; i++) {
            int r = (int) (Math.random() * deck.size());
            Card c = deck.remove(r);
            hand.add(c);
        }

        updateDeck(deck);
        return hand;
    }

    private static void updateDeck(ArrayList<Card> d){
        deck = d;
    }

    // When the card is right-clicked, the card changes
    public static ArrayList<Card> replaceCard(ArrayList<Card> hand, int loc) {
        int r = (int) (Math.random() * deck.size()); // pick random card index
        Card c1 = deck.remove(r); // save removed index
        hand.remove(loc); // remove old card
        hand.add(loc, c1); // add new card

        deckCount = deck.size();
        return hand;
    }

    public static void resetDeckCount(int value){
        deckCount = value;
    }

    public static boolean win(){
        System.out.println(deckCount);
        if (deckCount == 0){
            return true;
        }
        return false;
    }
}
