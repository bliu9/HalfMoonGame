import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

public class Game implements MouseListener, MouseMotionListener
{
    private Board board;
    boolean moonTileClicked;
    private MoonTile clickedMoonTile;
    private Player humanPlayer;
    private Player computerPlayer;
    private boolean isHumanPlayerTurn;
    private Player currentPlayer;
    private final int NUMBER_OF_BOARDS = 3;
    private GameViewer window;
    private ArrayList<MoonTile> moonTiles;
    private String[] moonPhases = {"empty","left sliver","left half","left most",
            "full","right most","right half","right sliver"};


    public Game()
    {
        this.moonTileClicked = false;

        moonTiles = new ArrayList<MoonTile>();
        generateBoard();
        generateMoonTiles();
        humanPlayer = new Player(this);
        computerPlayer = new Player(this);
        currentPlayer = humanPlayer;


        window = new GameViewer(this);
        this.window.addMouseListener(this);
        this.window.addMouseMotionListener(this);
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
        board = new Board(new ArrayList<ArrayList<BoardTile>>());

        // Generating each board
        if (boardType == 1) {
            //code for generating one arrangement of the board
        } else if (boardType == 2) {
            //code for generating second arrangement of the board
        } else if (boardType == 3) {
            //code for generating third arrangement of the board
        }
        else {
            // Debugging
            System.out.println("error making board");
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        System.out.println("pressed");
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        System.out.println("released");
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        System.out.println("CLICK");
        if (currentPlayer.equals(humanPlayer))
        {
            System.out.println("got human player");
            if (!moonTileClicked)
            {
                MoonTile clicked = getMoonTileClicked(e.getX(),e.getY());
                if (clicked != null)
                {
                    System.out.println("clicked moontile");
                    clickedMoonTile = clicked;
                    moonTileClicked = true;
                }
            }
            else if(moonTileClicked)
            {
                System.out.println("clicked boardtile");
                BoardTile clicked = getBoardTileClicked(e.getX(),e.getY());
                if (clicked != null)
                {
                    System.out.println(clickedMoonTile.getX()+""+clickedMoonTile.getY());
                    clickedMoonTile.setX(clicked.getX());
                    clickedMoonTile.setY(clicked.getY());
                    window.repaint();
                    System.out.println(clickedMoonTile.getX()+""+clickedMoonTile.getY());
                    clickedMoonTile = null;
                    System.out.println("swap code ran");
                    moonTileClicked = false;
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
        for (ArrayList<BoardTile> b : board.getBoard())
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
        System.out.println("enter");
        }

    @Override
    public void mouseExited(MouseEvent e) {
        System.out.println("exit");
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        System.out.println("dragging");
    }

    @Override
    public void mouseMoved(MouseEvent e) {
//        System.out.println("move");
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

    public Board getBoard()
    {
        return board;
    }
}