import java.awt.*;
import java.util.ArrayList;

public class BoardTile
{
    private boolean isWall;
    private boolean isOpen;
    private MoonTile playedTile;
    private int row;
    private int col;
    private int x;
    private int y;
    private ArrayList<BoardTile> neighbors;
    private final int size = 100;

    public BoardTile(boolean isWall,int row,int col,ArrayList<BoardTile> neighbors)
    {
        this.row = row;
        this.col = col;
        this.neighbors = neighbors;

        // If the tile is a wall, then set it to closed; if the tile is a space, set it to open
        this.isWall=isWall;
        if (this.isWall)
        {
            this.isOpen=false;
        }
        else
        {
          this.isOpen=true;
        }
    }

    // Sets the tile of the cell to the played MoonTile
    public void playTile(MoonTile playedTile)
    {
        this.playedTile=playedTile;
        playedTile.setRow(row);
        playedTile.setCol(col);
        this.isOpen = false;
    }

    // Draws the board tile
    public void draw(Graphics g)
    {

    }


    // Getters
    public boolean isOpen() {
        return isOpen;
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

    public int getSize(){return size;}

    public ArrayList<BoardTile> getNeighbors()
    {
        return neighbors;
    }
}
