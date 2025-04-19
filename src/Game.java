import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

public class Game implements MouseListener, MouseMotionListener
{
    ArrayList<ArrayList<BoardTile>> board;
    private MoonTile clickedMoonTile;
    private Player humanPlayer;
    private Player computerPlayer;
    private boolean isHumanPlayerTurn;
    private Player currentPlayer;
    private final int NUMBER_OF_BOARDS = 3;
    private boolean isChoosingMoonTile = false;
    private GameViewer window;
    private ArrayList<MoonTile> moonTiles;
    private String[] moonPhases = {"empty","left sliver","left half","left most",
            "full","right most","right half","right sliver"};


    public Game()
    {
        moonTiles = new ArrayList<MoonTile>();
        generateBoard();
        generateMoonTiles();
        humanPlayer = new Player(this);
        computerPlayer = new Player(this);
        currentPlayer = humanPlayer;

        window = new GameViewer(this);
    }

    public void generateMoonTiles()
    {
        for (int i=0;i<8;i++)
        {
            moonTiles.add(new MoonTile(moonPhases[i],i));
        }
    }

    public void generateBoard() {
        // Picking a random board
        int boardType = (int) (Math.random() * NUMBER_OF_BOARDS);

        // Generating each board
        if (boardType == 1) {
            //code for generating one arrangement of the board
        } else if (boardType == 2) {
            //code for generating second arrangement of the board
        } else if (boardType == 3) {
            //code for generating third arrangement of the board
        }

        // Debugging
        System.out.println("error making board");
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (currentPlayer.equals(humanPlayer))
        {
            if (true)
            {
                MoonTile clicked = getMoonTileClicked(e.getX(),e.getY());
                if (clicked != null)
                {
                    clickedMoonTile = clicked;
                }
            }
            else if(true)
            {
                BoardTile clicked = getBoardTileClicked(e.getX(),e.getY());
                if (clicked != null)
                {
                    //update the stuff to play a MoonTile
                }
            }
            else
            {
                System.out.println("\nclicked non-clickable object");
            }
        }
        else
        {
            System.out.println("erm how da computer click???");
        }
    }

    private BoardTile getBoardTileClicked(int x, int y)
    {
        BoardTile noTileClicked = null;
        for (ArrayList<BoardTile> b : board)
        {
            for (BoardTile bt : b)
            {
                if (bt.getX()<=x && x<=bt.getX()+bt.getSize() && bt.getY()<=y && y<=bt.getY()+bt.getSize())
                {
                    return bt;
                }
            }
        }
        return noTileClicked;
    }

    private MoonTile getMoonTileClicked(int x, int y)
    {
        MoonTile noTileClicked = null;
        for (MoonTile m : currentPlayer.getHand())
        {
            if (m.getX()<=x && x<=m.getX()+m.getSize() && m.getY()<=y && y<=m.getY()+m.getSize())
            {
                return m;
            }
        }
        return noTileClicked;
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    public static void main(String[] args) {
        Game game = new Game();
    }

    public ArrayList<MoonTile> getMoonTiles() {
        return moonTiles;
    }

    public Player getCurrentPlayer()
    {
        return currentPlayer;
    }
}