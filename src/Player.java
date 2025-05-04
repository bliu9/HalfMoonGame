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
        ArrayList<MoonTile> moonTilesCopy = new ArrayList<>(game.getMoonTiles());
        for (int i=0;i<4;i++)
        {
            hand.add(moonTilesCopy.get((int)(Math.random()*moonTilesCopy.size())));
            moonTilesCopy.remove(hand.get(i));
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

    public void setHandCoordinates()
    {
        int handLen = (hand.get(0).getSize() + GameViewer.HAND_TILE_GAP) * hand.size();
        int sideSpace = (GameViewer.WINDOW_WIDTH-handLen)/2;

        for (int i = 0; i < hand.size(); i++)
        {
            hand.get(i).setX(sideSpace+(GameViewer.HAND_TILE_GAP/2)+(i*(hand.get(0).getSize()+GameViewer.HAND_TILE_GAP)));
            hand.get(i).setY(GameViewer.HAND_Y_COORD);
        }
    }

    public boolean isWinner()
    {
        return isWinner;
    }

    public int getPoints()
    {
        return points;
    }

    public void addPoints()
    {
        points++;
    }

    public void addPoints(int add)
    {
        this.points += add;
    }

    public void setWinner(boolean winner)
    {
        this.isWinner = winner;
    }
}
