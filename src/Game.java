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
    private static final int CYCLE_BONUS_MULT = 2;
    private static final int MIN_TILES_FOR_CYCLE_BONUS = 3;
    private static final int PTS_FOR_PAIR = 2;
    private boolean isGameOver;
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
    private static final String[] moonPhases = {"empty","left sliver","left half","left most",
            "full","right most","right half","right sliver"};
    private static final int[] moonPhaseFillValues = {0,1,2,3,4,3,2,1};


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
        isGameOver = false;

        window = new GameViewer(this);
        this.window.addMouseListener(this);
        this.window.addMouseMotionListener(this);

        // Start the gameplay loop
        playGame();
    }

    private void playGame()
    {
        // While the game isn't over
        while (!isGameOver)
        {
            doCurrentPlayerTurn();



            // Update isGameOver if all board tiles are filled
            checkGameOver();
        }

        // Apply points to players for possession
        applyPossessionPoints();

        // Set who the winner is and display win screen
        if (humanPlayer.getPoints()>computerPlayer.getPoints())
        {
            humanPlayer.setWinner(true);
        }
        else if (humanPlayer.getPoints()<computerPlayer.getPoints())
        {
            computerPlayer.setWinner(true);
        }
        //draw win screen
            //if both players have their iswinner == false, it was a tie

    }

    private void doCurrentPlayerTurn()
    {
        if (currentPlayer.equals(humanPlayer))
        {
            //draw the "play a moon tile" prompt on the moon
            //set game state to human player turn (make this allow the detection code to run)
            //add a check moon cycle function call once the player places a tile
            //add a tile to the hand of the current player
            //update current player to computer player
        }
        else if(currentPlayer.equals(computerPlayer))
        {
            //draw a "computer's turn"
            //set game state to computer player turn
            //play a random moon tile from computer hand onto a random open board tile
            //call check moon cycle on that played tile
            //add a tile to the hand of the current player
            //update current player to human player
        }
    }

    private void applyPossessionPoints()
    {
        // iterates through board to add one point for each tile a player has possession over
        for (ArrayList<BoardTile> b:board.getBoard())
        {
            for (BoardTile bt: b)
            {
                if (!bt.isWall())
                {
                    if (bt.getPlayedTile().getPlayerPossession().equals(humanPlayer))
                    {
                        humanPlayer.addPoints();
                    }
                    else
                    {
                        computerPlayer.addPoints();
                    }
                }
            }
        }
    }

    public void checkGameOver()
    {
        // iterates through board to check if all the tiles are filled
        for (ArrayList<BoardTile> b:board.getBoard())
        {
            for (BoardTile bt: b)
            {
                if (!bt.isWall()&&bt.isOpen())
                {
                    isGameOver = false;
                    return;
                }
            }
        }

        // Set the game to be over
        isGameOver = true;
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
            if (moonTileClicked)
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

                    //NEED TO MAKE THESE UPDATE POSSESSION FOR SUCCESSFUL COMBO
                    // Call function to check for any combos that the player got
                    checkMoonCycles(temp);
                    // Call function to check for pairs the player got
                    checkMoonPairs(temp);

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

    public void checkMoonPairs(MoonTile clickedMoonTile)
    {
        for (BoardTile neighbor : clickedMoonTile.getPlaced().getNeighbors())
        {
            if (!neighbor.isOpen())
            {
                if (moonPhaseFillValues[findIndex(moonPhases, clickedMoonTile.getMoonPhase())] +
                        moonPhaseFillValues[findIndex(moonPhases, neighbor.getPlayedTile().getMoonPhase())] == 4
                        && !getFirstWord(clickedMoonTile.getMoonPhase()).equals(getFirstWord(neighbor.getPlayedTile().getMoonPhase()))) {
                    currentPlayer.addPoints(PTS_FOR_PAIR);
                }
            }
        }
    }

    public String getFirstWord(String str)
    {
        if (str == null || str.isEmpty()) {
            return "";
        }
        String[] words = str.trim().split(" ");
        return words[0];
    }

    public void checkMoonCycles(MoonTile clickedMoonTile)
    {
        int numTiles = checkMoonCyclesHelper(clickedMoonTile,0);
        if (numTiles>=MIN_TILES_FOR_CYCLE_BONUS)
        {
            currentPlayer.addPoints(numTiles*CYCLE_BONUS_MULT);
        }
    }

    public int checkMoonCyclesHelper(MoonTile search,int cycleCount)
    {
        int phaseIndex = findIndex(moonPhases,search.getMoonPhase());

        // Iterate through all the board tile's neighbors and if they are valid, call the function recursively to check them
        for (BoardTile neighbor : search.getPlaced().getNeighbors())
        {
            if (!neighbor.isOpen() && (neighbor.getPlayedTile().getMoonPhase().equals(moonPhases[phaseIndex-1]) ||
                    neighbor.getPlayedTile().getMoonPhase().equals(moonPhases[phaseIndex+1])))
            {
                return checkMoonCyclesHelper(neighbor.getPlayedTile(),++cycleCount);
            }
        }

        // Base case: there are no valid neighbors left to check
        return cycleCount;
    }

    public int findIndex(String[] arr,String str)
    {
        for (int i =0; i<arr.length;i++)
        {
            if (arr[i].equals(str))
            {
                return i;
            }
        }
        return -1;
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