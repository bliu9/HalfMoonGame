// Bryan Liu for CS2

import java.awt.*;
import java.util.ArrayList;

public class BoardTile
{
    public static final Color BOARD_THEME_COLOR = Color.darkGray;
    private boolean isWall;
    private boolean isOpen;
    private MoonTile playedTile;
    private int row;
    private int col;
    private int x;
    private int y;
    private ArrayList<BoardTile> neighbors;
    private static final int size = 104;
    private static Game game;
    private boolean isDrawn;

    public BoardTile(boolean isWall,int row,int col,int x, int y, Game game)
    {
        // initialize instance variables
        this.row = row;
        this.col = col;
        this.x = x;
        this.y = y;
        BoardTile.game = game;
        this.isDrawn = false;
        this.neighbors = new ArrayList<>();

        // If the tile is a wall, then set it to closed; if the tile is a space, set it to open
        this.isWall=isWall;
        this.isOpen= !this.isWall;
    }

    public BoardTile (int row,int col,int x, int y, Game game)
    {
        // copy over all the values of the old board tile
        this.row = row;
        this.col = col;
        this.x = x;
        this.y = y;
        BoardTile.game = game;
        isDrawn = false;
        this.neighbors = new ArrayList<>();
    }

    // Sets the tile of the cell to the played MoonTile
    public void playTile(MoonTile playedTile)
    {
        // update played tile
        this.playedTile=playedTile;

        // set row and col
        playedTile.setRow(row);
        playedTile.setCol(col);

        // set x and y pos
        playedTile.setX(x+((size-playedTile.getSize())/2));
        playedTile.setY(y+((size-playedTile.getSize())/2));

        // update where moon tile placed board tile refercne
        playedTile.setPlaced(this);

        // update isopen
        this.isOpen = false;
    }

    // Draws the board tile and a moon tile that is played into it
    public void draw(Graphics g)
    {
        // Update isDrawn
        isDrawn=true;

        // Default board tile color
        g.setColor(BOARD_THEME_COLOR);

        // If there is a moon tile played, update the board tile color to the possession color of the player
        if (playedTile != null && playedTile.getPlayerPossession() != null)
        {
            if (playedTile.getPlayerPossession().equals(game.getHumanPlayer()))
            {
                g.setColor(game.getHumanPlayer().getPlayerColorTheme());
            }
            else if (playedTile.getPlayerPossession().equals(game.getComputerPlayer()))
            {
                g.setColor(game.getComputerPlayer().getPlayerColorTheme());
            }
        }

        g.fillRect(x,y,size,size);

        // If there is a played moon tile, draw it
        if (playedTile != null)
        {
            playedTile.draw(g);
        }
    }

    // Setters
    public void setWall(boolean isWall)
    {
        this.isWall = isWall;
        this.isOpen= !this.isWall;
    }

    public void setDrawn(boolean isDrawn)
    {
        this.isDrawn = isDrawn;
    }

    // Getters
    public boolean isOpen() {
        return isOpen;
    }

    public boolean isDrawn()
    {
        return isDrawn;
    }

    public MoonTile getPlayedTile() {
        return playedTile;
    }

    public boolean isWall() {
        return isWall;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public int getX(){return x;}

    public int getY(){return y;}

    public static int getSize(){return size;}

    public ArrayList<BoardTile> getNeighbors()
    {
        return neighbors;
    }
}
