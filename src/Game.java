import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Game implements MouseListener, MouseMotionListener
{
    public static final int BOARD_SIDEX_SPACE = 81;
    public static final int BOARD_TOPY_SPACE = 215;
    public static final int BOARDTILE_SPACE = 5;
    private Board board;
    boolean moonTileClicked;
    private MoonTile clickedMoonTile;
    private Player humanPlayer;
    private Player computerPlayer;
    private boolean isHumanPlayerTurn;
    private Player currentPlayer;
    private static final int NUMBER_OF_BOARDS = 3;
    private static final int cols = 5;
    private static final int rows = 4;
    private int numRows;
    private int numCols;
    private GameViewer window;
    private ArrayList<MoonTile> moonTiles;
    private String[] moonPhases = {"empty","left sliver","left half","left most",
            "full","right most","right half","right sliver"};


    public Game()
    {
        this.moonTileClicked = false;

        moonTiles = new ArrayList<MoonTile>();
        board = new Board(this);
        generateBoard();
        generateMoonTiles();
        humanPlayer = new Player(this);
        computerPlayer = new Player(this);
        currentPlayer = humanPlayer;

        window = new GameViewer(this);
        this.window.addMouseListener(this);
        this.window.addMouseMotionListener(this);

        for (ArrayList<BoardTile> b : board.getBoard())
        {
            for (BoardTile bt : b)
            {
                if (!bt.isWall())
                {
                    System.out.println(bt.getX()+","+bt.getY()+"\n");
                }
            }
        }
    }

    public void generateMoonTiles()
    {
        for (int i=0;i<8;i++)
        {
            moonTiles.add(new MoonTile(moonPhases[i],i,this));
        }
    }

    // FillBoard function courtesy of MazeSolver
    public void fillBoard(String filename)
    {
        // Create the arraylist to update board
        ArrayList<ArrayList<BoardTile>> tempBoard = new ArrayList<ArrayList<BoardTile>>();
        for (int i=0; i<numRows;i++)
        {
            ArrayList<BoardTile> row = new ArrayList<>();
            for (int j=0;j<numCols;j++)
            {
                row.add(null);
            }
            tempBoard.add(row);
        }

        try {
            File myObj = new File(filename);
            Scanner myReader = new Scanner(myObj);

            // The row and col are specified in the first line of the file
            this.numRows = myReader.nextInt();
            this.numCols = myReader.nextInt();
            myReader.nextLine();

            // Fill temp board with dummy values that will be swapped later
            for (int i=0; i<numRows;i++)
            {
                ArrayList<BoardTile> row = new ArrayList<>();
                for (int j=0;j<numCols;j++)
                {
                    row.add(null);
                }
                tempBoard.add(row);
            }

            int x = 0;
            int y = 0;
            for (int row=0; row<this.numRows; row++) {
                y = BOARD_TOPY_SPACE + row*(BoardTile.getSize()+BOARDTILE_SPACE);
                String line = myReader.nextLine();

                for (int col=0; col<this.numCols; col++) {
                    x = BOARD_SIDEX_SPACE + col*(BoardTile.getSize()+BOARDTILE_SPACE);

                    // Create a new MazeCell for each location
                    tempBoard.get(row).set(col,new BoardTile(row,col,x,y,this));

                    // Set if it is a wall
                    if (line.charAt(col) == '#') {
                        tempBoard.get(row).get(col).setWall(true);
                    }
                    else
                    {
                        tempBoard.get(row).get(col).setWall(false);
                    }
                }
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        setNeighbors();
        board.setBoard(tempBoard);
    }

    public void setNeighbors()
    {
        for (int i=0;i<board.getBoard().size();i++)
        {
            for (int j=0;j<board.getBoard().get(i).size();j++)
            {
                BoardTile currentTile = board.getBoard().get(i).get(j);
                setNeighborsRing(currentTile);
            }
        }
    }

    public boolean setNeighborsRing(BoardTile currentTile)
    {
        boolean didSetNeighbors = false;

        //Checks tiles in a ring around the current tile
        int[] rowChecks = {-1,-1,-1,0,0,1,1,1};
        int[] colChecks = {-1,0,1,-1,1,-1,0,1};
        //Iterates through the loop for as many times as there are tiles around a center tile (8)
        for (int i=0;i<8;i++)
        {
            //Sets the row and col index to check
            int row = currentTile.getRow() + rowChecks[i];
            int col = currentTile.getCol() + colChecks[i];

            //If the tile that we want to get is not outside the board, and it is not a wall
            if (row >=0 && row<board.getBoard().size() && col>=0 && col<board.getBoard().get(0).size() && !board.getBoard().get(row).get(col).isWall())
            {
                currentTile.getNeighbors().add(board.getBoard().get(row).get(col));
                didSetNeighbors=true;
            }
        }

        return didSetNeighbors;
    }

    public void generateBoard() {
        // Picking a random board
//        int boardType = (int) (Math.random() * NUMBER_OF_BOARDS);
        int boardType =1;//FOR TESTING ONLY

        fillBoard("Board"+boardType);

        // Generating each board
//        if (boardType == 1) {
//            //code for generating one arrangement of the board
//            ArrayList<ArrayList<BoardTile>> board1 = new ArrayList<ArrayList<BoardTile>>();
//            ArrayList<BoardTile> a = new ArrayList<BoardTile>();
//            a.add(new BoardTile(false,-1,-1,300,300,this));
//            board1.add(a);
//            this.board.setBoard(board1);
//
//        } else if (boardType == 2) {
//            //code for generating second arrangement of the board
//        } else if (boardType == 3) {
//            //code for generating third arrangement of the board
//        }
//        else {
//            // Debugging
//            System.out.println("error making board");
//        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
//        System.out.println("pressed");
    }

    @Override
    public void mouseReleased(MouseEvent e) {
//        System.out.println("released");
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        System.out.println("CLICK");
        if (currentPlayer.equals(humanPlayer))
        {
            System.out.println("got human player");

            // Check to see if a moonTile was clicked
            // If it was, save the clicked moonTile to the clickedMoonTile instance variable and set moonTileClicked = true
            MoonTile clickedMT = getMoonTileClicked(e.getX(),e.getY());
            if (clickedMT != null)
            {
                System.out.println("clicked moontile");
                clickedMoonTile = clickedMT;
                moonTileClicked = true;
                return;
            }
            // If a moonTile has already been clicked
            if(moonTileClicked)
            {
                // Check to see if a boardTile was clicked
                // If so, move clicked moonTile to the boardTile and update moonTileClicked to false
                BoardTile clickedBT = getBoardTileClicked(e.getX(),e.getY());
                if (clickedBT != null && !clickedBT.isWall() && clickedBT.isOpen())
                {
                    System.out.println("clicked boardtile");
                    // Update the MT x and y position to the corresponding value on the boardtile
                    clickedMoonTile.setX(clickedBT.getX());
                    clickedMoonTile.setY(clickedBT.getY());

                    // Remove the MT from hand and move it to the board
                    MoonTile temp = new MoonTile(currentPlayer.getHand().remove(currentPlayer.getHand().indexOf(clickedMoonTile)));
                    clickedBT.playTile(temp);

                    // Reset the variables for swapping
                    clickedMoonTile = null;
                    moonTileClicked = false;
                    window.repaint();
//                    System.out.print(temp);
                    System.out.println("swap code ran");
                    return;
                }
            }
            System.out.println("\nclicked non-clickable object");
        }
        else
        {
            System.out.println("erm how da computer click???");
        }
        window.repaint();
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
//        System.out.println(currentPlayer.getHand().size());
        for (MoonTile m : currentPlayer.getHand())
        {
//            System.out.println(m);
            if (m.getX()<=x && x<=m.getX()+m.getSize() && m.getY()<=y && y<=m.getY()+m.getSize())
            {
                return m;
            }
        }
        return noTileClicked;
    }

    @Override
    public void mouseEntered(MouseEvent e) {
//        System.out.println("enter");
        }

    @Override
    public void mouseExited(MouseEvent e) {
//        System.out.println("exit");
    }

    @Override
    public void mouseDragged(MouseEvent e) {
//        System.out.println("dragging");
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

    public GameViewer getWindow()
    {
        return window;
    }

    public boolean isHumanPlayerTurn() {
        return isHumanPlayerTurn;
    }

    public Player getComputerPlayer() {
        return computerPlayer;
    }

    public Player getHumanPlayer() {
        return humanPlayer;
    }
}