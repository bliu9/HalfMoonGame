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
    private static final int size = 104;
    private Game game;

    public BoardTile(boolean isWall,int row,int col,int x, int y, Game game)
    {
        this.row = row;
        this.col = col;
        this.x = x;
        this.y = y;
        this.game = game;

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
        playedTile.setX(x+((size-playedTile.getSize())/2));
        playedTile.setY(y+((size-playedTile.getSize())/2));
        this.isOpen = false;
    }

    // Draws the board tile and a moon tile that is played into it
    public void draw(Graphics g)
    {
        g.setColor(Color.RED);
        g.drawRect(x,y,size,size);
        if (playedTile != null)
        {
            playedTile.draw(g);
        }
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
