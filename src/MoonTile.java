public class MoonTile
{
    private Player playerPossession;
    private String moonPhase;
    private int row;
    private int col;

    public MoonTile(String moonPhase)
    {
        this.moonPhase = moonPhase;
    }

    public MoonTile(String moonPhase,int row,int col)
    {
        this.moonPhase = moonPhase;
        this.row = row;
        this.col = col;
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
}
