import java.awt.*;

public class MoonTile
{
    private Player playerPossession;
    private String moonPhase;
    private int row;
    private int col;
    private int x;
    private int y;
    private final int size = 100;
    private int moonImage;
    private Game game;

    public MoonTile(String moonPhase, int moonImage, Game game)
    {
        this.moonPhase = moonPhase;
        this.moonImage = moonImage;
        this.game = game;
    }

    public MoonTile(String moonPhase,int row,int col)
    {
        this.moonPhase = moonPhase;
        this.row = row;
        this.col = col;
    }

    public void draw(Graphics g)
    {
        g.setColor(Color.RED);
        g.drawRect(x,y,size,size);


    }

    // Getters
    public String getMoonPhase() {
        return moonPhase;
    }

    public Player getPlayerPossession() {
        return playerPossession;
    }

    public int getCol() {
        return col;
    }

    public int getRow() {
        return row;
    }

    // Setters
    public void setCol(int col) {
        this.col = col;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public void setX(int x)
    {
        this.x = x;
    }

    public void setY(int y)
    {
        this.y=y;
    }

    // Getters
    public int getSize()
    {
        return size;
    }

    public int getX()
    {
        return x;
    }

    public int getY()
    {
        return y;
    }
}
