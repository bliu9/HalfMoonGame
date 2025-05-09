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
    private boolean isHumanPlayer;

    public Player(Game game, boolean isHumanPlayer)
    {
        this.game = game;
        this.isHumanPlayer = isHumanPlayer;
        if (isHumanPlayer)
        {
            playerColorTheme = Color.cyan;
        }
        else
        {
            playerColorTheme = Color.lightGray;
        }
        hand = new ArrayList<MoonTile>();
        generateHand();
    }

    public void generateHand()
    {
        ArrayList<MoonTile> moonTilesCopy = new ArrayList<>(game.getMoonTiles());
        for (int i=0;i<sizeOfHand;i++)
        {
            hand.add(moonTilesCopy.get((int)(Math.random()*moonTilesCopy.size())));
            moonTilesCopy.remove(hand.get(i));
        }
        System.out.println(hand);
    }

    public void addTile()
    {
        ArrayList<MoonTile> moonTilesCopy = new ArrayList<>();
        for (MoonTile mt : game.getMoonTiles())
        {
            moonTilesCopy.add(new MoonTile(mt));
        }
        hand.add(moonTilesCopy.get((int)(Math.random()*moonTilesCopy.size())));
    }

    public ArrayList<MoonTile> getHand()
    {
        return hand;
    }

    public void draw(Graphics g)
    {
        if (isHumanPlayer)
        {
            // Draw hand
            setHandCoordinates();
            for (MoonTile mt : hand)
            {
                mt.draw(g);
            }

            // Draw points
            g.setFont(new Font("Arial Black",Font.BOLD,game.getWindow().POINTS_FONT_SIZE));
            g.setColor(playerColorTheme);
            g.drawString("Your Points:",game.getWindow().H_PLAYER_PTS_START,game.getWindow().TITLE_BAR_HEIGHT);
            g.drawString(""+points,game.getWindow().H_PLAYER_PTS_START,game.getWindow().TITLE_BAR_HEIGHT+game.getWindow().POINTS_FONT_SIZE);
        }
        else
        {
            g.setFont(new Font("Arial Black",Font.BOLD,game.getWindow().POINTS_FONT_SIZE));
            g.setColor(playerColorTheme);
            g.drawString("Moon's Points:",game.getWindow().WINDOW_WIDTH-game.getWindow().C_PLAYER_PTS_START_SUB,game.getWindow().TITLE_BAR_HEIGHT);
            g.drawString(""+points,game.getWindow().WINDOW_WIDTH-game.getWindow().C_PLAYER_PTS_START_SUB,game.getWindow().TITLE_BAR_HEIGHT+game.getWindow().POINTS_FONT_SIZE);
        }

        // Draw player points
    }

    public void setHandCoordinates()
    {
        int handLen = (hand.get(0).getSize() + GameViewer.HAND_TILE_GAP) * hand.size();
        int sideSpace = (GameViewer.WINDOW_WIDTH-handLen)/2;

        //for debugging
        System.out.println("before setting coords "+hand.size());

        for (int i = 0; i < hand.size(); i++)
        {
            hand.get(i).setX(sideSpace+(GameViewer.HAND_TILE_GAP/2)+(i*(hand.get(0).getSize()+GameViewer.HAND_TILE_GAP)));
            hand.get(i).setY(GameViewer.HAND_Y_COORD);
        }

        //for debugging
        System.out.println("after setting coords "+hand.size());
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

    public Color getPlayerColorTheme()
    {
        return playerColorTheme;
    }
}
