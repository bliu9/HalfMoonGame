import java.awt.*;
import java.util.ArrayList;

public class Player
{
    private ArrayList<MoonTile> hand;
    private int points;
    private Color playerColorTheme;
    private boolean isWinner;
    private int sizeOfHand = 4;
    private Game game;

    public Player(Game game)
    {
        this.game = game;
        hand = new ArrayList<MoonTile>();
        generateHand();
    }

    public void generateHand()
    {
        for (int i=0;i<4;i++)
        {
            hand.add(game.getMoonTiles().get((int)(Math.random()*8)));
        }
        System.out.println(hand);
    }

    public ArrayList<MoonTile> getHand()
    {
        return hand;
    }

    public void draw(Graphics g)
    {
        for (MoonTile mt : hand)
        {
            mt.draw(g);
        }
    }
}
